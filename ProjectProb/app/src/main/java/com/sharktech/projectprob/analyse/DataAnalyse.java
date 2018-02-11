package com.sharktech.projectprob.analyse;

import android.util.Log;

import com.sharktech.projectprob.customtable.TableCell.ICell;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.ArrayList;

import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.ARITHMETIC;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.GEOMETRIC;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.POUND_ARITHMETIC;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.POUND_GEOMETRIC;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.POUND_QUADRATIC;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.POUND_WEIGHTED;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.QUADRATIC;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.AverageKey.WEIGHTED;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.DATA;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.DIV_BY_VAL;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.DIV_FREQ_VAL;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.FREQUENCY;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.POW_VAL;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.POW_VAL_FREQ;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.PROD_SQRT_VAL_FREQ;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.PROD_VAL_FREQ;
import static com.sharktech.projectprob.analyse.DataAnalyseResult.ValueKey.SQRT_VAL;

public class DataAnalyse {

    private TableColumn.IVariable mVariable;
    private DataAnalyseResult mResult;

    public DataAnalyse(TableColumn.IVariable variable) {
        this.mVariable = variable;
        this.mResult = new DataAnalyseResult();
    }

    public void setVariable(TableColumn.IVariable mVariable) {
        this.mVariable = mVariable;
        this.mResult.clear();
    }

    public boolean calculate(){

        if(!mResult.init(mVariable)){
            return false;
        }
        mResult.calculate();
        return true;
    }

    public ArrayList<TableColumn.IVariable> initClasses(){

        if(mVariable != null) {
            DataAnalyseClass dClass = new DataAnalyseClass();
            dClass.initClasses(mVariable);
            return dClass.getClasses();
        }
        return new ArrayList<>();
    }

    public int size(){
        return mResult.size();
    }

    public ICell getData(int index) {
        return mResult.get(DATA, index);
    }

    public double getFrequency(int index){
        return mResult.get(FREQUENCY, index).asNumber();
    }

    public boolean hasMode(){
        return mResult.nMode() > 0;
    }

    public ArrayList<ICell> getMode(){
        return mResult.getMode();
    }

    public String getTitle() {
        return mVariable == null ? "No title" : mVariable.getTitle();
    }

    public ArrayList<ICell> getData() {
        SortedGenericList<ICell> sorted = new SortedGenericList<>(ICell.class, new SortedGenericList.ISorter<ICell>() {
            @Override
            public int compare(ICell elem1, ICell elem2) {
                if (mVariable.isNumber()) {
                    return elem1.asNumber().doubleValue() == elem2.asNumber().doubleValue() ? 0
                            : elem1.asNumber() > elem2.asNumber() ? 1
                            : -1;
                }
                return elem1.getTitle().compareTo(elem2.getTitle());
            }
        });
        sorted.add(mResult.get(DATA));
        return sorted.asList();
    }

    public ArrayList<ICell> getFrequency() {
        return mResult.get(FREQUENCY);
    }

    public ArrayList<ICell> getProdValFreq() {
        return mResult.get(PROD_VAL_FREQ);
    }

    public ArrayList<ICell> getDivVal() {
        return mResult.get(DIV_BY_VAL);
    }

    public ArrayList<ICell> getDivFreqVal() {
        return mResult.get(DIV_FREQ_VAL);
    }

    public ArrayList<ICell> getSqrtVal() {
        return mResult.get(SQRT_VAL);
    }

    public ArrayList<ICell> getProdSqrtValFreq() {
        return mResult.get(PROD_SQRT_VAL_FREQ);
    }

    public ArrayList<ICell> getPowVal() {
        return mResult.get(POW_VAL);
    }

    public ArrayList<ICell> getPowValFreq() {
        return mResult.get(POW_VAL_FREQ);
    }

    public Double avgArithmetic() {
        return avg(ARITHMETIC);
    }

    public Double avgArithmeticPound() {
        return avg(POUND_ARITHMETIC);
    }

    public Double avgGeometric(){
        return avg(GEOMETRIC);
    }

    public Double avgGeometricPound() {
        return avg(POUND_GEOMETRIC);
    }

    public Double avgWeighted(){
        return avg(WEIGHTED);
    }

    public Double avgWeightedPound() {
        return avg(POUND_WEIGHTED);
    }

    public Double avgQuadratic() {
        return avg(QUADRATIC);
    }

    public Double avgQuadraticPound() {
        return avg(POUND_QUADRATIC);
    }

    private Double avg(DataAnalyseResult.AverageKey key){
        return !isEmpty() ? mResult.get(key) : -1d;
    }

    private boolean isEmpty(){
        return size() == 0;
    }

}