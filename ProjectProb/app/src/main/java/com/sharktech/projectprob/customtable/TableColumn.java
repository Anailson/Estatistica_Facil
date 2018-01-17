package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.models.VariableString;

import java.io.Serializable;
import java.util.ArrayList;

public class TableColumn<E extends TableColumn.IVariable> extends LinearLayout {

    private int mPosition;
    private E mVariable;
    private Analyses mAnalyses;
    private ArrayList<TableCell<TableCell.ICell>> mCells;

    public interface IVariable extends Serializable {

        String getTitle();

        int nElements();

        ArrayList<TableCell.ICell> getElements();

        TableCell.ICell getElement(int index);

        void setElement(TableCell.ICell value, int index);
    }

    public TableColumn(Context context) {
        this(context, null);
    }

    public TableColumn(Context context, E variable) {
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
            mCells.add(new TableCell<>(getContext(), mVariable.getElement(i)));
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

    protected TableColumn build() {

        TableCell cellTitle = new TableCell<>(getContext(), new VariableString.ValueString(mVariable.getTitle()));
        cellTitle.setCol(mPosition);
        addView(cellTitle);

        for (int row = 0; row < mVariable.nElements(); row++) {
            TableCell cell = mCells.get(row);
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

            if (getChildAt(0) instanceof TableCell) {
                TableCell cell = (TableCell) getChildAt(0);
                cell.setHeading();
            }
        }
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
        private TableCell.ICell mode;

        private Analyses(ArrayList<TableCell.ICell> cells) {

            calculate(cells);
        }

        private void calculate(ArrayList<TableCell.ICell> cells){
/*
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
                    sumArithmetic += cell.asNumber();
                    prodGeometric *= Math.pow(cell.asNumber(), cell.getCount());
                    sumWeighted += cell.asNumber() * cell.getCount();
                    sumQuadratic += Math.pow(cell.asNumber(), 2d) * cell.getCount();
                }
            }

            avgArithmetic = cells.isEmpty() ? 0 : sumArithmetic / cells.size();
            avgGeometric = Math.pow(prodGeometric, (1d / sumFreq));
            avgWeighted = cells.isEmpty() ? 0 : sumWeighted / sumFreq;
            avgQuadratic = Math.sqrt(sumQuadratic);
            mode = val;
            */
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

        public TableCell.ICell mode() {
            return mode;
        }
    }
}
