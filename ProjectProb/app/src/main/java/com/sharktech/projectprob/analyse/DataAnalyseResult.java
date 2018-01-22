package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableObject;

import java.util.ArrayList;
import java.util.HashMap;


class DataAnalyseResult {

    enum ValueKey{
        DATA, FREQUENCY, PROD_VAL_FREQ, POW_VAL, POW_VAL_FREQ,
        DIV_BY_VAL, DIV_FREQ_VAL, SQRT_VAL, PROD_SQRT_VAL_FREQ
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
        mMode = new VariableObject.ValueObject();

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

    void calculate(ArrayList<DataAnalyseValue> values){

        TableCell.ICell mode = null;
        long frequency = 0;
        Double sumFrequency = 0d, sumArithmetic = 0d, sumPoundArithmetic = 0d,
                prodGeometric = 1d, prodPoundGeometric = 1d,
                sumWeighted = 0d, sumPoundWeighted = 0d,
                sumQuadratic = 0d, sumPoundQuadratic = 0d;

        for (DataAnalyseValue data : values){
            if(data.getFrequency() > frequency){
                mode = data.getValue();
                frequency = data.getFrequency();
            }

            if(data.isNumber()){
                sumFrequency += data.getFrequency();
                sumArithmetic += data.asNumber();                               //ARITHMETIC
                sumPoundArithmetic += data.prodValFreq();                       //POUND_ARITHMETIC
                prodGeometric *= data.asNumber();                               //GEOMETRIC
                prodPoundGeometric *= data.powValFreq();                        //POUND_GEOMETRIC
                sumWeighted += data.divByVal();                                 //WEIGHTED
                sumPoundWeighted += data.divFreqVal();                          //POUND_WEIGHTED
                sumQuadratic += data.sqrtVal();                                 //QUADRATIC
                sumPoundQuadratic += data.prodSqrtValFreq();                    //POUND_QUADRATIC
            }

            //Values
            add(ValueKey.DATA, data.getValue());
            add(ValueKey.FREQUENCY, new VariableNumber.ValueInteger(data.getFrequency()));
            add(ValueKey.PROD_VAL_FREQ, new VariableNumber.ValueInteger(data.prodValFreq()));
            add(ValueKey.POW_VAL, new VariableNumber.ValueInteger(data.asNumber()));
            add(ValueKey.POW_VAL_FREQ, new VariableNumber.ValueInteger(data.powValFreq()));
            add(ValueKey.DIV_BY_VAL, new VariableNumber.ValueInteger(data.divByVal()));
            add(ValueKey.DIV_FREQ_VAL, new VariableNumber.ValueInteger(data.divFreqVal()));
            add(ValueKey.SQRT_VAL, new VariableNumber.ValueInteger(data.sqrtVal()));
            add(ValueKey.PROD_SQRT_VAL_FREQ, new VariableNumber.ValueInteger(data.prodSqrtValFreq()));
        }

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
        double avgGeo = Math.pow(prodGeometric, (1 / values.size()));
        double avgGeoPound = Math.pow(prodPoundGeometric, (1 / sumFrequency));
        add(AverageKey.ARITHMETIC, sumArithmetic / values.size());
        add(AverageKey.POUND_ARITHMETIC, sumPoundArithmetic / sumFrequency);
        add(AverageKey.GEOMETRIC, avgGeo);
        add(AverageKey.POUND_GEOMETRIC, avgGeoPound);
        add(AverageKey.WEIGHTED, sumWeighted != 0 ? values.size() / sumWeighted : 0);
        add(AverageKey.POUND_WEIGHTED, sumPoundWeighted != 0 ? sumFrequency / sumPoundWeighted : 0);
        add(AverageKey.QUADRATIC, Math.sqrt(sumQuadratic));
        add(AverageKey.POUND_QUADRATIC, Math.sqrt(sumPoundQuadratic));
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

    private void add(ValueKey key, Double value){
        add(key, new VariableNumber.ValueInteger(value));
    }

    private void add(ValueKey key, TableCell.ICell cell){
        mResultMap.get(key).add(cell);
    }

    private void add(AverageKey key, Double value){
        mAverages.put(key, value);
    }
}
