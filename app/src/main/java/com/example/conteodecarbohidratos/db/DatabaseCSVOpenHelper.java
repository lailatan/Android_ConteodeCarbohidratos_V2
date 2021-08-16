package com.example.conteodecarbohidratos.db;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
/*
Poner en dependencias del Gradle Scripts/build.gradle(Module: app)
compile 'com.readystatesoftware.sqliteasset:sqliteassethelper:+'
y sincronizar.
Las bases de datos deben copiarse en main/assets/databases/
y en el celular van al data/data/com.example.xxx/cache/databases
* */

public class DatabaseCSVOpenHelper extends SQLiteAssetHelper {


        public DatabaseCSVOpenHelper(Context context, String dbName, Integer dbVersion) {
            super(context, dbName, null, dbVersion);
        }
}