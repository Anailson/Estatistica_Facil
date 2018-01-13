package com.sharktech.projectprob.models;

import com.sharktech.projectprob.customtable.Variable.IVariable;
import com.sharktech.projectprob.customtable.Cell.ICell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class VariableObject implements IVariable {

    private ArrayList<ICell> mObjects;
    private String mTitle;

    public VariableObject(String mTitle) {
        this.mTitle = mTitle;
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


    public static class Person implements ICell<Person>{

        private String mName;
        private int mAge;

        public Person(String name, int age) {
            this.mName = name;
            this.mAge = age;
        }


        @Override
        public String getTitle() {
            return String.format(Locale.getDefault(), "Nome: %s. Idade: %d", mName, mAge);
        }

        @Override
        public Person getElement() {
            return this;
        }

        @Override
        public boolean isNumber() {
            return false;
        }

        @Override
        public Float asFloat() {
            return 1f;
        }

        @Override
        public String toString() {
            return getTitle();
        }
    }
}