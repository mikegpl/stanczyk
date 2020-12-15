import grpc

from generated.stanczyk_pb2 import StanczykRequest
from generated.stanczyk_pb2_grpc import StanczykServerStub


def run():
    with grpc.insecure_channel('localhost:50051') as channel:
        stub = StanczykServerStub(channel)
        response = stub.FindFaces(StanczykRequest(message="Jaką wybrać mapę do gry?"))
        for face in response.data:
            print(face)
    print(f"Rada Stańczyka: {response}")


run()
