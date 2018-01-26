package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;

class DataAnalyseValue {

    private TableCell.ICell mValue;
    private long mFrequency;

    DataAnalyseValue(TableCell.ICell mValue){
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

    boolean isNumber(){
        return mValue.isNumber();
    }

    double asNumber(){
        return mValue.asNumber();
    }

    double prodValFreq(){
        return mValue.isNumber() ? mValue.asNumber() * mFrequency : -1d;
    }

    double powValFreq(){
        return mValue.isNumber() ? (float) Math.pow(mValue.asNumber(), mFrequency) : -1d;
    }

    double divByVal(){
        if (!isNumber()) return  -1d;
        return mValue.asNumber() != 0 ? 1 / mValue.asNumber() : 0d;
    }

    double divFreqVal(){
        if(!isNumber()) return -1L;
        return mValue.asNumber() != 0 ? mFrequency / mValue.asNumber() : 0d;
    }

    double sqrtVal(){
        return mValue.isNumber() ? (float) Math.pow(mValue.asNumber(), 2) : -1d;
    }

    double prodSqrtValFreq(){
        return mValue.isNumber() ? (float) Math.pow(mValue.asNumber(), 2) * mFrequency : -1d;
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
