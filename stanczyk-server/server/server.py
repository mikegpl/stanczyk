import uuid
from concurrent import futures

import grpc

from face_detector import FaceDetector, make_image
from generated.stanczyk_pb2 import FindRequest, DetectedFaceData, FindResult, FindAndExchangeRequest, \
    DeviceExecutorMetadata, KnowledgeBatch, FindAndExchangeResult
from generated.stanczyk_pb2_grpc import StanczykTaskExecutionServiceServicer, \
    add_StanczykTaskExecutionServiceServicer_to_server, StanczykKnowledgeExchangeServiceServicer, \
    add_StanczykKnowledgeExchangeServiceServicer_to_server
from metric_utils import StanczykMetricCollector


class FacesGrpcService(StanczykTaskExecutionServiceServicer):
    def __init__(self, metrics_collector: StanczykMetricCollector):
        self.detector = FaceDetector()
        self.metrics = metrics_collector

    def _find_faces(self, request_id, base64_image):
        self.metrics.server.collect_initial(request_id)
        image = make_image(base64_image)
        self.metrics.server.insert(request_id, "size", image.size)
        faces = self.detector.detect_faces(image)
        faces = [DetectedFaceData(x=face_x, y=face_y, w=face_w, h=face_h)
                 for (face_x, face_y, face_w, face_h) in faces]
        self.metrics.server.collect_final(request_id)
        return faces

    def FindFaces(self, request: FindRequest, context):
        request_id = uuid.uuid4()
        return FindResult(data=self._find_faces(request_id, request.base64Image))

    def FindFacesAndExchangeKnowledge(self, request: FindAndExchangeRequest, context):
        request_id = uuid.uuid4()
        result = self._find_faces(request_id, request.base64Image)
        devices_metrics, server_metrics = self.metrics.get_recent_metrics()
        return FindAndExchangeResult(result=FindResult(data=result),
                                     data=KnowledgeBatch())  # todo - add some metrics -> KnowledgeBatch converter


class KnowledgeGrpcService(StanczykKnowledgeExchangeServiceServicer):
    def __init__(self, metrics_collector):
        self.metrics = metrics_collector

    def ExchangeKnowledge(self, request: DeviceExecutorMetadata, context):
        print(f"Received ${request} from device")
        self.metrics.devices.insert(request.data)
        devices_metrics, server_metrics = self.metrics.get_recent_metrics()
        print(f"NOT sending back snapshot\nDevices: {devices_metrics}\nServer: {server_metrics}")
        return KnowledgeBatch()  # todo - add some metrics -> KnowledgeBatch converter


class Server:
    @staticmethod
    def run():
        metrics_collector = StanczykMetricCollector()

        server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
        add_StanczykTaskExecutionServiceServicer_to_server(FacesGrpcService(metrics_collector),
                                                           server)
        add_StanczykKnowledgeExchangeServiceServicer_to_server(
            KnowledgeGrpcService(metrics_collector), server)
        server.add_insecure_port('localhost:50051')
        server.start()
        server.wait_for_termination()
