package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell.ICell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableObject;

import java.util.ArrayList;

public class DataAnalyse {

    private TableColumn.IVariable mVariable;
    private ArrayList<DataAnalyseValue> mValues;
    private ArrayList<ICell> mData, mFrequency, mVariance;
    private ICell mMode;

    public DataAnalyse(TableColumn.IVariable variable) {
        this.mVariable = variable;
        this.mValues = new ArrayList<>();
        this.mData = new ArrayList<>();
        this.mFrequency = new ArrayList<>();
        this.mVariance = new ArrayList<>();
        this.mMode = new VariableObject.ValueObject();
    }

    public void setVariable(TableColumn.IVariable mVariable) {
        this.mVariable = mVariable;
        this.mValues.clear();
        this.mData.clear();
        this.mFrequency.clear();
        this.mVariance.clear();
        this.mMode = new VariableObject.ValueObject();
    }

    public boolean calculate(){

        if(!init()){
            return false;
        }

        ICell mode = null;
        long frequency = 0;
        for (DataAnalyseValue data : mValues){
            if(data.mFrequency > frequency){
                mode = data.mValue;
                frequency = data.mFrequency;
            }
            data.calculate();
            mData.add(data.mValue);
            mFrequency.add(new VariableNumber.ValueInteger(data.mFrequency));
            mVariance.add(new VariableNumber.ValueInteger(data.mVariance));
        }
        this.mMode = mode;
        return true;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return this.mValues.size();
    }

    public ICell getData(int index) {
        return this.mData.get(index);
    }

    public Double getFrequency(int index){
        return this.mFrequency.get(index).asNumber();
    }

    public Double getVariance(int index) {
        return this.mVariance.get(index).asNumber();
    }

    public ICell getMode(){
        return this.mMode;
    }

    public Double avgArithmetic() {
        if (isEmpty()) return -1d;

        Double sumArithmetic = 0d;
        for(int i = 0; i < size(); i++){

            ICell cell = getData(i);
            if(cell.isNumber()){
                sumArithmetic += cell.asNumber();
            }
        }
        return sumArithmetic / size();
    }

    public Double avgGeometric() {

        if(isEmpty()) return -1d;

        Double sumFreq = 0d;
        Double prodGeometric = 1d;

        for(int i = 0; i < size(); i++){
            ICell cell = getData(i);
            Double frequency = getFrequency(i);
            sumFreq += frequency;
            if(cell.isNumber()){
                prodGeometric *= Math.pow(cell.asNumber(), frequency);
            }

        }
        return Math.pow(prodGeometric, (1d / sumFreq));
    }

    public Double avgWeighted() {

        if(isEmpty()) return -1d;

        Double sumWeighted = 0d;
        Double sumFreq = 0d;
        for(int i = 0; i < size(); i++){
            ICell cell = getData(i);
            Double frequency = getFrequency(i);
            sumFreq += frequency;
            if(cell.isNumber()){
                sumWeighted += cell.asNumber() * frequency;
            }
        }
        return sumWeighted / sumFreq;
    }

    public Double avgQuadratic() {
        if(isEmpty()) return -1d;

        Double sumQuadratic = 0d;
        for(int i = 0; i < size(); i++){
            ICell cell = getData(i);

            if(cell.isNumber()){

                sumQuadratic += Math.pow(cell.asNumber(), 2d) * getFrequency(i);
            }
        }

        return Math.sqrt(sumQuadratic);
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

    public String getTitle() {
        return mVariable == null ? "No title" : mVariable.getTitle();
    }

    public ArrayList<ICell> getData() {
        return mData;
    }

    public ArrayList<ICell> getFrequency() {
        return mFrequency;
    }

    public ArrayList<ICell> getVariance() {
        return mVariance;
    }

    private class DataAnalyseValue {

        private ICell mValue;
        private long mFrequency;
        private double mVariance;

        private DataAnalyseValue(ICell mValue){
            this.mValue = mValue;
            this.mFrequency = mValue == null ? 0 : 1;
            this.mVariance = 1;
        }

        private void inc(){
            mFrequency++;
        }

        private void calculate(){
            if(mValue.isNumber()){

            }
        }

        @Override
        public boolean equals(Object obj) {

            if(obj instanceof ICell) {
                ICell cell = (ICell) obj;
                return cell.getTitle().equals(mValue.getTitle());
            }
            return false;
        }
    }
}
