package com.sharktech.projectprob.models;

import com.sharktech.projectprob.customtable.Variable.IVariable;
import com.sharktech.projectprob.customtable.Cell.ICell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class VariablePersonModel implements IVariable {

    private ArrayList<ICell> mPersons;
    private String mTitle;

    public VariablePersonModel(String mTitle) {
        this.mTitle = mTitle;
        this.mPersons = new ArrayList<>();
    }

    public void add(Person person) {
        this.mPersons.add(person);
    }

    public void add(Person[] persons) {

        mPersons.addAll(Arrays.asList(persons));
    }

    @Override
    public String getTitle() {
        return "Titulo: " + mTitle;
    }

    @Override
    public int nElements() {
        return mPersons.size();
    }

    @Override
    public ArrayList<ICell> getElements() {
        return mPersons;
    }

    @Override
    public ICell getElement(int index) {
        return mPersons.get(index);
    }

    @Override
    public void setElement(ICell value, int index) {
        mPersons.set(index, value);
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