package com.sharktech.projectprob.models;


import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.ArrayList;

public class VariableNumber implements TableColumn.IVariable {

    private String mTitle;
    private ArrayList<TableCell.ICell> mValues;

    public VariableNumber(String title) {
        this.mTitle = title;
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
    public ArrayList<TableCell.ICell> getElements() {
        return mValues;
    }

    @Override
    public TableCell.ICell getElement(int index) {
        return mValues.get(index);
    }

    @Override
    public void setElement(TableCell.ICell value, int index) {
        mValues.set(index, value);
    }

    public static class ValueInteger implements TableCell.ICell<Number> {

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
        public Double asNumber() {
            return mValue.doubleValue();
        }

        @Override
        public String toString() {
            return getTitle();
        }
    }
}
