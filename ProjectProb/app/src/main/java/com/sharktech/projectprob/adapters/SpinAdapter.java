package com.sharktech.projectprob.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.sharktech.projectprob.R;

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
        return new ArrayAdapter<>(context, R.layout.spinner_custom, values);
    }

    public <E> ArrayAdapter<E> getAdapter(List<E> values){
        return new ArrayAdapter<>(context, R.layout.spinner_custom, values);
    }
}
