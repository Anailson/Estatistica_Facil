package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;

class DataAnalyseValue {

    private boolean mIsNumber;
    private TableCell.ICell mValue;
    private long mFrequency;

    DataAnalyseValue(boolean isNumber, TableCell.ICell mValue){
        this.mIsNumber = isNumber;
        this.mValue = mValue;
        this.mFrequency = mValue == null ? 0 : 1;
    }

    TableCell.ICell getValue() {
        return mValue;
    }

    long getFrequency(){
        return mFrequency;
    }

    void inc(){
        mFrequency++;
    }
/*
    boolean isNumber(){
        return mValue.isNumber();
    }
*/
    double asNumber(){
        return mValue.asNumber();
    }

    double prodValFreq(){
        return mIsNumber ? mValue.asNumber() * mFrequency : -1d;
    }

    double powValFreq(){
        return mIsNumber ? (float) Math.pow(mValue.asNumber(), mFrequency) : -1d;
    }

    double divByVal(){
        if (!mIsNumber) return  -1d;
        return mValue.asNumber() != 0 ? 1 / mValue.asNumber() : 0d;
    }

    double divFreqVal(){
        if(!mIsNumber) return -1L;
        return mValue.asNumber() != 0 ? mFrequency / mValue.asNumber() : 0d;
    }

    double sqrtVal(){
        return mIsNumber ? (float) Math.pow(mValue.asNumber(), 2) : -1d;
    }

    double prodSqrtValFreq(){
        return mIsNumber ? (float) Math.pow(mValue.asNumber(), 2) * mFrequency : -1d;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof TableCell.ICell) {
            TableCell.ICell cell = (TableCell.ICell) obj;
            return cell.getTitle().equals(mValue.getTitle());
        }
        return false;
    }
}
