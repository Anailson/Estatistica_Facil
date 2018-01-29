package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class DataAnalyseClass {

    private ArrayList<TableCell.ICell> mCells;
    private TableCell.ICell mMin, mMax;

    DataAnalyseClass(){
        mCells = new ArrayList<>();
        mMin = mMax = null;
    }

    void initClasses(ArrayList<TableCell.ICell> cells){

        mCells.addAll(ascendingSort(cells));
        if(mCells.size() > 0){
            int lastIndex = mCells.size() - 1;
            mMin = mCells.get(0).isNumber() ? mCells.get(0) : null;
            mMax = mCells.get(lastIndex).isNumber() ? mCells.get(lastIndex) : null;
        }
    }

    ArrayList<TableColumn.IVariable> getClasses() {

        ArrayList<TableColumn.IVariable> classes = new ArrayList<>();

        if(mMin != null && mMax != null){

            VariableString varClass = new VariableString("Classes");
            VariableNumber varFreq = new VariableNumber("Frequência");

            int n = mCells.size();
            double delta = mMax.asNumber() - mMin.asNumber();
            double nClasses = nClasses();
            double interval = Math.ceil(delta / nClasses);
            double minLimit = minLimit();

            for(int i = 0; i < nClasses; i++){
                double maxLimit = minLimit + interval;
                int freq = elementsBetween(minLimit, maxLimit);
                varClass.add(minLimit + " |- " + maxLimit);
                varFreq.add(freq);
                minLimit = maxLimit;
            }

            classes.add(varClass);
            classes.add(varFreq);
        }

        return classes;
    }

    private List<TableCell.ICell> ascendingSort(ArrayList<TableCell.ICell> cells){
        TableCell.ICell[] sorted = cells.toArray(new TableCell.ICell[cells.size()]);
        Arrays.sort(sorted, new Comparator<TableCell.ICell>() {
            @Override
            public int compare(TableCell.ICell o1, TableCell.ICell o2) {
                return (o1.isNumber() && o2.isNumber() && o1.asNumber().doubleValue() == o2.asNumber().doubleValue()) ? 0
                        : o1.asNumber() > o2.asNumber() ? 1 : -1;
            }
        });
        return Arrays.asList(sorted);
    }

    private int elementsBetween(double minLimit, double maxLimit){
        int n = 0;
        for (TableCell.ICell cell : mCells){
            if(cell.asNumber() >= maxLimit){
                return n;
            }
            n += cell.asNumber() >= minLimit ? 1 : 0;
        }
        return n;
    }

    //TODO What best value to n classes?
    private double nClasses(){
        return Math.ceil(Math.sqrt(mCells.size()));
    }

    //TODO MinLimit works ever? try some values.
    private double minLimit(){
        double rounded = Math.round(mMin.asNumber());
        double dif = mMin.asNumber() - rounded;

        return dif == 0 ? mMin.asNumber()
                : dif > 0 ? rounded : Math.round(Math.abs(dif));
    }
}