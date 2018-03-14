package com.sharktech.projectprob.controllers;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.rustamg.filedialogs.FileDialog;
import com.rustamg.filedialogs.OpenFileDialog;
import com.rustamg.filedialogs.SaveFileDialog;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.alert.DefaultAlert;
import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.CellValue;
import com.sharktech.projectprob.persistence.VariablePersistence;
import com.sharktech.projectprob.views.DataAnalyseView;
import com.sharktech.projectprob.views.InferenceView;
import com.sharktech.projectprob.views.MainView;
import com.sharktech.projectprob.views.VariableTableView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import io.realm.RealmList;

public class MainController {

    private static final int REQ_STORAGE_PERMISSION = 0;
    private static final int INDEX_WRITE_STORAGE = 0;
    private static final int INDEX_READ_STORAGE = 1;
    private static final int PERMISSIONS = 2;
    private static final String TAG_IMPORT_FILE = "IMPORT_FILE";
    private static final String TAG_EXPORT_FILE = "EXPORT_FILE";
    private static final String CAN_READ = "CAN_READ";
    private static final String CAN_WRITE = "CAN_WRITE";

    private boolean canRead, canWrite;
    private FragmentActivity activity;

    public MainController(FragmentActivity activity) {
        SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        this.canRead = preferences.getBoolean(CAN_READ, false);
        this.canWrite = preferences.getBoolean(CAN_WRITE, false);
        this.activity = activity;
    }

    public void verifyStoragePermission() {
        int selPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && selPermission != PackageManager.PERMISSION_GRANTED) {
            String[] requests = new String[PERMISSIONS];
            requests[INDEX_READ_STORAGE] = Manifest.permission.READ_EXTERNAL_STORAGE;
            requests[INDEX_WRITE_STORAGE] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            ActivityCompat.requestPermissions(activity, requests, REQ_STORAGE_PERMISSION);
        }
    }

    public void permissionResult(int requestCode, int[] grantResults) {
        if (requestCode == REQ_STORAGE_PERMISSION) {
            canRead = grantResults[INDEX_READ_STORAGE] == PackageManager.PERMISSION_GRANTED;
            canWrite = grantResults[INDEX_WRITE_STORAGE] == PackageManager.PERMISSION_GRANTED;
            SharedPreferences.Editor editor = activity.getPreferences(Context.MODE_PRIVATE).edit();
            editor.putBoolean(CAN_READ, canRead);
            editor.putBoolean(CAN_WRITE, canWrite);
            editor.apply();
        }
    }

    public void selectedFile(String tag, String name, File parent) {
        if (tag.equals(TAG_EXPORT_FILE)) {
            write(name, parent);
        } else if (tag.equals(TAG_IMPORT_FILE)) {
            read(name, parent);
        }
    }

    public boolean selectMenu(int id) {

        Fragment fragment = null;
        switch (id) {
            case R.id.menu_export_data: exportFile(); break;
            case R.id.menu_import_data: importFile(); break;
            case R.id.nav_variables: fragment = new VariableTableView(); break;
            case R.id.nav_data_analyse: fragment = new DataAnalyseView(); break;
            case R.id.nav_inference: fragment = new InferenceView(); break;
            case R.id.nav_probability: showToast("Probabilidade: Em desenvolvimento"); break;
            case R.id.nav_formula: showToast("Fórmula: Em desenvolvimento"); break;
            default: return false;
        }
        return replaceFragment(fragment) > 0;
    }

    private int replaceFragment(Fragment fragment) {
        if (fragment == null) return -1;

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return fragmentManager.beginTransaction().replace(R.id.layout_main, fragment).commit();
    }

    private void exportFile() {
        if(canWrite) {
            Bundle bundle = new Bundle();
            bundle.putString(FileDialog.EXTENSION, "csv");
            SaveFileDialog dialog = new SaveFileDialog();
            dialog.setArguments(bundle);
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_NoActionBar);
            dialog.show(activity.getSupportFragmentManager(), TAG_EXPORT_FILE);
        }
    }

    private void importFile() {
        if(canRead) {
            Bundle bundle = new Bundle();
            bundle.putString(FileDialog.EXTENSION, "csv");

            FileDialog dialog = new OpenFileDialog();
            dialog.setArguments(bundle);
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
            dialog.show(activity.getSupportFragmentManager(), TAG_IMPORT_FILE);
        }
    }

    private void write(String name, File parent) {
        name = name.replaceAll("(.csv)*", "");
        File file = new File(parent, name + ".csv");
        file.setReadable(true);
        file.setWritable(true);
        ArrayList<TableColumn.IVariable> variables = VariablePersistence.getInstance().getVariables();

        try {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fOut);

            for (TableColumn.IVariable variable : variables) {
                String text = toFile(variable);
                writer.append(text).append("\n\r");
            }
            writer.close();
            fOut.close();
            MediaScannerConnection.scanFile(activity, new String[]{file.getAbsolutePath()}, null, null);
            showToast("Arquvio " + file.getName() + " salvo em " + file.getPath());

        } catch (IOException e){
            showDialog("Erro de escrita", "Não foi possível escrever no arquivo.");
        }
    }

    private void read(String name, File parent) {
        File file = new File(parent, name);
        file.setWritable(true);
        file.setReadable(true);
        if(!name.endsWith(".csv")){
            showDialog("Arquivo incompatível", "É permitido apenas arquivos .csv");
            return;
        }

        try{
            FileInputStream fis = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null){
                fromFile(line);
            }
            br.close();
            in.close();
            fis.close();

            ((MainView) activity).resume();

        } catch (IOException e){
            showDialog("Erro de leitura", "Não foi possível ler o arquivo especificado");
        }
    }

    private String toFile(TableColumn.IVariable variable) {
        StringBuilder builder = new StringBuilder();
        builder.append(variable.getTitle()).append(",");
        builder.append(variable.isNumber()).append(",");

        for (TableCell.ICell cell : variable.getElements()) {
            builder.append(cell.getTitle()).append(";");
        }
        int lastIndex = builder.lastIndexOf(";");
        return builder.substring(0, lastIndex);
    }

    private boolean fromFile(String line){
        int indexTitle = 0, indexNumber = 1, indexCells = 2;
        String[] lines = line.split(",");
        boolean created = false;

        if(lines.length == 3){
            VariablePersistence persistence = VariablePersistence.getInstance();
            String title = lines[indexTitle];
            boolean isNumber = Boolean.parseBoolean(lines[indexNumber]);
            String[] cellsValue = lines[indexCells].split(";");
            RealmList<CellValue> cells = persistence.newCellValueList(cellsValue, isNumber);

            created = persistence.newVariable(title, isNumber, cells);
            if(!created){
                showDialog("Erro ao criar variável",
                        "É possível que a variável '" + title + "' já exista.\n"
                        + "Altere o nome seu nome e tente novamente");
            }
        }
        return created;
    }

    private void showToast(String text) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }

    private void showDialog(String title, String message){
        DefaultAlert alert = new DefaultAlert(activity);
        alert.setTitle(title)
                .setMessage(message)
                .setPositiveListener("OK", (dialog, which) -> dialog.dismiss());
        alert.show();
    }
}