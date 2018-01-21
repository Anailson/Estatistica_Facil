package com.sharktech.projectprob.analyse;

import android.util.Log;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.*;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.*;

class DataAnalyseResult {

    enum ValueKey{
        DATA, FREQUENCY , PROD_VAL_FREQ, PROD_VAL_SQRT_FREQ
    }

    enum AverageKey{
        ARITHMETIC, POUND_ARITHMETIC, GEOMETRIC, POUND_GEOMETRIC,
        WEIGHTED, POUND_WEIGHTED, QUADRATIC, POUND_QUADRATIC
    }


    private TableCell.ICell mMode;
    private HashMap<AverageKey, Double> mAverages;
    private HashMap<ValueKey, ArrayList<TableCell.ICell>> mResultMap;


    DataAnalyseResult() {
        this.mMode = new VariableObject.ValueObject();
        this.mAverages = new HashMap<>();
        this.mResultMap = new HashMap<>();

        mResultMap.put(DATA, new ArrayList<TableCell.ICell>());
        mResultMap.put(FREQUENCY, new ArrayList<TableCell.ICell>());
        mResultMap.put(PROD_VAL_FREQ, new ArrayList<TableCell.ICell>());
        mResultMap.put(PROD_VAL_SQRT_FREQ, new ArrayList<TableCell.ICell>());
    }

    void clear(){
        mMode = new VariableObject.ValueObject();

        mResultMap.get(DATA).clear();
        mResultMap.get(FREQUENCY).clear();
        mResultMap.get(PROD_VAL_FREQ).clear();
        mResultMap.get(PROD_VAL_SQRT_FREQ).clear();
    }

    void calculate(ArrayList<DataAnalyseValue> values){
        TableCell.ICell mode = null;
        long frequency = 0, sumFrequency = 0;

        Double sumArithmetic = 0d, sumPoundArithmetic = 0d,
                prodGeometric = 1d, prodPoundGeometric = 1d,
                sumWeighted = 0d, sumPoundWeighted = 0d,
                sumQuadratic = 0d, sumPoundQuadratic = 0d;

        for (DataAnalyseValue data : values){
            if(data.getFrequency() > frequency){
                mode = data.getValue();
                frequency = data.getFrequency();
            }

            sumFrequency += data.getFrequency();
            sumArithmetic += data.isNumber() ? data.asNumber() : 0d;        //ARITHMETIC
            sumPoundArithmetic += data.prodValFreq();                       //POUND_ARITHMETIC
            prodGeometric *= data.isNumber() ? data.asNumber() : 1d;        //GEOMETRIC
            prodPoundGeometric *= data.powValFreq();                        //POUND_GEOMETRIC
            sumWeighted += data.isNumber() ? data.divByVal() : 0d;          //WEIGHTED
            sumPoundWeighted += data.divFreqVal();                          //POUND_WEIGHTED
            sumQuadratic += data.isNumber() ? data.sqrtVal() : 0d;          //QUADRATIC
            sumPoundQuadratic += data.prodSqrtValFreq();                    //POUND_QUADRATIC


            //Values
            add(DATA, data.getValue());
            add(FREQUENCY, new VariableNumber.ValueInteger(data.getFrequency()));
            add(PROD_VAL_FREQ, new VariableNumber.ValueInteger(data.prodValFreq()));
            add(PROD_VAL_SQRT_FREQ, new VariableNumber.ValueInteger(data.prodValSqrtFreq()));
        }
        //Averages
        add(ARITHMETIC, sumArithmetic / values.size());
        add(POUND_ARITHMETIC, sumPoundArithmetic / sumFrequency);
        add(GEOMETRIC, Math.pow(prodGeometric, (1 / values.size())));
        add(POUND_GEOMETRIC, Math.pow(prodPoundGeometric, (1 / sumFrequency)));
        add(WEIGHTED, sumWeighted != 0 ? values.size() / sumWeighted : 0);
        add(POUND_WEIGHTED, sumPoundWeighted != 0 ? sumFrequency / sumPoundWeighted : 0);
        add(QUADRATIC, Math.sqrt(sumQuadratic));
        add(POUND_QUADRATIC, Math.sqrt(sumPoundQuadratic));
        setMode(mode);
    }

    TableCell.ICell getMode() {
        return mMode;
    }

    Double get(AverageKey key){
        return mAverages.get(key);
    }

    TableCell.ICell get(ValueKey key, int index){
        return mResultMap.get(key).get(index);
    }

    ArrayList<TableCell.ICell> get(ValueKey key){
        return mResultMap.get(key);
    }

    private void setMode(TableCell.ICell mode) {
        if(mode == null) throw new IllegalArgumentException("Mode cannot be null");
        this.mMode = mode;
    }

    private void add(ValueKey key, TableCell.ICell cell){
        mResultMap.get(key).add(cell);
    }

    private void add(AverageKey key, Double value){
        mAverages.put(key, value);
    }
}