package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sharktech.projectprob.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Variable<E extends Variable.IVariable> extends LinearLayout {

    private int mPosition;
    private E mVariable;
    private Analyses mAnalyses;
    private ArrayList<Cell<Cell.ICell>> mCells;

    public interface IVariable extends Serializable {

        String getTitle();

        int nElements();

        ArrayList<Cell.ICell> getElements();

        Cell.ICell getElement(int index);

        void setElement(Cell.ICell value, int index);
    }

    public Variable(Context context) {
        this(context, null);
    }

    public Variable(Context context, E variable) {
        super(context);
        this.mPosition = -1;
        this.mVariable = variable;
        this.mCells = new ArrayList<>();
        this.mAnalyses = null;

        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.border_light);

        for(int i = 0; i < mVariable.nElements(); i++){
            mCells.add(new Cell<>(getContext(), mVariable.getElement(i)));
        }
    }

    public void setPosition(int position) {
        if (position >= 0) {
            this.mPosition = position;

            for (int i = 0; i < mVariable.nElements(); i++) {

                mCells.get(i).setCol(position);
            }
        }
    }

    public Analyses calculate() {
        if (mAnalyses == null) {
            mAnalyses = new Analyses(mVariable.getElements());
        }
        return mAnalyses;
    }

    protected int nElements(){
        return mVariable.nElements();
    }

    protected Variable build() {

        Cell cellTitle = Cell.newCell(getContext(), mVariable.getTitle());
        cellTitle.setCol(mPosition);
        addView(cellTitle);

        for (int row = 0; row < mVariable.nElements(); row++) {
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

    protected static Variable newVariable(final Context context, final Object variable){

        return new Variable<>(context, new IVariable () {
            @Override
            public String getTitle() { return variable.toString(); }

            @Override
            public int nElements() { return 0; }

            @Override
            public ArrayList<Cell.ICell> getElements() { return new ArrayList<>(); }

            @Override
            public Cell.ICell getElement(int index) { return null; }

            @Override
            public void setElement(Cell.ICell value, int index) {}
        });
    }


    @Override
    public void setBackgroundColor(int color) {

        for (int i = 0; i < mVariable.nElements(); i++) {
            mCells.get(i).setHeading();
        }
        super.setBackgroundColor(color);
    }

    public class Analyses implements Serializable{

        private double avgArithmetic, avgGeometric, avgWeighted, avgQuadratic;
        private Cell.ICell mode;

        private Analyses(ArrayList<Cell.ICell> cells) {

            calculate(cells);
        }

        private void calculate(ArrayList<Cell.ICell> cells){

            Integer sumFreq = 0;
            Double sumArithmetic = 0d;
            Double prodGeometric = 1d;
            Double sumWeighted = 0d;
            Double sumQuadratic = 0d;
            int countMode = 0;
            Cell.ICell val = null;

            for (int i = 0; i < mVariable.nElements(); i++) {

                Cell cell = mCells.get(i);
                if(cell.getCount() > countMode){
                    countMode = cell.getCount();

                    val = cell.getValue();
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

        public Cell.ICell mode() {
            return mode;
        }
    }
}
