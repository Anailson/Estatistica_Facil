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
    /*
        public static DataAnalyseDetails details(TableColumn.IVariable variable){
            SortedDataValueList values = init(variable);
            DataAnalyseDetails averages = new DataAnalyseDetails();
            averages.calculate(variable.isNumber(), values);
            return averages;
        }

        public static DataAnalyseTable table(TableColumn.IVariable variable){
            SortedDataValueList values = init(variable);
            DataAnalyseTable averages = new DataAnalyseTable();
            averages.calculate(variable.isNumber(), values);
            return averages;
        }

    private SortedDataValueList init(TableColumn.IVariable variable) {


        SortedDataValueList values = new SortedDataValueList(variable.isNumber());

        return values;
    }


        private static int indexOfValue(SortedDataValueList values, TableCell.ICell val) {
            String upperVal = val.getTitle().toUpperCase();

            for (int i = 0; i < values.size(); i++) {
                String upperCell = values.get(i).getTitle().toUpperCase();

                if (upperVal.equals(upperCell)) {
                    return i;
                }
            }
            return -2;
        }
        */
    public ArrayList<TableColumn.IVariable> initClasses() {
/*
        if(mVariable != null) {
            DataAnalyseClass dClass = new DataAnalyseClass();
            dClass.initClasses(mVariable);
            return dClass.getClasses();
        }
*/
        return new ArrayList<>();
    }
}