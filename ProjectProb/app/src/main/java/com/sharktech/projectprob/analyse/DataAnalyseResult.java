package com.sharktech.projectprob.analyse;

import android.util.Log;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;
import java.util.HashMap;

class DataAnalyseResult {

    enum ValueKey {
        DATA, FREQUENCY, PROD_VAL_FREQ, POW_VAL, POW_VAL_FREQ,
        DIV_BY_VAL, DIV_FREQ_VAL, SQRT_VAL, PROD_SQRT_VAL_FREQ
    }
/*
    enum AverageKey {
        ARITHMETIC, POUND_ARITHMETIC, GEOMETRIC, POUND_GEOMETRIC,
        WEIGHTED, POUND_WEIGHTED, QUADRATIC, POUND_QUADRATIC
    }
*/

    private boolean mIsNumber;
    private SortedCellsList mSortedList;
    //private ArrayList<TableCell.ICell> mModes;
    //private HashMap<AverageKey, Double> mAverages;
    private HashMap<ValueKey, ArrayList<TableCell.ICell>> mResultMap;

    DataAnalyseResult() {
        mSortedList = new SortedCellsList();
        //mModes = new ArrayList<>();
        //mAverages = new HashMap<>();
        mResultMap = new HashMap<>();

        mResultMap.put(ValueKey.DATA, new ArrayList<TableCell.ICell>());
        mResultMap.put(ValueKey.FREQUENCY, new ArrayList<TableCell.ICell>());
        mResultMap.put(ValueKey.PROD_VAL_FREQ, new ArrayList<TableCell.ICell>());
        mResultMap.put(ValueKey.POW_VAL, new ArrayList<TableCell.ICell>());
        mResultMap.put(ValueKey.POW_VAL_FREQ, new ArrayList<TableCell.ICell>());
        mResultMap.put(ValueKey.DIV_BY_VAL, new ArrayList<TableCell.ICell>());
        mResultMap.put(ValueKey.DIV_FREQ_VAL, new ArrayList<TableCell.ICell>());
        mResultMap.put(ValueKey.SQRT_VAL, new ArrayList<TableCell.ICell>());
        mResultMap.put(ValueKey.PROD_SQRT_VAL_FREQ, new ArrayList<TableCell.ICell>());
    }

    void clear(){
        mSortedList.clear();
        //mModes.clear();

        mResultMap.get(ValueKey.DATA).clear();
        mResultMap.get(ValueKey.FREQUENCY).clear();
        mResultMap.get(ValueKey.PROD_VAL_FREQ).clear();
        mResultMap.get(ValueKey.POW_VAL).clear();
        mResultMap.get(ValueKey.POW_VAL_FREQ).clear();
        mResultMap.get(ValueKey.DIV_BY_VAL).clear();
        mResultMap.get(ValueKey.DIV_FREQ_VAL).clear();
        mResultMap.get(ValueKey.SQRT_VAL).clear();
        mResultMap.get(ValueKey.PROD_SQRT_VAL_FREQ).clear();
    }

    boolean init(TableColumn.IVariable variable){
        if(variable == null) return false;
        mIsNumber = variable.isNumber();
        return mSortedList.init(variable);
    }

    void calculate(){

        long frequency = 0;
        Double sumFrequency = 0d, sumArithmetic = 0d, sumPoundArithmetic = 0d,
                prodGeometric = 1d, prodPoundGeometric = 1d,
                sumWeighted = 0d, sumPoundWeighted = 0d,
                sumQuadratic = 0d, sumPoundQuadratic = 0d;
        mSortedList.quarterValue(SortedCellsList.Quart.FIRST);
        mSortedList.quarterValue(SortedCellsList.Quart.SECOND);
        mSortedList.quarterValue(SortedCellsList.Quart.THIRD);

        for (DataAnalyseValue data : mSortedList.getValues()) {
            /*
            if (data.getFrequency() == frequency) {
                mModes.add(data.getValue());
            } else if (data.getFrequency() > frequency) {
                mModes.clear();
                mModes.add(data.getValue());
                frequency = data.getFrequency();
            }
            */

            if (mIsNumber) {
                sumFrequency += data.getFrequency();
                sumArithmetic += data.asNumber();                               //ARITHMETIC
                sumPoundArithmetic += data.prodValFreq();                       //POUND_ARITHMETIC
                prodGeometric *= data.asNumber();                               //GEOMETRIC
                prodPoundGeometric *= data.powValFreq();                        //POUND_GEOMETRIC
                sumWeighted += data.divByVal();                                 //WEIGHTED
                sumPoundWeighted += data.divFreqVal();                          //POUND_WEIGHTED
                sumQuadratic += data.sqrtVal();                                 //QUADRATIC
                sumPoundQuadratic += data.prodSqrtValFreq();                    //POUND_QUADRATIC

                //Values
                add(ValueKey.POW_VAL, data.asNumber());
                add(ValueKey.POW_VAL_FREQ, data.powValFreq());
                add(ValueKey.DIV_BY_VAL, data.divByVal());
                add(ValueKey.DIV_FREQ_VAL, data.divFreqVal());
                add(ValueKey.SQRT_VAL, data.sqrtVal());
                add(ValueKey.PROD_SQRT_VAL_FREQ, data.prodSqrtValFreq());
            } else{
                String val = " - - - ";
                add(ValueKey.FREQUENCY, val);
                add(ValueKey.PROD_VAL_FREQ, val);
                add(ValueKey.POW_VAL, val);
                add(ValueKey.POW_VAL_FREQ, val);
                add(ValueKey.DIV_BY_VAL, val);
                add(ValueKey.DIV_FREQ_VAL, val);
                add(ValueKey.SQRT_VAL, val);
                add(ValueKey.PROD_SQRT_VAL_FREQ, val);
            }
            add(ValueKey.DATA, data.getValue());
            add(ValueKey.FREQUENCY, (double) data.getFrequency());
        }
        if(prodGeometric == 1) prodGeometric = -1d;
        if(prodPoundGeometric == 1) prodPoundGeometric = -1d;

        //Value accumulate (sum and prod)
        add(ValueKey.DATA, sumArithmetic);
        add(ValueKey.FREQUENCY, sumFrequency);
        add(ValueKey.PROD_VAL_FREQ, sumPoundArithmetic);
        add(ValueKey.POW_VAL, prodGeometric);
        add(ValueKey.POW_VAL_FREQ, prodPoundGeometric);
        add(ValueKey.DIV_BY_VAL, sumWeighted);
        add(ValueKey.DIV_FREQ_VAL, sumPoundWeighted);
        add(ValueKey.SQRT_VAL, sumQuadratic);
        add(ValueKey.PROD_SQRT_VAL_FREQ, sumPoundQuadratic);

        //Averages
        //TODO verify how to calculate root n.
        double avgGeo = Math.pow(prodGeometric, (1 / mSortedList.valuesSize()));
        double avgGeoPound = Math.pow(prodPoundGeometric, (1 / sumFrequency));
        Log.e("ROOT_N", "avgGeo " + avgGeo + " avgGeoPound " + avgGeoPound);
        /*
        add(AverageKey.ARITHMETIC, sumArithmetic / mSortedList.valuesSize());
        add(AverageKey.POUND_ARITHMETIC, sumPoundArithmetic / sumFrequency);
        add(AverageKey.GEOMETRIC, avgGeo);
        add(AverageKey.POUND_GEOMETRIC, avgGeoPound);
        add(AverageKey.WEIGHTED, sumWeighted != 0 ? mSortedList.valuesSize() / sumWeighted : 0);
        add(AverageKey.POUND_WEIGHTED, sumPoundWeighted != 0 ? sumFrequency / sumPoundWeighted : 0);
        add(AverageKey.QUADRATIC, Math.sqrt(sumQuadratic));
        add(AverageKey.POUND_QUADRATIC, Math.sqrt(sumPoundQuadratic));
        */
    }

    int size(){
        return mSortedList.valuesSize();
    }
/*
    ArrayList<TableCell.ICell> getMode() {
        return mModes;
    }

    int nMode() {
        return mModes.size();
    }

    Double get(AverageKey key) {
        return mAverages.get(key);
    }
*/
    TableCell.ICell get(ValueKey key, int index) {
        return mResultMap.get(key).get(index);
    }

    ArrayList<TableCell.ICell> get(ValueKey key) {
        return mResultMap.get(key);
    }

    private void add(ValueKey key, Double value) {
        add(key, new VariableNumber.ValueInteger(value));
    }

    private void add(ValueKey key, String value) {
        add(key, new VariableString.ValueString(value));
    }

    private void add(ValueKey key, TableCell.ICell cell) {
        mResultMap.get(key).add(cell);
    }
/*
    private void add(AverageKey key, Double value) {
        mAverages.put(key, value);
    }
    */
}
