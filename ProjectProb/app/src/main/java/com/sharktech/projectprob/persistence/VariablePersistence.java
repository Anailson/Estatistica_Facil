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
        init();
    }

    private void init(){
        VariableObject person = new VariableObject("Pessoa");
        person.add(new VariableObject.ValueObject[]{
                new VariableObject.ValueObject("Joao 23"),
                new VariableObject.ValueObject("Antônio 25"),
                new VariableObject.ValueObject("Maria 23"),
                new VariableObject.ValueObject("Antônio 25"),
                new VariableObject.ValueObject("Francisca 52")
        });

        VariableNumber flts = new VariableNumber("Float");
        flts.add(new Float[]{1.3f, 2.2f, 1.3f, 4.4f, 2.3f, 4.3f, 7.4f, 7.2f, 2.2f, 2.5f});
        flts.add(new Float[]{1.2f, 1.2f, 7.3f, 4.9f, 1.3f, 7.3f, 8.4f, 7.0f, 4.2f, 2.7f});
        flts.add(new Float[]{9.2f, 2.6f, 5.3f, 4.2f, 8.3f, 7.5f, 3.4f, 7.6f, 2.2f, 2.0f});
        flts.add(new Float[]{9.7f, 4.6f, 5.8f, 9.2f, 2.3f, 7.9f, 2.4f, 7.3f, 5.2f, 2.5f});
        flts.add(new Float[]{6.7f, 0.6f, 2.8f, 1.2f, 2.7f, 4.9f, 9.4f, 7.8f, 1.2f, 2.0f});

        VariableNumber ints = new VariableNumber("Integer");
        ints.add(new Integer[]{1, 3, 5, 8, 1, 2, 6, 9, 4, 7});
        ints.add(new Integer[]{2, 9, 7, 3, 6, 2, 9, 5, 1, 7});
        ints.add(new Integer[]{4, 3, 9, 6, 8, 5, 4, 8, 2, 1});
        ints.add(new Integer[]{6, 8, 9, 4, 3, 3, 7, 5, 4, 8});
        ints.add(new Integer[]{9, 5, 1, 6, 7, 9, 5, 3, 7, 2});

        VariableString chars = new VariableString("Character");
        chars.add(new Character[]{'U', 'D', 'U', 'T', 'U'});

        VariableString strs = new VariableString("String");
        strs.add(new String[]{"Um", "Dois", "Três", "Dois", "Cinco", "Um", "Sete"});

        mVariables.add(flts);
        mVariables.add(ints);
        mVariables.add(chars);
        mVariables.add(strs);
        mVariables.add(person);
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

    public int size(){
        return getVariables().size();
    }

    public IVariable getVariable(int index){
        return getVariables().get(index);
    }

    public ArrayList<IVariable> getVariables() {

        if(mVarsAuxi.size() > 0){
            mVariables.addAll(mVarsAuxi);
            mVarsAuxi.clear();
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

    public void remove(int index){
        mVariables.remove(index);
    }
}
