package com.sharktech.projectprob.analyse;

class TableDistribution {

    private TableDistribution(){}

    static float normal(float value) {
        return (value > TableNormal.lastValue()) ? 3.10f : TableNormal.zValue(value);
    }

    static float quiQuadratic(float confidence, int libertyDegree){
        int col = confidence == 0.995f ? 0
                : confidence == 0.99f ? 1
                : confidence == 0.975f ? 2
                : confidence == 0.95f ? 3
                : confidence == 0.9f ? 4
                : confidence == 0.8f ? 5
                : confidence == 0.75f ? 6
                : confidence == 0.25f ? 7
                : confidence == 0.2f ? 8
                : confidence == 0.1f ? 9
                : confidence == 0.05f ? 10
                : confidence == 0.025f ? 11
                : confidence == 0.01f ? 12
                : confidence == 0.005f ? 13 : -1;
        int row = (libertyDegree >= 1 && libertyDegree <= 30) ? libertyDegree - 1   // 0~29
                : (libertyDegree == 35) ? 30
                : (libertyDegree == 40) ? 31 : -1;
        return TableQuiQuadratic.quiValue(row, col);
    }
}
