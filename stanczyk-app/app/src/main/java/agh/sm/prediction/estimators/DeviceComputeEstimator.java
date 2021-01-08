package agh.sm.prediction.estimators;


import java.util.Collections;
import java.util.List;

import agh.sm.prediction.params.DeviceParameters;
import agh.sm.prediction.params.KnnX;
import agh.sm.prediction.params.TaskParameters;
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

    public double getDeviceComputeTime(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        return 2137.0;
    }

    public double predictDeviceComputeTime(TaskParameters taskParameters, DeviceParameters deviceParameters) {
        KnnX knnX = new KnnX(taskParameters, deviceParameters);
        return regresor.predict(DataFrame.of(Collections.singletonList(knnX), KnnX.class))[0];
    }

    public void learn(List<KnnX> knowledge) {
        Formula formula = Formula.lhs("y"); // TODO : Co to xd
        DataFrame df = DataFrame.of(knowledge, KnnX.class);
        regresor = OLS.fit(formula, df);
    }
}
