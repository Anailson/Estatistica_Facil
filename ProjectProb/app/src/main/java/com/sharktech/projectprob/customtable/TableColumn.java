package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.models.CellValue;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.RealmList;

public class TableColumn<E extends TableColumn.IVariable> extends LinearLayout {

    private int mPosition;
    private E mVariable;
    private ArrayList<TableCell> mCells;

    public interface IVariable extends Serializable {

        String getTitle();

        int nElements();

        boolean isNumber();

        RealmList<TableCell.ICell> getElements();

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

        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.border_light);

        for(int i = 0; i < mVariable.nElements(); i++){
            mCells.add(new TableCell(getContext(), mVariable.getElement(i), mVariable.isNumber()));
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

    protected TableColumn build() {

        TableCell cellTitle = new TableCell(getContext(), new CellValue(mVariable.getTitle()), mVariable.isNumber());
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
}
