package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sharktech.projectprob.analyse.DataAnalyse.Sum;
import static com.sharktech.projectprob.analyse.DataAnalyse.ValueKey;

public class DataAnalyseResult {

    private SortedDataAnalyseValueList mValues;
    private ArrayList<TableCell.ICell> mModes;
    private double mBiggerFreq;
    private HashMap<Sum, Double> mSum;
    private HashMap<ValueKey, ArrayList<TableCell.ICell>> mResultMap;

    DataAnalyseResult() {

        mValues = new SortedDataAnalyseValueList();
        mModes = new ArrayList<>();
        mSum = new HashMap<>();
        mResultMap = new HashMap<>();
        mBiggerFreq = 0;

        defaultValues();
    }

    private void defaultValues() {

        mSum.put(Sum.SUM_VALUES, 0d);
        mSum.put(Sum.SUM_FREQUENCY, 0d);
        mSum.put(Sum.SUM_VAL_MULTI_FREQ, 0d);
        mSum.put(Sum.PROD_VALUES, 1d);
        mSum.put(Sum.PROD_VAL_POW_FREQ, 1d);
        mSum.put(Sum.SUM_ONE_DIV_VAL, 0d);
        mSum.put(Sum.SUM_FREQ_DIV_VAL, 0d);
        mSum.put(Sum.SUM_SQRT_VAL, 0d);
        mSum.put(Sum.SUM_SQRT_VAL_MULTI_FREQ, 0d);

        mResultMap.put(ValueKey.DATA, new ArrayList<>());
        mResultMap.put(ValueKey.FREQUENCY, new ArrayList<>());
        mResultMap.put(ValueKey.FREQUENCY_ACCUMULATED, new ArrayList<>());
        mResultMap.put(ValueKey.PROD_VAL_FREQ, new ArrayList<>());
        mResultMap.put(ValueKey.POW_VAL, new ArrayList<>());
        mResultMap.put(ValueKey.POW_VAL_FREQ, new ArrayList<>());
        mResultMap.put(ValueKey.DIV_BY_VAL, new ArrayList<>());
        mResultMap.put(ValueKey.DIV_FREQ_VAL, new ArrayList<>());
        mResultMap.put(ValueKey.SQRT_VAL, new ArrayList<>());
        mResultMap.put(ValueKey.PROD_SQRT_VAL_FREQ, new ArrayList<>());
    }

    void init(TableColumn.IVariable variable) {
        if (variable == null) throw new NullPointerException("IVariable cannot be null");

        mValues.setIsNumber(variable.isNumber());

        for (TableCell.ICell cell : variable.getElements()) {

            DataAnalyseValue data = new DataAnalyseValue(variable.isNumber(), cell);
            int index = indexOf(data);
            if (index < 0) mValues.add(data);
            else mValues.get(index).inc();
        }

        calculate();
    }
    public ArrayList<DataAnalyseValue> sort() {
        return mValues.asList();
    }

    public ArrayList<TableCell.ICell> getModes() {
        return mModes;
    }

    private void calculate() {

        ArrayList<DataAnalyseValue> values = mValues.asList();
        for(DataAnalyseValue value : values) {

            setModes(value);
            plus(Sum.SUM_FREQUENCY, (double) value.getFrequency());
            //Log.e("Item", item.getTitle() + " " + item.getFrequency());
            addToList(ValueKey.DATA, value.getValue());
            addToList(ValueKey.FREQUENCY, (double) value.getFrequency());
            addToList(ValueKey.FREQUENCY_ACCUMULATED, get(Sum.SUM_FREQUENCY));

            if (mValues.isNumber()) {
                plus(Sum.SUM_VALUES, value.asNumber());
                plus(Sum.SUM_VAL_MULTI_FREQ, value.prodValFreq());
                multi(Sum.PROD_VALUES, value.asNumber());
                multi(Sum.PROD_VAL_POW_FREQ, value.powValFreq());
                plus(Sum.SUM_ONE_DIV_VAL, value.divByVal());
                plus(Sum.SUM_FREQ_DIV_VAL, value.divFreqVal());
                plus(Sum.SUM_SQRT_VAL, value.sqrtVal());
                plus(Sum.SUM_SQRT_VAL_MULTI_FREQ, value.prodSqrtValFreq());

                addToList(ValueKey.PROD_VAL_FREQ, value.prodValFreq());
                addToList(ValueKey.POW_VAL, value.asNumber());
                addToList(ValueKey.POW_VAL_FREQ, value.powValFreq());
                addToList(ValueKey.DIV_BY_VAL, value.divByVal());
                addToList(ValueKey.DIV_FREQ_VAL, value.divFreqVal());
                addToList(ValueKey.SQRT_VAL, value.sqrtVal());
                addToList(ValueKey.PROD_SQRT_VAL_FREQ, value.prodSqrtValFreq());
            }
        }

        addToList(ValueKey.FREQUENCY, get(Sum.SUM_FREQUENCY));
        if (mValues.isNumber()) {
            addToList(ValueKey.DATA, mSum.get(Sum.SUM_VALUES));
            addToList(ValueKey.PROD_VAL_FREQ, mSum.get(Sum.SUM_VAL_MULTI_FREQ));
            addToList(ValueKey.POW_VAL, mSum.get(Sum.PROD_VALUES));
            addToList(ValueKey.POW_VAL_FREQ, mSum.get(Sum.PROD_VAL_POW_FREQ));
            addToList(ValueKey.DIV_BY_VAL, mSum.get(Sum.SUM_ONE_DIV_VAL));
            addToList(ValueKey.DIV_FREQ_VAL, mSum.get(Sum.SUM_FREQ_DIV_VAL));
            addToList(ValueKey.SQRT_VAL, mSum.get(Sum.SUM_SQRT_VAL));
            addToList(ValueKey.PROD_SQRT_VAL_FREQ, mSum.get(Sum.SUM_SQRT_VAL_MULTI_FREQ));
        } else {
            addToList(ValueKey.DATA, new VariableString.ValueString("- - -"));
        }
    }

    private void setModes(DataAnalyseValue item) {

        if (item.getFrequency() > mBiggerFreq) {
            mBiggerFreq = item.getFrequency();
            mModes.clear();
            mModes.add(item.getValue());
        } else if (item.getFrequency() == mBiggerFreq) {
            mModes.add(item.getValue());
        }
    }

    public ArrayList<TableCell.ICell> get(ValueKey key) {
        return mResultMap.get(key);
    }

    public Double get(Sum key) {
        return mSum.get(key);
    }

    public DataAnalyseValue get(int index) {
        return mValues.get(index);
    }

    public int size() {
        return mValues.size();
    }

    private int indexOf(DataAnalyseValue element) {
        for (int i = 0; i < mValues.size(); i++) {
            if (mValues.get(i).getTitle().equals(element.getTitle())) {
                return i;
            }
        }
        return -2;
    }

    private void plus(Sum key, Double value) {
        Double sum = mSum.get(key) + value;
        mSum.put(key, sum);
    }

    private void multi(Sum key, Double value) {
        Double sum = mSum.get(key) * value;
        mSum.put(key, sum);
    }

    private void addToList(ValueKey key, Double value) {
        addToList(key, new VariableNumber.ValueInteger(value));
    }

    private void addToList(ValueKey key, TableCell.ICell cell) {
        mResultMap.get(key).add(cell);
    }
}
