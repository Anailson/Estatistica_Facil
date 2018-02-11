package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.ArrayList;

public class DataAnalyse {

    private TableColumn.IVariable mVariable;
    //private DataAnalyseResult mResult;

    public DataAnalyse(TableColumn.IVariable variable) {
        this.mVariable = variable;
        //this.mResult = new DataAnalyseResult();
    }

    public void setVariable(TableColumn.IVariable mVariable) {
        this.mVariable = mVariable;
        //this.mResult.clear();
    }

    public static DataAnalyseDetails details(TableColumn.IVariable variable){
        SortedGenericList<DataAnalyseValue> values = init(variable);
        DataAnalyseDetails averages = new DataAnalyseDetails();
        averages.calculate(variable.isNumber(), values);
        return averages;
    }

    public static DataAnalyseTable table(TableColumn.IVariable variable){
        SortedGenericList<DataAnalyseValue> values = init(variable);
        DataAnalyseTable averages = new DataAnalyseTable();
        averages.calculate(variable.isNumber(), values);
        return averages;
    }

    public static SortedGenericList<DataAnalyseValue> init(TableColumn.IVariable variable){
        if (variable == null) throw new NullPointerException("IVariable cannot be null");

        SortedGenericList<DataAnalyseValue> values = new SortedGenericList<>(DataAnalyseValue.class, sorter(variable.isNumber()));
        for(TableCell.ICell cell : variable.getElements()){

            int index = indexOfValue(values, cell);
            if(index >= 0) values.get(index).inc();
            else values.add(new DataAnalyseValue(variable.isNumber(), cell));
        }
        return values;
    }

    private static int indexOfValue(SortedGenericList<DataAnalyseValue> values, TableCell.ICell val) {
        String upperVal = val.getTitle().toUpperCase();

        for (int i = 0; i < values.size(); i++) {
            String upperCell = values.get(i).getTitle().toUpperCase();

            if (upperVal.equals(upperCell)) {
                return i;
            }
        }
        return -2;
    }

    public ArrayList<TableColumn.IVariable> initClasses(){

        if(mVariable != null) {
            DataAnalyseClass dClass = new DataAnalyseClass();
            dClass.initClasses(mVariable);
            return dClass.getClasses();
        }
        return new ArrayList<>();
    }
/*
    public int size(){
        return mResult.size();
    }
/*
    public ICell getData(int index) {
        return new VariableString.ValueString("Refactoring");
    }

    public double getFrequency(int index){
        return -100;
    }

    public String getTitle(){
        return "Refactoring";
    }*/

    private static SortedGenericList.ISorter<DataAnalyseValue> sorter(final boolean isNumber){
        return new SortedGenericList.ISorter<DataAnalyseValue>() {
            @Override
            public int compare(DataAnalyseValue elem1, DataAnalyseValue elem2) {
                if(!isNumber) return 0;
                return elem1.asNumber() == elem2.asNumber() ? 0
                        : elem1.asNumber() > elem2.asNumber() ? 1 : -1;
            }
        };
    }

}