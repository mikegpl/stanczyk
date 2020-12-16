from concurrent import futures

import grpc

from face_detector import FaceDetector
from generated.stanczyk_pb2 import FindRequest, DetectedFaceData, FindResult
from generated.stanczyk_pb2_grpc import StanczykTaskExecutionServiceServicer, \
    add_StanczykTaskExecutionServiceServicer_to_server


class FacesGrpcService(StanczykTaskExecutionServiceServicer):
    def __init__(self):
        self.detector = FaceDetector()

    def FindFaces(self, request: FindRequest, context):
        faces = self.detector.from_base64_jpeg(request.base64Image)
        faces = [DetectedFaceData(x=face_x, y=face_y, w=face_w, h=face_h)
                 for (face_x, face_y, face_w, face_h) in faces]
        return FindResult(data=faces)


class Server:
    @staticmethod
    def run():
        server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
        add_StanczykTaskExecutionServiceServicer_to_server(FacesGrpcService(), server)
        server.add_insecure_port('localhost:50051')
        server.start()
        server.wait_for_termination()
