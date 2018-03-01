package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.ArrayList;

public class DataAnalyse {

    public enum Sum {
        SUM_VALUES, SUM_FREQUENCY, SUM_VAL_MULTI_FREQ, PROD_VALUES, PROD_VAL_POW_FREQ,
        SUM_ONE_DIV_VAL, SUM_FREQ_DIV_VAL, SUM_SQRT_VAL, SUM_SQRT_VAL_MULTI_FREQ
    }

    public enum Average {
        ARITHMETIC, ARITHMETIC_POUND, GEOMETRIC, GEOMETRIC_POUND,
        WEIGHTED, WEIGHTED_POUND, QUADRATIC, QUADRATIC_POUND
    }

    public enum ValueKey {
        DATA, FREQUENCY, FREQUENCY_ACCUMULATED, PROD_VAL_FREQ, POW_VAL, POW_VAL_FREQ,
        DIV_BY_VAL, DIV_FREQ_VAL, SQRT_VAL, PROD_SQRT_VAL_FREQ
    }

    private DataAnalyseResult mSortedValues;
    private TableColumn.IVariable mVariable;

    public DataAnalyse(TableColumn.IVariable variable) {
        mSortedValues = new DataAnalyseResult();
        mVariable = variable;
    }

    public void setVariable(TableColumn.IVariable variable) {
        mVariable = variable;
    }

    public void init(){
        mSortedValues.init(mVariable);
    }

    public DataAnalyseResult getValues() {
        return mSortedValues;
    }

    public Double get(Sum key) {
        return mSortedValues.get(key);
    }

    public Double get(Average key) {
        return DataAnalyseCalc.get(key, mSortedValues);
    }

    public ArrayList<TableCell.ICell> get(ValueKey key) {
        return mSortedValues.get(key);
    }

    public Double kurtosis() {
        return DataAnalyseCalc.kurtosis(mSortedValues.sort());
    }

    public Double asymmetry() {
        return DataAnalyseCalc.asymmetry(mSortedValues.sort());
    }

    public boolean hasModes() {
        return mSortedValues.getModes().size() > 0;
    }

    public ArrayList<TableCell.ICell> modes() {
        return mSortedValues.getModes();
    }

    public ArrayList<TableColumn.IVariable> initClasses() {
        return new ArrayList<>();
    }

    public static void intervalAverage(IntervalConfidenceValues values) {
        DataAnalyseCalc.intervalAverage(values);
    }

    public static void intervalProportion(IntervalConfidenceValues values) {
        DataAnalyseCalc.intervalProportion(values);
    }

    public static class IntervalConfidenceValues{

        Double sampleAvg, sampleSize, deviation, populationSize, confidence, success;
        IntervalConfidenceResult result;

        public IntervalConfidenceValues(){
            sampleAvg = sampleSize = deviation = populationSize = confidence = success = null;
        }

        public Double getSuccess() {
            return success;
        }

        public void setSuccess(Double success) {
            this.success = success;
        }

        public Double getSampleAvg() {
            return sampleAvg;
        }

        public void setSampleAvg(Double sampleAvg) {
            this.sampleAvg = sampleAvg;
        }

        public Double getSampleSize() {
            return sampleSize;
        }

        public void setSampleSize(Double sampleSize) {
            this.sampleSize = sampleSize;
        }

        public Double getDeviation() {
            return deviation;
        }

        public void setDeviation(Double deviation) {
            this.deviation = deviation;
        }

        public Double getPopulationSize() {
            return populationSize;
        }

        public void setPopulationSize(Double populationSize) {
            this.populationSize = populationSize;
        }

        public Double getConfidence() {
            return confidence;
        }

        public void setConfidence(Double confidence) {
            this.confidence = confidence;
        }

        public void setOnResult(IntervalConfidenceResult result){
            this.result = result;
        }

        boolean isEmpty(){
            return isNull(sampleAvg) && isNull(sampleSize) && isNull(populationSize) && isNull(deviation) && isNull(confidence);
        }

        void onSuccess(Double min, Double max, Double error, float z, IntervalConfidenceValues values){
            if(result != null) result.onSuccess(min, max, error, z, values);
        }

        void onError(){
            if(result != null) result.onError();
        }

        boolean isNull(Double value){
            return value == null;
        }
    }

    public interface IntervalConfidenceResult{
        void onSuccess(Double min, Double max, Double error, float z, IntervalConfidenceValues values);
        void onError();
    }
}
