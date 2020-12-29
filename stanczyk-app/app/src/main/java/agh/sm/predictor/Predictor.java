package agh.sm.predictor;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Predictor {

    private final String TAG = "Predictor";

    public enum ExecutionTarget {
        DEVICE,
        CLOUD;
    }

    private List<Pair<Knowledge, Boolean>> pastPredictions;

    public Predictor() {
        pastPredictions = new ArrayList<>();
    }

    public ExecutionTarget predict(Knowledge taskKnowledge) {
        if (taskKnowledge.getCpu().equals(Knowledge.Cpu.FAST)) {
            return ExecutionTarget.DEVICE;
        }
        if (taskKnowledge.getCpu().equals(Knowledge.Cpu.SLOW) &&
            taskKnowledge.getNetworkSpeed().equals(Knowledge.NetworkSpeed.FAST)) {
            return ExecutionTarget.CLOUD;
        }
        return ExecutionTarget.DEVICE;
    }

    /*
     * Update predictor with cloud knowledge
     */
    public void teachCloud() {
        return;
    }

    /*
     * Save results from locally executed task
     */
    public void teachDevice(long executionTime, long energyDelta) {
        Log.i(TAG, "Teaching Local Model time : " + executionTime + " | energy : " + energyDelta);
        return;
    }
}
