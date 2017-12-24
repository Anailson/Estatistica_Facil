package com.sharktech.projectprob.interfaces;

import com.sharktech.projectprob.customtable.Value;

import java.util.ArrayList;

public interface IVariable <E>{

    String getTitle();

    int nElements();

    ArrayList<Value> getElements();

    Value<E> getElement(int index);

    void setElement(Value<E> value, int index);
}
