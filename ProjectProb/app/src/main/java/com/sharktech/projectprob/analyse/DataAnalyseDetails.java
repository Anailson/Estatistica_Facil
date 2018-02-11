package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.ArrayList;
import java.util.HashMap;

public class DataAnalyseDetails {

    public enum AverageKey {
        ARITHMETIC, POUND_ARITHMETIC, GEOMETRIC, POUND_GEOMETRIC,
        WEIGHTED, POUND_WEIGHTED, QUADRATIC, POUND_QUADRATIC
    }

    private ArrayList<TableCell.ICell> mModes;
    private HashMap<AverageKey, Double> mAverages;

    public DataAnalyseDetails() {
        mModes = new ArrayList<>();
        mAverages = new HashMap<>();
    }

    public Double get(AverageKey key) {
        return mAverages.get(key);
    }

    public ArrayList<TableCell.ICell> getModes(){
        return mModes;
    }

    void calculate(boolean isNumber, SortedGenericList<DataAnalyseValue> values){

        long frequency = 0;
        Double sumFrequency = 0d, sumArithmetic = 0d, sumPoundArithmetic = 0d,
                prodGeometric = 1d, prodPoundGeometric = 1d,
                sumWeighted = 0d, sumPoundWeighted = 0d,
                sumQuadratic = 0d, sumPoundQuadratic = 0d;

        for(DataAnalyseValue value : values.asList()){

            if (value.getFrequency() == frequency) {
                mModes.add(value.getValue());
            } else if (value.getFrequency() > frequency) {
                mModes.clear();
                mModes.add(value.getValue());
                frequency = value.getFrequency();
            }

            if(isNumber){
                sumFrequency += value.getFrequency();
                sumArithmetic += value.asNumber();                               //ARITHMETIC
                sumPoundArithmetic += value.prodValFreq();                       //POUND_ARITHMETIC
                prodGeometric *= value.asNumber();                               //GEOMETRIC
                prodPoundGeometric *= value.powValFreq();                        //POUND_GEOMETRIC
                sumWeighted += value.divByVal();                                 //WEIGHTED
                sumPoundWeighted += value.divFreqVal();                          //POUND_WEIGHTED
                sumQuadratic += value.sqrtVal();                                 //QUADRATIC
                sumPoundQuadratic += value.prodSqrtValFreq();                    //POUND_QUADRATIC
            }
        }
        int n = values.asList().size();

        double avgGeo = Math.pow(prodGeometric, (1 / n));
        double avgGeoPound = Math.pow(prodPoundGeometric, (1 / sumFrequency));
        mAverages.put(AverageKey.ARITHMETIC, sumArithmetic / n);
        mAverages.put(AverageKey.POUND_ARITHMETIC, sumPoundArithmetic / sumFrequency);
        mAverages.put(AverageKey.GEOMETRIC, avgGeo);
        mAverages.put(AverageKey.POUND_GEOMETRIC, avgGeoPound);
        mAverages.put(AverageKey.WEIGHTED, sumWeighted != 0 ? n / sumWeighted : 0);
        mAverages.put(AverageKey.POUND_WEIGHTED, sumPoundWeighted != 0 ? sumFrequency / sumPoundWeighted : 0);
        mAverages.put(AverageKey.QUADRATIC, Math.sqrt(sumQuadratic));
        mAverages.put(AverageKey.POUND_QUADRATIC, Math.sqrt(sumPoundQuadratic));
    }
    public boolean hasMode(){
        return mModes.size() > 0;
    }
}
