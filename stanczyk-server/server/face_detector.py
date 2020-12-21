import cv2
import numpy as np
import base64


def make_image(base64img):
    jpg_bytes = base64.b64decode(base64img)
    image_array = np.frombuffer(jpg_bytes, dtype=np.uint8)
    return cv2.imdecode(image_array, flags=1)


class FaceDetector:
    def __init__(self):
        self.cascade_detector = cv2.CascadeClassifier('../resources/haarcascade_config.xml')

    def from_file(self, path):
        image = cv2.imread(path)
        return self.detect_faces(image)

    def from_base64_jpeg(self, image):
        jpg_bytes = base64.b64decode(image)
        image_array = np.frombuffer(jpg_bytes, dtype=np.uint8)
        image = cv2.imdecode(image_array, flags=1)
        return self.detect_faces(image)

    def detect_faces(self, image):
        grayed = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        return self.cascade_detector.detectMultiScale(grayed, scaleFactor=1.1, minNeighbors=4)
