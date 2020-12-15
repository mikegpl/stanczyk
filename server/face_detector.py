import cv2


class FaceDetector:
    def __init__(self):
        self.cascade_detector = cv2.CascadeClassifier('../resources/haarcascade_config.xml')

    def from_file(self, path):
        image = cv2.imread(path)
        return self.detect_faces(image)

    def detect_faces(self, image):
        grayed = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        return self.cascade_detector.detectMultiScale(grayed, scaleFactor=1.1, minNeighbors=4)
