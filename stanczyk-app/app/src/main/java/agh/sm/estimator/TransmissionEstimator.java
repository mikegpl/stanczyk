package agh.sm.estimator;

import agh.sm.metrics.MetricCollector;

public class TransmissionEstimator {

    private final MetricCollector metricCollector;

    public TransmissionEstimator(MetricCollector metricCollector) {
        this.metricCollector = metricCollector;
    }

    /*
     * TODO : Decide how to get network speeds
     *  directly from MetricCollector, or preprocessed from DeviceParameters
     */
    public double getTransmissionTime(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        return taskParameters.getTaskSize() / (metricCollector.getNetworkDownloadSpeed() + metricCollector.getNetworkUploadSpeed());
    }
}
