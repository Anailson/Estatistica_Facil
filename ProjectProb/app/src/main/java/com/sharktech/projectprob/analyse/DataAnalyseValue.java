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

    double prodValSqrtFreq(){
        return mValue.isNumber() ? Math.sqrt(mValue.asNumber()) * mFrequency : -1d;
    }

    double powValFreq(){
        return mValue.isNumber() ? Math.pow(mValue.asNumber(), mFrequency ) : -1d;
    }

    double divByVal(){
        return mValue.isNumber() && mValue.asNumber() > 0 ? 1 / mValue.asNumber() : -1d;
    }

    double divFreqVal(){
        return mValue.isNumber() && mValue.asNumber() > 0 ? mFrequency / mValue.asNumber() : -1d;
    }

    double sqrtVal(){
        return mValue.isNumber() ? Math.pow(mValue.asNumber(), 2) : -1d;
    }

    double prodSqrtValFreq(){
        return mValue.isNumber() ? Math.pow(mValue.asNumber(), 2) * mFrequency : -1d;
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
