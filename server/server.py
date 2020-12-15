from concurrent import futures

import grpc

from generated.stanczyk_pb2 import StanczykResponse, StanczykRequest
from generated.stanczyk_pb2_grpc import StanczykServerServicer, add_StanczykServerServicer_to_server


class Service(StanczykServerServicer):
    def FindFaces(self, request: StanczykRequest, context):
        return StanczykResponse(message="Nie graj na mapie Polski, królu")


class Server:
    @staticmethod
    def run():
        server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
        add_StanczykServerServicer_to_server(Service(), server)
        server.add_insecure_port('localhost:50051')
        server.start()
        server.wait_for_termination()
