package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sharktech.projectprob.analyse.DataAnalyseTable.ValueKey.*;

public class DataAnalyseTable {

    public enum ValueKey {
        DATA, FREQUENCY, FREQUENCY_ACCUMULATED, PROD_VAL_FREQ, POW_VAL, POW_VAL_FREQ,
        DIV_BY_VAL, DIV_FREQ_VAL, SQRT_VAL, PROD_SQRT_VAL_FREQ
    }

    private HashMap<ValueKey, ArrayList<TableCell.ICell>> mResultMap;

    DataAnalyseTable (){
        mResultMap = new HashMap<>();

        mResultMap.put(DATA, new ArrayList<TableCell.ICell>());
        mResultMap.put(FREQUENCY, new ArrayList<TableCell.ICell>());
        mResultMap.put(FREQUENCY_ACCUMULATED, new ArrayList<TableCell.ICell>());
        mResultMap.put(PROD_VAL_FREQ, new ArrayList<TableCell.ICell>());
        mResultMap.put(POW_VAL, new ArrayList<TableCell.ICell>());
        mResultMap.put(POW_VAL_FREQ, new ArrayList<TableCell.ICell>());
        mResultMap.put(DIV_BY_VAL, new ArrayList<TableCell.ICell>());
        mResultMap.put(DIV_FREQ_VAL, new ArrayList<TableCell.ICell>());
        mResultMap.put(SQRT_VAL, new ArrayList<TableCell.ICell>());
        mResultMap.put(PROD_SQRT_VAL_FREQ, new ArrayList<TableCell.ICell>());
    }

    void calculate(boolean isNumber, SortedGenericList<DataAnalyseValue> values){

        Double sumFrequency = 0d, sumArithmetic = 0d, sumPoundArithmetic = 0d,
                prodGeometric = 1d, prodPoundGeometric = 1d,
                sumWeighted = 0d, sumPoundWeighted = 0d,
                sumQuadratic = 0d, sumPoundQuadratic = 0d;

        for (DataAnalyseValue data : values.asList()) {

            sumFrequency += data.getFrequency();
            add(FREQUENCY, (double) data.getFrequency());
            add(FREQUENCY_ACCUMULATED, sumFrequency);
            add(DATA, data.getValue());
            if(isNumber){
                sumArithmetic += data.asNumber();                               //ARITHMETIC
                sumPoundArithmetic += data.prodValFreq();                       //POUND_ARITHMETIC
                prodGeometric *= data.asNumber();                               //GEOMETRIC
                prodPoundGeometric *= data.powValFreq();                        //POUND_GEOMETRIC
                sumWeighted += data.divByVal();                                 //WEIGHTED
                sumPoundWeighted += data.divFreqVal();                          //POUND_WEIGHTED
                sumQuadratic += data.sqrtVal();                                 //QUADRATIC
                sumPoundQuadratic += data.prodSqrtValFreq();                    //POUND_QUADRATIC

                add(PROD_VAL_FREQ, data.prodValFreq());
                add(POW_VAL, data.asNumber());
                add(POW_VAL_FREQ, data.powValFreq());
                add(DIV_BY_VAL, data.divByVal());
                add(DIV_FREQ_VAL, data.divFreqVal());
                add(SQRT_VAL, data.sqrtVal());
                add(PROD_SQRT_VAL_FREQ, data.prodSqrtValFreq());
            }
        }
        String empty = " - - - ";
        if(isNumber) {
            add(DATA, sumArithmetic);
            add(PROD_VAL_FREQ, sumPoundArithmetic);
            add(POW_VAL, prodGeometric);
            add(POW_VAL_FREQ, prodPoundGeometric);
            add(DIV_BY_VAL, sumWeighted);
            add(DIV_FREQ_VAL, sumPoundWeighted);
            add(SQRT_VAL, sumQuadratic);
            add(PROD_SQRT_VAL_FREQ, sumPoundQuadratic);
        }else{
            add(DATA, empty);
        }
        add(FREQUENCY_ACCUMULATED, empty);
        add(FREQUENCY, sumFrequency);
    }

    public ArrayList<TableCell.ICell> get(ValueKey key){
        return mResultMap.get(key);
    }

    private void add(ValueKey key, Double value){
        mResultMap.get(key).add(new VariableNumber.ValueInteger(value));
    }

    private void add(ValueKey key, String value){
        mResultMap.get(key).add(new VariableString.ValueString(value));
    }

    private void add(ValueKey key, TableCell.ICell cell){
        mResultMap.get(key).add(cell);
    }
}
