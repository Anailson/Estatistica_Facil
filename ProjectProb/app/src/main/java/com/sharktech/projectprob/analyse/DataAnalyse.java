package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell.ICell;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.ArrayList;

import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.*;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.*;

public class DataAnalyse {

    private TableColumn.IVariable mVariable;
    private ArrayList<DataAnalyseValue> mValues;
    private DataAnalyseResult mResult;

    public DataAnalyse(TableColumn.IVariable variable) {
        this.mVariable = variable;
        this.mValues = new ArrayList<>();
        this.mResult = new DataAnalyseResult();
    }

    public void setVariable(TableColumn.IVariable mVariable) {
        this.mVariable = mVariable;
        this.mValues.clear();
        this.mResult.clear();
    }

    public boolean calculate(){

        if(!init()){
            return false;
        }

        mResult.calculate(mValues);
        return true;
    }

    public int size(){
        return this.mValues.size();
    }

    public ICell getData(int index) {
        return mResult.get(DATA, index);
    }

    public Double getFrequency(int index){
        return mResult.get(FREQUENCY, index).asNumber();
    }

    public ICell getMode(){
        return mResult.getMode();
    }

    public String getTitle() {
        return mVariable == null ? "No title" : mVariable.getTitle();
    }

    public ArrayList<ICell> getData() {
        return mResult.get(DATA);
    }

    public ArrayList<ICell> getFrequency() {
        return mResult.get(FREQUENCY);
    }

    public ArrayList<ICell> getProdValFreq() {
        return mResult.get(PROD_VAL_FREQ);
    }

    public Double avgArithmetic() {
        return avg(ARITHMETIC);
    }

    public Double avgPoundArithmetic() {
        return avg(POUND_ARITHMETIC);
    }

    public Double avgGeometric(){
        return avg(GEOMETRIC);
    }

    public Double avgPoundGeometric() {
        return avg(POUND_GEOMETRIC);
    }

    public Double avgWeighted(){
        return avg(WEIGHTED);
    }

    public Double avgPoundWeighted() {

        return avg(POUND_WEIGHTED);
    }

    public Double avgQuadratic() {
        return avg(QUADRATIC);
    }

    public Double avgPoundQuadratic() {
        return avg(POUND_QUADRATIC);
    }

    private Double avg(DataAnalyseResult.AverageKey key){
        return !isEmpty() ? mResult.get(key) : -1d;
    }

    private boolean isEmpty(){
        return size() == 0;
    }

    private boolean init(){

        if(mVariable == null || mVariable.nElements() == 0) return false;

        for (ICell cell : mVariable.getElements()) {
            int index = indexOf(cell);

            if (index >= 0) {
                mValues.get(index).inc();
            } else {
                mValues.add(new DataAnalyseValue(cell));
            }
        }
        return true;
    }

    private int indexOf(ICell cell){
        for (int i = 0; i < mValues.size(); i++){
            if(mValues.get(i).equals(cell)){
                return i;
            }
        }
        return -2;
    }
}
