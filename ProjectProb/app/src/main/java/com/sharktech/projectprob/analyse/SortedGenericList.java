package com.sharktech.projectprob.analyse;

import android.support.v7.util.SortedList;

import java.util.ArrayList;
import java.util.Collection;


public class SortedGenericList <T> extends SortedList.Callback<T>{

    private SortedList<T> mList;
    private ISorter <T> mSorter;

    public SortedGenericList(Class<T> cls, ISorter<T> sorter) {
        mList = new SortedList<>(cls, this);
        mSorter = sorter;
    }

    public int add(T item){
        return mList.add(item);
    }

    public void add(Collection<T> collection){
        mList.addAll(collection);
    }

    public void add(T[] collection){
        mList.addAll(collection);
    }

    public T get(int index){
        return mList.get(index);
    }

    public boolean remove(T item){
        return mList.remove(item);
    }

    public T remove(int index){
        return mList.removeItemAt(index);
    }

    public int size(){
        return mList.size();
    }

    public void clear(){
        mList.clear();
    }


    public ArrayList<T> asList(){
        ArrayList<T> list = new ArrayList<>();
        for(int i = 0; i < mList.size(); i++){
            list.add(mList.get(i));
        }
        return list;
    }

    @Override
    public int compare(T o1, T o2) {
        return mSorter.compare(o1, o2);
    }

    @Override
    public void onChanged(int position, int count) {}

    @Override
    public boolean areContentsTheSame(T oldItem, T newItem) {
        return false;
    }

    @Override
    public boolean areItemsTheSame(T item1, T item2) {
        return false;
    }

    @Override
    public void onInserted(int position, int count) {}

    @Override
    public void onRemoved(int position, int count) {}

    @Override
    public void onMoved(int fromPosition, int toPosition) {}

    public interface ISorter<T>{
        int compare(T elem1, T elem2);
    }
}
