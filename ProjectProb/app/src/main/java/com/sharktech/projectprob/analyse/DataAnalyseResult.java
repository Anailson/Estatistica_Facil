package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableColumn;

class DataAnalyseResult {


    private boolean mIsNumber;
    private SortedCellsList mSortedList;

    DataAnalyseResult() {
        mSortedList = new SortedCellsList();
    }

    void clear(){
        mSortedList.clear();
    }

    boolean init(TableColumn.IVariable variable){
        if(variable == null) return false;
        mIsNumber = variable.isNumber();
        return mSortedList.init(variable);
    }

    void calculate(){
        mSortedList.quarterValue(SortedCellsList.Quart.FIRST);
        mSortedList.quarterValue(SortedCellsList.Quart.SECOND);
        mSortedList.quarterValue(SortedCellsList.Quart.THIRD);

    }

    int size(){
        return mSortedList.valuesSize();
    }
}
