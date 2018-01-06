package com.sharktech.projectprob.customtable;

public class Value<E extends Cell.ICell> {

    private E mElement;
    private int mCount;

    Value(E element, int count){
        this.mElement = element;
        this.mCount = count;
    }

    public Value(E element) {
        this.mElement = element;
        this.mCount = isEmpty() ? 0 : 1;
    }

    public String simpleText(){
        return toString();
    }


    boolean isNumber() {
        return mElement.isNumber();
    }

    Float asFloat() {
        return mElement.asFloat();
    }

    E getData(){
        return mElement;
    }

    void setData(E value) {
        this.mElement = value;
    }

    public int getCount() {
        return mCount;
    }

    int inc(){
        return mCount++;
    }

    boolean isEmpty() {
        return mElement == null;
    }


    @Override
    public String toString() {

        return isEmpty() ? " - " :  mElement.getTitle();
    }
}