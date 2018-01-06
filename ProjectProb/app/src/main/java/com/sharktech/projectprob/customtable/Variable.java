package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.interfaces.ICell;
import com.sharktech.projectprob.interfaces.IVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Variable<E> extends LinearLayout implements IVariable<E>{

    private int mPosition;
    private Context mContext;
    private String mTitle;
    private ArrayList<Cell<E>> mCells;
    private Analyses mAnalyses;
    private ICell<E> mCell;

    public Variable(Context context) {
        this(context, " No Title ");
    }

    public Variable(Context context, String title) {
        super(context);
        this.mPosition = -1;
        this.mTitle = title;
        this.mCells = new ArrayList<>();
        this.mAnalyses = null;

        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.border_light);
    }

    public void setPosition(int position) {
        if (position >= 0) {
            this.mPosition = position;
            for (Cell cell : mCells) {
                cell.setCol(position);
            }
        }
    }

    public void setListener(ICell<E> mCell) {
        this.mCell = mCell;
    }

    public Analyses calculate() {
        if (mAnalyses == null) {
            mAnalyses = new Analyses(mCells);
        }
        return mAnalyses;
    }

    public void add(E cell) {
        add(Cell.newCell(mContext, cell));
    }

    public void add(Cell<E> cell) {
        mCells.add(cell);
    }

    public void addAll(Cell<E>[] values) {
        addAll(Arrays.asList(values));
    }

    public void addAll(List<Cell<E>> values) {

        for (Cell<E> value : values) {
            add(value);
        }
    }

    protected void emptyCell() {
        add(Cell.<E>emptyCell(mContext));
    }

    protected Variable build() {

        Cell<String> cellTitle = Cell.newCell(getContext(), mTitle);
        cellTitle.setCol(mPosition);
        addView(cellTitle);

        for (int row = 0; row < mCells.size(); row++) {
            Cell cell = mCells.get(row);
            removeParent(cell);
            cell.setPosition(mPosition, row);
            addView(cell);
        }
        setHeading();
        return this;
    }

    private void removeParent(View child){
        ViewGroup parent = (ViewGroup) child.getParent();
        if(parent != null) parent.removeView(child);
    }

    private void setHeading() {
        if (getChildCount() > 0) {

            if (getChildAt(0) instanceof Cell) {
                Cell cell = (Cell) getChildAt(0);
                cell.setHeading();
            }
        }
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public int nElements() {
        return mCells.size();
    }

    @Override
    public ArrayList<Value> getElements(){

        ArrayList<Value> els = new ArrayList<>();
        for(Cell<E> cell : mCells){
            if(!cell.isEmpty()){
                els.add(cell.getValue());
            }
        }
        return els;
    }

    @Override
    public Value<E> getElement(int index) {
        return mCells.get(index).getValue();
    }

    @Override
    public void setElement(Value<E> value, int index) {
        mCells.get(index).setValue(value);
    }

    @Override
    public void setBackgroundColor(int color) {

        for (Cell cell : mCells) {
            cell.setHeading();
        }
        super.setBackgroundColor(color);
    }

    public class Analyses {

        private double avgArithmetic, avgGeometric, avgWeighted, avgQuadratic;
        private E mode;

        private Analyses(ArrayList<Cell<E>> cells) {

            calculate(cells);
        }

        private void calculate(ArrayList<Cell<E>> cells){

            Integer sumFreq = 0;
            Double sumArithmetic = 0d;
            Double prodGeometric = 1d;
            Double sumWeighted = 0d;
            Double sumQuadratic = 0d;
            int countMode = 0;
            E val = null;

            for (Cell<E> cell : cells) {

                if(cell.getCount() > countMode){
                    countMode = cell.getCount();
                    val = cell.getValue().getData();
                }

                sumFreq += cell.getCount();
                if(cell.isNumber()) {
                    sumArithmetic += cell.asFloat();
                    prodGeometric *= Math.pow(cell.asFloat(), cell.getCount());
                    sumWeighted += cell.asFloat() * cell.getCount();
                    sumQuadratic += Math.pow(cell.asFloat(), 2d) * cell.getCount();
                }
            }

            avgArithmetic = cells.isEmpty() ? 0 : sumArithmetic / cells.size();
            avgGeometric = Math.pow(prodGeometric, (1d / sumFreq));
            avgWeighted = cells.isEmpty() ? 0 : sumWeighted / sumFreq;
            avgQuadratic = Math.sqrt(sumQuadratic);
            mode = val;
        }

        public Double avgArithmetic() {

            return avgArithmetic;
        }

        public Double avgGeometric() {
            return avgGeometric;
        }

        public Double avgWeighted() {
            return avgWeighted;
        }

        public Double avgQuadratic() {
            return avgQuadratic;
        }

        public E mode() {
            return mode;
        }
    }
}
