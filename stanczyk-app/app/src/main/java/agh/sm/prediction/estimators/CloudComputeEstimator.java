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

public class CloudComputeEstimator {

    private LinearModel regresor;

    public long getCloudComputeTime(TaskParametersMetadata taskParametersMetadata) {
        return 1;
    }

    public double predictCloudComputeTime(TaskParametersMetadata taskParametersMetadata, DeviceParameters deviceParameters) {
        KnnX knnX = new KnnX(taskParametersMetadata, deviceParameters);
        return regresor.predict(DataFrame.of(Collections.singletonList(knnX), KnnX.class))[0];
    }

    public void learn(List<KnnX> knowledge) {
        Formula formula = Formula.lhs("y"); // TODO : Co to xd
        DataFrame df = DataFrame.of(knowledge, KnnX.class);
        regresor = OLS.fit(formula, df);
    }
}
