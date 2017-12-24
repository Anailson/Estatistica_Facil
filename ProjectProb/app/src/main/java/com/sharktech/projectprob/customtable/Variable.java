package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.widget.LinearLayout;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.interfaces.IVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Variable<E> extends LinearLayout implements IVariable<E>{

    private int position;
    private String title;
    private ArrayList<Cell<E>> cells;
    private Analyses analyses;

    public Variable(Context context) {
        this(context, " No Title ");
    }

    public Variable(Context context, String title) {
        super(context);
        this.position = -1;
        this.title = title;
        this.cells = new ArrayList<>();
        this.analyses = null;

        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.border_light);
    }

    public void setPosition(int position) {
        if (position >= 0) {
            this.position = position;
            for (Cell cell : cells) {
                cell.setCol(position);
            }
        }
    }

    public Analyses calculate() {
        if (analyses == null) {
            analyses = new Analyses(cells);
        }
        return analyses;
    }

    public void add(E value) {
        add(new Value<>(value));
    }

    public void add(Value<E> value) {
        cells.add(new Cell<>(getContext(), value));
    }

    public void addAll(Value<E>[] values) {
        addAll(Arrays.asList(values));
    }

    public void addAll(List<Value<E>> values) {

        for (Value<E> value : values) {
            add(value);
        }
    }

    protected void emptyCell() {
        add(new Value<E>(null));
    }

    protected Variable build() {
        String title = position >= 0
                ? String.format(Locale.getDefault(), "[%d]%s(f) %s", (position + 1), "\n", this.title)
                : String.format(Locale.getDefault(), "\n %s", this.title);

        Cell cellTitle = new Cell<>(getContext(), new Value<>(title));
        cellTitle.setCol(position);
        addView(cellTitle);

        for (int row = 0; row < cells.size(); row++) {
            Cell cell = cells.get(row);
            cell.setPosition(position, row);
            addView(cell);
        }
        setHeading();
        return this;
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
        return title;
    }

    @Override
    public int nElements() {
        return cells.size();
    }

    @Override
    public ArrayList<Value> getElements(){

        ArrayList<Value> els = new ArrayList<>();
        for(Cell<E> cell : cells){
            if(!cell.isEmpty()){
                els.add(cell.getValue());
            }
        }
        return els;
    }

    @Override
    public Value<E> getElement(int index) {
        return cells.get(index).getValue();
    }

    @Override
    public void setElement(Value<E> value, int index) {
        cells.get(index).setValue(value);
    }

    @Override
    public void setBackgroundColor(int color) {

        for (Cell cell : cells) {
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
