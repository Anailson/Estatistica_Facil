package com.sharktech.projectprob.analyse;

import java.util.ArrayList;

class DataAnalyseCalc {

    static Double asymmetry(ArrayList<DataAnalyseValue> values){

        double sumFrequency = sumFrequency(values);
        Double fstQuart = elemBeforeValue(values, indexFirstQuart(sumFrequency));
        Double secQuart = elemBeforeValue(values, indexSecondQuart(sumFrequency));
        Double trdQuart = elemBeforeValue(values, indexThirdQuart(sumFrequency));
        return (fstQuart == null || secQuart == null ||trdQuart == null) ? null
                : (trdQuart + fstQuart - 2 * secQuart) / (trdQuart - fstQuart);
    }

    static Double kurtosis(ArrayList<DataAnalyseValue> values){
        double sumFrequency = sumFrequency(values);
        Double fstQuart = elemBeforeValue(values, indexFirstQuart(sumFrequency));
        Double trdQuart = elemBeforeValue(values, indexThirdQuart(sumFrequency));
        Double fstTenth = elemBeforeValue(values, indexFirstTenth(sumFrequency));
        Double nthTenth = elemBeforeValue(values, indexNinthTenth(sumFrequency));
        return (fstQuart == null || trdQuart == null || fstTenth == null || nthTenth == null) ? null
                : (trdQuart - fstQuart) / (2 * (nthTenth - fstTenth));
    }

    private static double sumFrequency(ArrayList<DataAnalyseValue> values){
        double sumFrequency = 0;
        for(DataAnalyseValue value : values){
            sumFrequency += value.getFrequency();
        }
        return sumFrequency;
    }

    private static double indexFirstQuart(double sumFrequency){
        return (sumFrequency + 1) / 4;
    }

    private static double indexSecondQuart(double sumFrequency){
        return (sumFrequency + 1) / 2;
    }

    private static double indexThirdQuart(double sumFrequency){
        return 3 * indexFirstQuart(sumFrequency);
    }

    private static double indexFirstTenth(double sumFrequency){
        return (sumFrequency + 1) / 4;
    }

    private static double indexNinthTenth(double sumFrequency){
        return (sumFrequency + 1) / 2;
    }

    private static Double elemBeforeValue(ArrayList<DataAnalyseValue>values, double value){
        double accumulated = 0;

        for(DataAnalyseValue data : values){

            if(data.isNumber()) {
                accumulated += data.getFrequency();
                if (value <= accumulated) {
                    return data.asNumber();
                }
            }
        }
        return null;
    }
}
