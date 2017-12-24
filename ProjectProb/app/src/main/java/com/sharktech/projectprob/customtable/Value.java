package com.sharktech.projectprob.customtable;


import java.util.Locale;

public class Value<E> {

    private E data;
    private int count;

    Value(E data) {
        this.data = data;
        this.count = isEmpty() ? 0 : 1;
    }

    String simpleText(){
        return toString();
    }

    String formattedText(){
        String text = isEmpty() ? " - " : toString();
        return String.format(Locale.getDefault(), "(%d) %s", count, text);
    }

    public E getData(){
        return data;
    }

    public void setData(E value) {
        this.data = value;
    }

    public int getCount() {
        return count;
    }

    public int inc(){
        return count++;
    }

    boolean isEmpty() {
        return data == null;
    }

    boolean isNumber() {
        return !isEmpty() && data.getClass().getSuperclass() == Number.class;
    }

    @Override
    public String toString() {

        return isEmpty() ? " - " :  data.toString();
    }
}