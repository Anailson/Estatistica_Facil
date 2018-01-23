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
        return mValue.isNumber() ? mValue.asNumber() * mFrequency : -1L;
    }

    double prodValSqrtFreq(){
        return mValue.isNumber() ? Math.sqrt(mValue.asNumber()) * mFrequency : -1L;
    }

    Long powValFreq(){
        return mValue.isNumber() ? (long) Math.pow(mValue.asNumber(), mFrequency) : -1L;
    }

    Long divByVal(){
        if (!isNumber()) return  -1L;
        return mValue.asNumber() != 0 ? 1 / mValue.asNumber() : 0L;
    }

    Long divFreqVal(){
        if(!isNumber()) return -1L;
        return mValue.asNumber() != 0 ? mFrequency / mValue.asNumber() : 0L;
    }

    Long sqrtVal(){
        return mValue.isNumber() ? (long) Math.pow(mValue.asNumber(), 2) : -1L;
    }

    Long prodSqrtValFreq(){
        return mValue.isNumber() ? (long) Math.pow(mValue.asNumber(), 2) * mFrequency : -1L;
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
