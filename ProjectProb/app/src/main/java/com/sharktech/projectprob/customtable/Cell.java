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

public class Cell<E extends Cell.ICell> extends android.support.v7.widget.AppCompatEditText {

    private Position mPosition;
    private Listeners mListener;
    private Value<E> mValue;

    public interface ICell<E> {

        String getTitle();

        E getElement();

        boolean isNumber();

        Float asFloat();
    }

    public Cell(Context context) {
        this(context, new Value<E>(null));
    }

    public Cell(Context context, E element){
        this(context, new Value<>(element));
    }

    public Cell(Context context, Value<E> value){
        super(context);
        this.mValue = value;
        this.mPosition = new Position(-1, -1);
        this.mListener = new Listeners(getContext(), mPosition);

        init();
    }

    private void init(){
        setText(mValue.simpleText());
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

    protected Cell.ICell getValue() {
        return mValue.getData();
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

    protected void setHeading() {

        mPosition.row = -1;
        setBackgroundColor(getContext().getResources().getColor(R.color.color_primary_light));
        setTypeface(getTypeface(), Typeface.BOLD);
        setTextColor(Color.WHITE);
        setText(mValue.simpleText());
    }

    protected static Cell newCell(Context context, final Object value){

        return new Cell<Cell.ICell>(context, new Cell.ICell() {
            @Override
            public String getTitle() { return value.toString(); }

            @Override
            public Object getElement() { return value; }

            @Override
            public boolean isNumber() { return false; }

            @Override
            public Float asFloat() { return 1f; }
        });
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
        return mValue.getCount();
    }

    public boolean isNumber() {
        return mValue.isNumber();
    }

    public double asFloat() {
        return mValue.asFloat();
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

        //View.OnLongClickListener
        @Override
        public boolean onLongClick(View v) {
            if (position.isClickable()) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show();
                v.setFocusableInTouchMode(true);
            }
            return true;
        }

        //View.OnFocusChangeListener
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (position.isClickable()) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show();
                if (!hasFocus) v.setFocusableInTouchMode(false);
            }
        }

        //TextView.OnEditorActionListener
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if(actionId == EditorInfo.IME_ACTION_DONE){

                v.setFocusableInTouchMode(false);
                v.setFocusable(false);
            }
            return false;
        }
    }
}

