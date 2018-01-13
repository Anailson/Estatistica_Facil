package com.sharktech.projectprob.persistence;

import android.content.Context;

import com.sharktech.projectprob.customtable.Cell.ICell;
import com.sharktech.projectprob.customtable.Variable.IVariable;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableObject;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;


public class VariablePersistence {

    private ArrayList<IVariable> mVariables, mVarsAuxi;

    private static VariablePersistence mPersistence;

    private VariablePersistence() {
        this.mVariables = new ArrayList<>();
        this.mVarsAuxi = new ArrayList<>();
    }

    public static VariablePersistence getInstance(){
        if(mPersistence == null){
            mPersistence = new VariablePersistence();
        }
        return mPersistence;
    }

    public ArrayList<IVariable> getVariables(IVariable variables) {
        add(variables);
        return getVariables();
    }

    public ArrayList<IVariable> getVariables(ArrayList<IVariable> variables) {
        add(variables);
        return getVariables();
    }

    public ArrayList<IVariable> getVariables() {
        mVariables = new ArrayList<>();


        VariableObject person = new VariableObject("Pessoa");
        person.add(new VariableObject.Person[]{
            new VariableObject.Person("Joao", 22),
            new VariableObject.Person("Antônio", 35),
            new VariableObject.Person("Maria", 23),
            new VariableObject.Person("Antônio", 35),
            new VariableObject.Person("Francisca", 52)
        });

        VariableNumber flts = new VariableNumber("Float");
        flts.add(new Float[]{1.3f, 2.2f, 1.3f, 4.4f});

        VariableNumber ints = new VariableNumber("Integer");
        ints.add(new Integer[]{1, 2, 3, 2, 5, 1, 7, 3, 5, 4, 8, 1, 7, 1});

        VariableString chars = new VariableString("Character");
        chars.add(new Character[]{'U', 'D', 'U', 'T', 'U'});

        VariableString strs = new VariableString("String");
        strs.add(new String[]{"Um", "Dois", "Três", "Dois", "Cinco", "Um", "Sete"});

        mVariables.add(flts);
        mVariables.add(ints);
        mVariables.add(chars);
        mVariables.add(strs);
        mVariables.add(person);

        if(mVarsAuxi.size() > 0){
            mVariables.addAll(mVarsAuxi);
        }

        return mVariables;
    }

    private IVariable getVariable(Context context, final String title, Object[] values){

        final ArrayList<ICell> cells = new ArrayList<>();
        for(final Object o : values){
            cells.add(new ICell() {
                @Override
                public String getTitle() { return o.toString();}

                @Override
                public Object getElement() { return o; }

                @Override
                public boolean isNumber() { return false; }

                @Override
                public Float asFloat() { return null; }
            });
        }

        return new IVariable () {
            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public int nElements() {
                return cells.size();
            }

            @Override
            public ArrayList<ICell> getElements() {
                return cells;
            }

            @Override
            public ICell getElement(int index) {
                return cells.get(index);
            }

            @Override
            public void setElement(ICell value, int index) {

            }
        };
    }

    public void add(ArrayList<IVariable> vars) {
        mVarsAuxi.addAll(vars);
    }

    public void add(IVariable variable){
        mVarsAuxi.add(variable);
    }
}
