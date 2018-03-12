package com.sharktech.projectprob.models;

import com.sharktech.projectprob.customtable.TableCell.ICell;
import com.sharktech.projectprob.customtable.TableColumn.IVariable;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class VariableString extends RealmObject implements IVariable {

    private String title;
    private RealmList<CellValue> values;

    public VariableString() {
        this("No var-title");
    }

    public VariableString(String title) {
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

    public void add(CellValue value){
        values.add(value);
    }

    public void add(String value){
        CellValue cell = new CellValue();
        cell.setValue(value);
        values.add(cell);
    }

    public void add(Character value){
        add(String.valueOf(value));
    }

    public void add(char value){
        add(String.valueOf(value));
    }

    public void add(String[] values){
        for(String s : values){
            add(s);
        }
    }

    public void add(Character[] values){
        for(Character c : values){
            add(c);
        }
    }

    public void add(char[] values){
        for(char c : values){
            add(c);
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
        return false;
    }

    @Override
    public RealmList<ICell> getElements() {
        RealmList<ICell> cells = new RealmList<>();
        cells.addAll(values);
        return cells;
    }

    @Override
    public ICell getElement(int index) {
        return values.get(index);
    }

    @Override
    public void setElement(ICell value, int index) {
        CellValue cell = new CellValue();
        cell.setValue(value.getTitle());
        values.set(index, cell);
    }
}
