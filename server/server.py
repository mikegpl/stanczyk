from concurrent import futures

import grpc

from generated.stanczyk_pb2 import FaceDetectionResult, DetectedFaceData, StanczykRequest
from generated.stanczyk_pb2_grpc import StanczykServerServicer, add_StanczykServerServicer_to_server


class FacesGrpcService(StanczykServerServicer):
    def FindFaces(self, request: StanczykRequest, context):
        result = FaceDetectionResult()
        result.data.append(DetectedFaceData(x=0, y=1, w=100, h=100))
        return result


class Server:
    @staticmethod
    def run():
        server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
        add_StanczykServerServicer_to_server(FacesGrpcService(), server)
        server.add_insecure_port('localhost:50051')
        server.start()
        server.wait_for_termination()
