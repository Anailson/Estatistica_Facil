package com.sharktech.projectprob.models;

import com.sharktech.projectprob.customtable.TableCell;

import io.realm.RealmObject;

public class CellValue extends RealmObject implements TableCell.ICell {

    private boolean number;
    private String value;

    public CellValue() {
        number = false;
        value = "No Cell-title";
    }

    public CellValue(String value) {
        this.number = false;
        this.value = value;
    }

    public CellValue(Double value) {
        this.number = true;
        this.value = value.toString();
    }

    public boolean isNumber() {
        return number;
    }

    public void setNumber(boolean number) {
        this.number = number;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getTitle() {
        return value;
    }

    @Override
    public Double asNumber() {
        return number ? Double.valueOf(value) : 1d;
    }
}
