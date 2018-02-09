package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

class DataAnalyseClass {

    private ArrayList<TableCell.ICell> mCells;
    private TableCell.ICell mMin, mMax;

    DataAnalyseClass(){
        mCells = new ArrayList<>();
        mMin = mMax = null;
    }

    void initClasses(TableColumn.IVariable variable){

        mCells.addAll(ascendingSort(variable));
        if(mCells.size() > 0){
            int lastIndex = mCells.size() - 1;
            mMin = variable.isNumber() ? mCells.get(0) : null;
            mMax = variable.isNumber() ? mCells.get(lastIndex) : null;
        }
    }

    ArrayList<TableColumn.IVariable> getClasses() {

        ArrayList<TableColumn.IVariable> classes = new ArrayList<>();

        if(mMin != null && mMax != null){

            VariableString varClass = new VariableString("Classes");
            VariableNumber varFreq = new VariableNumber("Frequência");

            double delta = format(mMax.asNumber() - mMin.asNumber());
            double nClasses = nClasses();
            double interval = format(delta / nClasses);
            double minLimit = format(mMin.asNumber());

            do{
                double maxLimit = minLimit + interval;
                int freq = countElementsBetween(minLimit, maxLimit);
                varClass.add(minLimit + " |- " + maxLimit);
                varFreq.add(freq);
                minLimit = maxLimit;

            }while(minLimit < mMax.asNumber());

            classes.add(varClass);
            classes.add(varFreq);
        }

        return classes;
    }

    private List<TableCell.ICell> ascendingSort(final TableColumn.IVariable variable){
        TableCell.ICell[] sorted = variable.getElements().toArray(new TableCell.ICell[variable.nElements()]);
        Arrays.sort(sorted, new Comparator<TableCell.ICell>() {
            @Override
            public int compare(TableCell.ICell o1, TableCell.ICell o2) {
                return (variable.isNumber() && o1.asNumber().doubleValue() == o2.asNumber().doubleValue()) ? 0
                        : o1.asNumber() > o2.asNumber() ? 1 : -1;
            }
        });
        return Arrays.asList(sorted);
    }

    private int countElementsBetween(double minLimit, double maxLimit){
        int count = 0;
        for (TableCell.ICell cell : mCells){
            if(cell.asNumber() >= maxLimit){
                return count;
            }
            count += cell.asNumber() >= minLimit ? 1 : 0;
        }
        return count;
    }

    //TODO What best value to n classes?
    private double nClasses(){
        return format(Math.sqrt(mCells.size()));
    }

    private double format(double value){
        return Double.valueOf(String.format(Locale.getDefault(), "%.1f", value));
    }
}