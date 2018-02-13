package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;

import java.util.ArrayList;
import java.util.HashMap;

public class DataAnalyseDetails {

    public enum Details {
        ARITHMETIC, POUND_ARITHMETIC, GEOMETRIC, POUND_GEOMETRIC,
        WEIGHTED, POUND_WEIGHTED, QUADRATIC, POUND_QUADRATIC,
        ASYMMETRY, KURTOSIS
    }

    private boolean mCalculated;
    private ArrayList<TableCell.ICell> mModes;
    private HashMap<Details, Double> mDetails;

    DataAnalyseDetails() {
        mCalculated = false;
        mModes = new ArrayList<>();
        mDetails = new HashMap<>();
    }

    public Double get(Details key) {
        verifyIsCalculated();
        return mDetails.get(key);
    }

    public ArrayList<TableCell.ICell> getModes(){
        verifyIsCalculated();
        return mModes;
    }

    void calculate(boolean isNumber, SortedGenericList<DataAnalyseValue> values){

        long frequency = 0;
        Double sumFrequency = 0d, sumArithmetic = 0d, sumPoundArithmetic = 0d,
                prodGeometric = 1d, prodPoundGeometric = 1d,
                sumWeighted = 0d, sumPoundWeighted = 0d,
                sumQuadratic = 0d, sumPoundQuadratic = 0d;
        ArrayList<DataAnalyseValue> list = values.asList();
        for(DataAnalyseValue value : list){

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


        if(isNumber){
            int n = values.asList().size();
            double avgGeo = Math.pow(prodGeometric, (1 / n));
            double avgGeoPound = Math.pow(prodPoundGeometric, (1 / sumFrequency.floatValue()));
            mDetails.put(Details.ARITHMETIC, sumArithmetic / n);
            mDetails.put(Details.POUND_ARITHMETIC, sumPoundArithmetic / sumFrequency);
            mDetails.put(Details.GEOMETRIC, avgGeo);
            mDetails.put(Details.POUND_GEOMETRIC, avgGeoPound);
            mDetails.put(Details.WEIGHTED, sumWeighted != 0 ? n / sumWeighted : 0);
            mDetails.put(Details.POUND_WEIGHTED, sumPoundWeighted != 0 ? sumFrequency / sumPoundWeighted : 0);
            mDetails.put(Details.QUADRATIC, Math.sqrt(sumQuadratic));
            mDetails.put(Details.POUND_QUADRATIC, Math.sqrt(sumPoundQuadratic));
        }
        mDetails.put(Details.ASYMMETRY, DataAnalyseCalc.asymmetry(list));
        mDetails.put(Details.KURTOSIS, DataAnalyseCalc.kurtosis(list));

        mCalculated = true;
    }
    public boolean hasMode(){
        return mModes.size() > 0;
    }

    private void verifyIsCalculated(){
        if(!mCalculated) throw new IllegalStateException("You don't calculate values yet. " +
                "You must call calculate() before call get(AverageKey) or getModes().");
    }
}
