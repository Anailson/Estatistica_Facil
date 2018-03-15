package com.sharktech.projectprob.persistence;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.customtable.TableColumn.IVariable;
import com.sharktech.projectprob.models.CellValue;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class VariablePersistence {

    private static VariablePersistence mPersistence;
    private static final String INITIALIZED = "INITIALIZED";
    private static final String TITLE = "title";

    private VariablePersistence() {}

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

        if (variable.isNumber()) {
            VariableNumber num = (VariableNumber) variable;
            RealmList<CellValue> cells = num.getValues();
            cells.add(cell);
            num.setValues(cells);
            realm.copyToRealmOrUpdate(num);
        } else {
            VariableString str = (VariableString) variable;
            RealmList<CellValue> cells = str.getValues();
            cells.add(cell);
            str.setValues(cells);
            realm.copyToRealmOrUpdate(str);
        }
        realm.commitTransaction();
    }

    public void addCell(int index, RealmList<CellValue> cells){
        IVariable variable = getVariable(index);
        Realm realm = beginTransaction();


        if (variable.isNumber()) {

            VariableNumber num = (VariableNumber) variable;
            RealmList<CellValue> elements = num.getValues();
            elements.addAll(cells);
            num.setValues(elements);
            realm.copyToRealmOrUpdate(num);
        } else {
            VariableString str = (VariableString) variable;
            RealmList<CellValue> elements = str.getValues();
            elements.addAll(cells);
            str.setValues(elements);
            realm.copyToRealmOrUpdate(str);
        }
        realm.commitTransaction();
    }

    public void remove(int index){
        IVariable variable = getVariable(index);
        Class<? extends RealmObject> cls = variable.isNumber() ? VariableNumber.class : VariableString.class;

        Realm realm = beginTransaction();
        RealmObject object = realm.where(cls).equalTo(TITLE, variable.getTitle()).findFirst();
        if(object != null) object.deleteFromRealm();
        realm.commitTransaction();
    }

    public void remove(int indexCol, int indexRow){
        final IVariable variable = getVariable(indexCol);
        Class<? extends RealmObject> cls = variable.isNumber() ? VariableNumber.class : VariableString.class;

        Realm realm = beginTransaction();
        RealmObject object = realm.where(cls).equalTo(TITLE, variable.getTitle()).findFirst();
        if(object != null){
            CellValue cell = (CellValue) ((VariableString) object).getElement(indexRow);
            cell.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public void edit(int indexCol, int indexRow, TableCell.ICell cell){
        IVariable variable = getVariable(indexCol);
        Realm realm = beginTransaction();
        CellValue oldValue;

        if(variable.isNumber()) {
            VariableNumber num = (VariableNumber) variable;
            oldValue = (CellValue) num.getElement(indexRow);
            oldValue.setValue(cell.getTitle());
            realm.copyToRealmOrUpdate(num);
        } else {
            VariableString str = (VariableString) variable;
            oldValue = (CellValue) str.getElement(indexRow);
            oldValue.setValue(cell.getTitle());
            realm.copyToRealmOrUpdate(str);
        }
        realm.commitTransaction();
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
