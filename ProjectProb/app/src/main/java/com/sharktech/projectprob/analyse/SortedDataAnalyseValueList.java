package com.sharktech.projectprob.analyse;

import android.support.v7.util.SortedList;

import java.util.ArrayList;

class SortedDataAnalyseValueList {

    private boolean mIsNumber;
    private SortedList<DataAnalyseValue> mList;

    SortedDataAnalyseValueList(){
        mIsNumber = false;
        mList = new SortedList<>(DataAnalyseValue.class, callback());
    }

    ArrayList<DataAnalyseValue> asList(){
        ArrayList<DataAnalyseValue> values = new ArrayList<>();
        for(int i = 0; i < size(); i++){
            values.add(mList.get(i));
        }
        return values;
    }

    void setIsNumber(boolean mIsNumber) {
        this.mIsNumber = mIsNumber;
    }

    boolean isNumber(){
        return mIsNumber;
    }

    void add(DataAnalyseValue value){
        mList.add(value);
    }

    DataAnalyseValue get(int index){
        return mList.get(index);
    }

    int size(){
        return mList.size();
    }

    private SortedList.Callback<DataAnalyseValue> callback(){
        return new SortedList.Callback<DataAnalyseValue>() {
            @Override
            public int compare(DataAnalyseValue elem1, DataAnalyseValue elem2) {
                return !mIsNumber ? elem1.getTitle().compareTo(elem2.getTitle())
                        : elem1.asNumber() == elem2.asNumber() ? 0
                        : elem1.asNumber() < elem2.asNumber() ? -1
                        : 1;
            }

            @Override
            public void onChanged(int position, int count) {}

            @Override
            public boolean areContentsTheSame(DataAnalyseValue oldItem, DataAnalyseValue newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }

            @Override
            public boolean areItemsTheSame(DataAnalyseValue item1, DataAnalyseValue item2) {
                return areContentsTheSame(item1, item2) && item1.getFrequency() == item2.getFrequency();
            }

            @Override
            public void onInserted(int position, int count) {}

            @Override
            public void onRemoved(int position, int count) {}

            @Override
            public void onMoved(int fromPosition, int toPosition) {}
        };
    }
}
