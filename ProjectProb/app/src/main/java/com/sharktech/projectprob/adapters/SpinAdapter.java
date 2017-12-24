package com.sharktech.projectprob.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class SpinAdapter {

    private Context context;

    public SpinAdapter(Context context){
        this.context = context;
    }

    public ArrayAdapter<String> getAdapter(int arrayResource){

        String [] values = context.getResources().getStringArray(arrayResource);
        return getAdapter(values);
    }

    public ArrayAdapter<String> getAdapter(String [] values){
        return new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, values);
    }

    public <E> ArrayAdapter<E> getAdapter(List<E> values){
        return new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, values);
    }
}
