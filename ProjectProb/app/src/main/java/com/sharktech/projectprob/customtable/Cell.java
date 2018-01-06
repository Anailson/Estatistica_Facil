package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.interfaces.ICell;

public class Cell<E> extends android.support.v7.widget.AppCompatEditText {

    private Position mPosition;
    private Listeners mListener;
    private Value<E> mValue;
    private ICell<E> mCell;

    public Cell(Context context) {
        this(context, new Value<E>(null));
    }

    public Cell(Context context, E value){
        this(context, new Value<>(value));
    }

    public Cell(Context context, Value<E> value){
        super(context);
        this.mValue = value;
        this.mPosition = new Position(-1, -1);
        this.mListener = new Listeners(getContext(), mPosition);
        this.mCell = emptyListener();

        init();
    }

    private void init(){
        setText(mValue.formattedText());
        setSingleLine(true);
        setTextColor(getResources().getColor(R.color.color_default));
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setPadding(20, 10, 20, 5);
        setTextSize(16);
        setFocusableInTouchMode(false);
        setImeOptions(EditorInfo.IME_ACTION_DONE);
        setOnLongClickListener(mListener);
        setOnFocusChangeListener(mListener);
        setOnEditorActionListener(mListener);
    }

    protected Value<E> getValue() {
        return mValue;
    }

    protected void setValue(Value<E> value){
        this.mValue = value;
    }

    protected boolean isEmpty(){
        return mValue == null || mValue.isEmpty();
    }

    protected void setCol(int col) {
        setPosition(col, mPosition.row);
    }

    protected void setPosition(int col, int row) {

        if(mValue != null) {
            mPosition.setPosition(col, row);
        }
        if(col < 0){
            setHeading();
        }
        setGravity();
    }

    protected void setListener(ICell<E> listener) {
        this.mCell = listener;
    }

    protected void setHeading() {

        mPosition.row = -1;
        setBackgroundColor(getContext().getResources().getColor(R.color.color_primary_light));
        setTypeface(getTypeface(), Typeface.BOLD);
        setTextColor(Color.WHITE);
        setText(mValue.simpleText());
    }

    protected static <T> Cell<T> newCell(Context context, final T value){

        Cell<T> cell = new Cell<>(context, value);
        cell.setListener(Cell.defaultListener(value));
        return cell;
    }

    protected static <T> Cell<T> emptyCell(Context context) {
        Cell<T> cell = new Cell<>(context);
        cell.setListener(Cell.<T>emptyListener());
        return cell;
    }

    private static <T> ICell<T> defaultListener(final T value) {
        return new ICell<T>() {

            @Override
            public String getTitle() { return value.toString(); }

            @Override
            public T getElement() { return value; }

            @Override
            public int getCount() { return 1; }

            @Override
            public boolean isNumber() { return false; }

            @Override
            public Float asFloat() { return -1f; }
        };
    }

    private static <T> ICell<T> emptyListener() {
        return new ICell<T>() {

            @Override
            public String getTitle() { return " - "; }

            @Override
            public T getElement() { return null; }

            @Override
            public int getCount() { return 0; }

            @Override
            public boolean isNumber() { return false; }

            @Override
            public Float asFloat() { return -1f; }

        };
    }



    private void setGravity(){
        if(mPosition.col >=  0 && mPosition.row >= 0){
            setGravity(Gravity.START);
        } else if(mPosition.col >= 0 && mPosition.row < 0) {
            setGravity(Gravity.CENTER);
        } else if(mPosition.col < 0 && mPosition.row < 0){
            setGravity(Gravity.END);
        }
    }

    public Integer getCount() {
        return mCell.getCount();
    }

    public boolean isNumber() {
        return mCell.isNumber();
    }

    public double asFloat() {
        return mCell.asFloat();
    }

    private class Position {
        private int col, row;

        private Position(int col, int row) {
            setPosition(col, row);
        }

        private void setPosition(int col, int row) {
            this.col = col;
            this.row = row;
        }

        private boolean isClickable() {
            return row >= 0 && col >= 0;
        }

        @Override
        public String toString() {
            return "Pos: (" + col + "x" + row + ");";
        }
    }

    private class Listeners implements View.OnLongClickListener, View.OnFocusChangeListener, TextView.OnEditorActionListener {

        private Context context;
        private Position position;

        private Listeners(Context context, Position position) {
            this.context = context;
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {
            if (position.isClickable()) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show();
                v.setFocusableInTouchMode(true);
            }
            return true;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (position.isClickable()) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show();
                if (!hasFocus) v.setFocusableInTouchMode(false);
            }
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if(actionId == EditorInfo.IME_ACTION_DONE){

                v.setFocusableInTouchMode(false);
                v.setFocusable(false);
            }
            return false;
        }
    }}

