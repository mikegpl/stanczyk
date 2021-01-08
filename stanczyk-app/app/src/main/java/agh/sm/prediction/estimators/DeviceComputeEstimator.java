package agh.sm.prediction.estimators;


import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.TaskParameters;
import smile.classification.KNN;
import smile.math.distance.Distance;
import smile.neighbor.KNNSearch;

/*
 * Predict how hard given task is
 */
public class DeviceComputeEstimator {

    private KNN<X> knn;

    public DeviceComputeEstimator() {
    }

    public double getDeviceComputeTime(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        return 2137.0;
    }

    public double predictDeviceComputeTime(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        return 2137.0;
    }

    /*
     * TODO : provide knowledge as parameters
     *  or store knowledge in the object and just call learn()
     */
    public void learn(X[] x, int[] y) {
        knn = KNN.fit(x, y, 3, getDistance());

    }

    private Distance<X> getDistance() {
        /*
         * TODO : Implemnt real distance metric
         */
        return (x, y) -> {
            double distance = 0;
            distance += Math.abs(x.deviceParameters.getCpuCount() - y.deviceParameters.getCpuCount())*10; // CPU distance
            distance *= (double) x.taskParameters.getTaskSize() / y.taskParameters.getTaskSize();
            return distance;
        };
    }

    private class X {
        private final TaskParameters taskParameters;
        private final DeviceParameters deviceParameters;

        public X(TaskParameters taskParameters, DeviceParameters deviceParameters) {
            this.taskParameters = taskParameters;
            this.deviceParameters = deviceParameters;
        }

        public TaskParameters getTaskParameters() {
            return taskParameters;
        }

        public DeviceParameters getDeviceParameters() {
            return deviceParameters;
        }
    }
}
