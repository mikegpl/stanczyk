import uuid
from concurrent import futures

import grpc

from face_detector import FaceDetector, make_image
from generated.stanczyk_pb2 import FindRequest, DetectedFaceData, FindResult
from generated.stanczyk_pb2_grpc import StanczykTaskExecutionServiceServicer, \
    add_StanczykTaskExecutionServiceServicer_to_server
from metric_utils import MetricCollector


class FacesGrpcService(StanczykTaskExecutionServiceServicer):
    def __init__(self):
        self.detector = FaceDetector()
        self.metricCollector = MetricCollector()

    def FindFaces(self, request: FindRequest, context):
        request_id = uuid.uuid4()
        self.metricCollector.collect_initial(request_id)
        image = make_image(request.base64Image)
        self.metricCollector.insert(request_id, "size", image.size)
        faces = self.detector.detect_faces(image)
`        faces = [DetectedFaceData(x=face_x, y=face_y, w=face_w, h=face_h)
                 for (face_x, face_y, face_w, face_h) in faces]
        self.metricCollector.collect_final(request_id)
        print(self.metricCollector.get_recent_metrics())
        return FindResult(data=faces)


class Server:
    @staticmethod
    def run():
        server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
        add_StanczykTaskExecutionServiceServicer_to_server(FacesGrpcService(), server)
        server.add_insecure_port('localhost:50051')
        server.start()
        server.wait_for_termination()
