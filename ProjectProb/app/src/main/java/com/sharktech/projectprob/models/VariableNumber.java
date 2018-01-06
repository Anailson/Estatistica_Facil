package com.sharktech.projectprob.models;


import com.sharktech.projectprob.customtable.Cell;
import com.sharktech.projectprob.customtable.Variable;

import java.util.ArrayList;

public class VariableNumber implements Variable.IVariable {

    private String mTitle;
    private ArrayList<Cell.ICell> mValues;

    public VariableNumber(String mTitle) {
        this.mTitle = mTitle;
        this.mValues = new ArrayList<>();
    }

    public void add(Number value){
        add(new ValueInteger(value));
    }

    public void add(ValueInteger value){
        mValues.add(value);
    }

    public void add(Number [] values){
        for(Number num : values){
            mValues.add(new ValueInteger(num));
        }
    }

    public void add(ArrayList<ValueInteger> values){
        mValues.addAll(values);
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public int nElements() {
        return mValues.size();
    }

    @Override
    public ArrayList<Cell.ICell> getElements() {
        return mValues;
    }

    @Override
    public Cell.ICell getElement(int index) {
        return mValues.get(index);
    }

    @Override
    public void setElement(Cell.ICell value, int index) {
        mValues.set(index, value);
    }

    public class ValueInteger implements Cell.ICell<Number> {

        private Number mValue;

        public ValueInteger(Number value) {
            this.mValue = value;
        }

        @Override
        public String getTitle() {
            return mValue.toString();
        }

        @Override
        public Number getElement() {
            return mValue;
        }

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public Float asFloat() {
            return mValue.floatValue();
        }

        @Override
        public String toString() {
            return getTitle();
        }
    }
}
