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

    float asNumber(){
        return mValue.asNumber();
    }

    float prodValFreq(){
        return mValue.isNumber() ? mValue.asNumber() * mFrequency : -1f;
    }

    float powValFreq(){
        return mValue.isNumber() ? (float) Math.pow(mValue.asNumber(), mFrequency) : -1f;
    }

    float divByVal(){
        if (!isNumber()) return  -1L;
        return mValue.asNumber() != 0 ? 1 / mValue.asNumber() : 0f;
    }

    float divFreqVal(){
        if(!isNumber()) return -1L;
        return mValue.asNumber() != 0 ? mFrequency / mValue.asNumber() : 0f;
    }

    float sqrtVal(){
        return mValue.isNumber() ? (float) Math.pow(mValue.asNumber(), 2) : -1f;
    }

    float prodSqrtValFreq(){
        return mValue.isNumber() ? (float) Math.sqrt(mValue.asNumber()) * mFrequency : -1f;
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
