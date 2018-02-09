package com.sharktech.projectprob.analyse;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.ArrayList;

class SortedCellsList {

    enum Quart {
        FIRST, SECOND, THIRD
    }

    private SortedGenericList<TableCell.ICell> mCells;
    private SortedGenericList<DataAnalyseValue> mValues;

    SortedCellsList() {

        mCells = new SortedGenericList<>(TableCell.ICell.class, new SorterCallback<TableCell.ICell>());
        mValues = new SortedGenericList<>(DataAnalyseValue.class, new SorterCallback<DataAnalyseValue>());
    }

    ArrayList<DataAnalyseValue> getValues() {
        ArrayList<DataAnalyseValue> list = new ArrayList<>();
        for (int i = 0; i < mValues.size(); i++) {
            list.add(mValues.get(i));
        }
        return list;
    }

    int valuesSize() {
        return mValues.size();
    }

    void clear() {
        mCells.clear();
        mValues.clear();
    }

    boolean init(TableColumn.IVariable variable) {

        if (variable == null || variable.nElements() == 0) return false;
        for (TableCell.ICell cell : variable.getElements()) {
            int index = indexOfValue(cell);

            if (index >= 0) {
                mValues.get(index).inc();
            } else {
                mValues.add(new DataAnalyseValue(cell));
            }
            mCells.add(cell);
        }
        return true;
    }

    double quarterValue(Quart quart) {

        int n = mCells.size() + 1;
        float val = (quart == Quart.FIRST) ? n / 4f
                : (quart == Quart.SECOND) ? n / 2f
                : (quart == Quart.THIRD) ? 3 * n / 4f
                : -1;
        if (val == -1) return -1;

        int supIndex = (int) Math.ceil(val);

        TableCell.ICell min = mCells.get(supIndex - 2),
                max = mCells.get(supIndex - 1);

        return (!min.isNumber() || !max.isNumber()) ? -1
                : difIsZero(supIndex, val) ? max.asNumber()
                : (max.asNumber() + min.asNumber()) / 2f;
    }

    private boolean difIsZero(double a, double b) {
        return a - b == 0;
    }

    private int indexOfValue(TableCell.ICell val) {

        for (int i = 0; i < valuesSize(); i++) {
            TableCell.ICell cell = mValues.get(i).getValue();
            if (val.isNumber() && cell.isNumber() && val.asNumber().doubleValue() == cell.asNumber().doubleValue()) {
                return i;
            }
        }
        return -2;
    }

    private class SorterCallback<T> implements SortedGenericList.ISorter<T> {

        @Override
        public int compare(T item1, T item2) {

            int instance = instanceOf(item1, item2);
            return (instance == 0) ? compare((TableCell.ICell) item1, (TableCell.ICell) item2)
                    : (instance == 1) ? compare(((DataAnalyseValue) item1).getValue(), ((DataAnalyseValue) item2).getValue())
                    : 0;
        }

        private int compare(TableCell.ICell cell1, TableCell.ICell cell2) {

            if (cell1.isNumber() && cell2.isNumber()) {
                return cell1.asNumber().doubleValue() == cell2.asNumber().doubleValue() ? 0
                        : cell1.asNumber() > cell2.asNumber() ? 1 : -1;
            }
            return cell1.getTitle().compareTo(cell2.getTitle());
        }

        private int instanceOf(T item1, T item2) {
            return item1 instanceof TableCell.ICell && item2 instanceof TableCell.ICell ? 0
                    : item1 instanceof DataAnalyseValue && item2 instanceof DataAnalyseValue ? 1
                    : -1;
        }
    }
}
