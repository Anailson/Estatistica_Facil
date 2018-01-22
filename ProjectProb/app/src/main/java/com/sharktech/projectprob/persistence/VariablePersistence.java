package com.sharktech.projectprob.persistence;

import com.sharktech.projectprob.customtable.TableCell.ICell;
import com.sharktech.projectprob.customtable.TableColumn.IVariable;
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
        person.add(new VariableObject.ValueObject[]{
            new VariableObject.ValueObject("Joao 23"),
            new VariableObject.ValueObject("Antônio 25"),
            new VariableObject.ValueObject("Maria 23"),
            new VariableObject.ValueObject("Antônio 25"),
            new VariableObject.ValueObject("Francisca 52")
        });

        VariableNumber flts = new VariableNumber("Float");
        flts.add(new Float[]{1.3f, 2.2f, 1.3f, 4.4f});

        VariableNumber ints = new VariableNumber("Integer");
        ints.add(new Integer[]{1, 2, 3, 2});
        ints.add(new Integer[]{5, 5, 7, 3, 5, 4, 8, 5, 1, 7, 1});

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

    private IVariable getVariable(final String title, Object[] values){

        final ArrayList<ICell> cells = new ArrayList<>();
        for(final Object o : values){
            cells.add(new VariableObject.ValueObject(o));
        }

        VariableObject var = new VariableObject(title);
        var.add(cells);
        return var;
    }

    public void add(ArrayList<IVariable> vars) {
        mVarsAuxi.addAll(vars);
    }

    public void add(IVariable variable){
        mVarsAuxi.add(variable);
    }
}
