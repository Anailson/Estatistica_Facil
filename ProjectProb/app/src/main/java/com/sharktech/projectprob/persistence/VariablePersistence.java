package com.sharktech.projectprob.persistence;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.customtable.TableColumn.IVariable;
import com.sharktech.projectprob.models.CellValue;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class VariablePersistence {

    private static VariablePersistence mPersistence;
    private static final String INITIALIZED = "INITIALIZED";

    private VariablePersistence() {
        try {
            //init();
        }catch (RealmPrimaryKeyConstraintException e){
            Log.e("RealmPrimaryKey", e.getMessage());
        }
    }

    private void init() throws RealmPrimaryKeyConstraintException {

        Realm realm = beginTransaction();

        VariableNumber flts = realm.createObject(VariableNumber.class, "Float");
        //flts.setTitle("Float");
        flts.add(new Float[]{1.3f, 2.2f, 1.3f, 4.4f, 2.3f, 4.3f, 7.4f, 7.2f, 2.2f, 2.5f});
        flts.add(new Float[]{1.2f, 1.2f, 7.3f, 4.9f, 1.3f, 7.3f, 8.4f, 7.0f, 4.2f, 2.7f});
        flts.add(new Float[]{9.2f, 2.6f, 5.3f, 4.2f, 8.3f, 7.5f, 3.4f, 7.6f, 2.2f, 2.0f});
        flts.add(new Float[]{9.7f, 4.6f, 5.8f, 9.2f, 2.3f, 7.9f, 2.4f, 7.3f, 5.2f, 2.5f});
        flts.add(new Float[]{6.7f, 0.6f, 2.8f, 1.2f, 2.7f, 4.9f, 9.4f, 7.8f, 1.2f, 2.0f});

        VariableString strs = realm.createObject(VariableString.class, "String");
        //strs.setTitle("String");
        strs.add(new String[]{"Um", "Dois", "Três", "Dois", "Cinco", "Um", "Sete"});

        VariableNumber example = realm.createObject(VariableNumber.class, "Example_1");
        //example.setTitle("Example_1");
        example.add(new Integer[]{48, 48, 49, 50, 50, 50, 50, 52, 53, 53});
        example.add(new Integer[]{54, 55, 56, 58, 58, 60, 65, 67, 70, 70});
        example.add(new Integer[]{70, 71, 72, 74, 75, 75, 76, 77, 77, 77});
        example.add(new Integer[]{78, 79, 80, 80, 81, 82, 83, 83, 85, 89});

        realm.copyToRealmOrUpdate(flts);
        realm.copyToRealmOrUpdate(strs);
        realm.copyToRealmOrUpdate(example);
        realm.commitTransaction();
    }

    public static VariablePersistence getInstance(){
        if(mPersistence == null){
            mPersistence = new VariablePersistence();
        }
        return mPersistence;
    }

    public static void initDatabase(Activity activity){
        SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        boolean initialized = preferences.getBoolean(INITIALIZED, false);
        if(!initialized){
            preferences.edit().putBoolean(INITIALIZED, true).apply();
        }
        Realm.init(activity.getApplicationContext());
    }

    public int size(){
        return getVariables().size();
    }

    public IVariable getVariable(int index){
        return getVariables().get(index);
    }

    public ArrayList<String> getTitles(){
        ArrayList<TableColumn.IVariable> variables = VariablePersistence.getInstance().getVariables();
        ArrayList<String> titles = new ArrayList<>();

        for(int i = 0; i < variables.size(); i++){
            titles.add(variables.get(i).getTitle());
        }
        return titles;
    }

    public ArrayList<IVariable> getVariables() {
        ArrayList<IVariable> variables = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();

        variables.addAll(realm.where(VariableNumber.class).findAll());
        variables.addAll(realm.where(VariableString.class).findAll());
        return variables;
    }

    public RealmList<CellValue> newCellValueList(String[] values, boolean isNumber){
        Realm realm = beginTransaction();
        RealmList<CellValue> cells = new RealmList<>();

        for(String s : values){
            CellValue cellValue = realm.createObject(CellValue.class);
            cellValue.setValue(s);
            cellValue.setNumber(isNumber);
            cells.add(cellValue);
        }
        realm.commitTransaction();
        return cells;
    }

    public boolean newVariable(String title, boolean isNumber, RealmList<CellValue> cells){

        boolean result = true;
        Realm realm = beginTransaction();

        try {
            if(isNumber) {
                VariableNumber number = realm.createObject(VariableNumber.class, title);
                number.setValues(cells);
                realm.copyToRealmOrUpdate(number);
            } else {
                VariableString string = realm.createObject(VariableString.class, title);
                string.setValues(cells);
                realm.copyToRealmOrUpdate(string);
            }
            realm.commitTransaction();
        } catch (RealmPrimaryKeyConstraintException e){
            result = false;
            realm.cancelTransaction();
        }
        return result;
    }

    public void addCell(int index, CellValue cell){
        IVariable variable = getVariable(index);
        Realm realm = beginTransaction();
        variable.getElements().add(cell);
        realm.commitTransaction();
    }

    public void addCell(int index, RealmList<CellValue> cells){
        IVariable variable = getVariable(index);
        Realm realm = beginTransaction();
        for(CellValue cell : cells){
            variable.getElements().add(cell);
        }
        realm.commitTransaction();
    }

    public void remove(int index){
        //mVariables.remove(index);
    }

    private Realm beginTransaction(){
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
        }catch (IllegalStateException e){
            Log.e("IllegalStateException", e.getMessage());
        }
        return realm;
    }
}
