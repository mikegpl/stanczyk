import base64

import cv2
import grpc

from generated.stanczyk_pb2 import FindRequest
from generated.stanczyk_pb2_grpc import StanczykTaskExecutionServiceStub


def run():
    with grpc.insecure_channel('localhost:50051') as channel:
        stub = StanczykTaskExecutionServiceStub(channel)
        image = cv2.imread("../resources/bill.jpeg")
        _, buffer = cv2.imencode(".jpg", image)
        image_base64 = base64.b64encode(buffer)
        request = FindRequest(fileName="xD", base64Image=image_base64)
        response = stub.FindFaces(request)
        for face in response.data:
            print(face)


run()
