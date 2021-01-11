package agh.sm.prediction.estimators;


import java.util.Collections;
import java.util.List;

import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.KnnX;
import agh.sm.prediction.params.TaskParametersMetadata;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.regression.LinearModel;
import smile.regression.OLS;


/*
 * Predict how hard given task is
 */
public class DeviceComputeEstimator {

    private LinearModel regresor;

    public DeviceComputeEstimator() {
    }

    public double getDeviceComputeTime(TaskParametersMetadata taskParametersMetadata, DeviceParameters deviceParameters) {
        return 2137.0;
    }

    public double predictDeviceComputeTime(TaskParametersMetadata taskParametersMetadata, DeviceParameters deviceParameters) {
        KnnX knnX = new KnnX(taskParametersMetadata, deviceParameters);
        DataFrame df = DataFrame.of(Collections.singletonList(knnX), KnnX.class);
        return regresor.predict(df)[0];
    }

    public void learn(List<KnnX> labels, long[] features) {
        Formula formula = Formula.lhs("y"); // TODO : Co to xd
        DataFrame df = DataFrame.of(labels, KnnX.class);
        regresor = OLS.fit(formula, df);
    }
}
