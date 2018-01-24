package com.sharktech.projectprob.models;

import com.sharktech.projectprob.customtable.TableColumn.IVariable;
import com.sharktech.projectprob.customtable.TableCell.ICell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class VariableObject implements IVariable {

    private ArrayList<ICell> mObjects;
    private String mTitle;

    public VariableObject(String title) {
        this.mTitle = title;
        this.mObjects = new ArrayList<>();
    }

    public void add(ICell person) {
        this.mObjects.add(person);
    }

    public void add(ICell[] persons) {
        mObjects.addAll(Arrays.asList(persons));
    }

    public void add(ArrayList<ICell> cells){
        mObjects.addAll(cells);
    }

    @Override
    public String getTitle() {
        return mTitle.toUpperCase();
    }

    @Override
    public int nElements() {
        return mObjects.size();
    }

    @Override
    public ArrayList<ICell> getElements() {
        return mObjects;
    }

    @Override
    public ICell getElement(int index) {
        return mObjects.get(index);
    }

    @Override
    public void setElement(ICell value, int index) {
        mObjects.set(index, value);
    }


    public static class ValueObject implements ICell{

        private Object mElement;

        public ValueObject() {
            this("");
        }

        public ValueObject(Object object) {
            this.mElement = object;
        }

        @Override
        public String getTitle() {
            return toString();
        }

        @Override
        public Object getElement() {
            return this;
        }

        @Override
        public boolean isNumber() {
            return false;
        }

        @Override
        public Float asNumber() {
            return 1f;
        }

        @Override
        public String toString() {
            return mElement.toString();
        }
    }
}