package com.sharktech.projectprob.models;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class VariableNumber extends RealmObject implements TableColumn.IVariable {

    @PrimaryKey
    private String title;
    private RealmList<CellValue> values;

    public VariableNumber() {
        this("No VarNumber-title");
    }

    public VariableNumber(String title) {
        this.title = title;
        this.values = new RealmList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<CellValue> getValues() {
        return values;
    }

    public void setValues(RealmList<CellValue> values) {
        this.values = values;
    }

    public void add(Number value){
        add(new CellValue(value.doubleValue()));
    }

    public void add(CellValue value){
        values.add(value);
    }

    public void add(Number [] values){
        for(Number num : values){
            this.values.add(new CellValue(num.doubleValue()));
        }
    }

    public void add(ArrayList<CellValue> values){
        this.values.addAll(values);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int nElements() {
        return values.size();
    }
    @Override

    public boolean isNumber() {
        return true;
    }

    @Override
    public RealmList<TableCell.ICell> getElements() {

        RealmList<TableCell.ICell> cells = new RealmList<>();
        cells.addAll(values);
        return cells;
    }

    @Override
    public TableCell.ICell getElement(int index) {
        return values.get(index);
    }

    @Override
    public void setElement(TableCell.ICell value, int index) {
        CellValue cell = new CellValue();
        cell.setValue(value.getTitle());
        values.set(index, cell);
    }
/*
    public static class ValueInteger implements TableCell.ICell {

        private Number mValue;

        public ValueInteger(Number value) {
            this.mValue = value;
        }

        @Override
        public String getTitle() {
            double ceil = Math.ceil(mValue.doubleValue());

            return ceil - mValue.doubleValue() != 0
                    ? String.format(Locale.getDefault(), "%.6f", mValue.doubleValue())
                    : mValue.doubleValue() > mValue.longValue()
                    ? String.valueOf(mValue.doubleValue())
                    : String.valueOf(mValue.longValue());
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
    */
}
