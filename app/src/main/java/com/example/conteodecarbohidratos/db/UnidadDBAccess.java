package com.example.conteodecarbohidratos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.conteodecarbohidratos.clases.Unidad;

import java.util.ArrayList;

public class UnidadDBAccess {
    private static final String DB_NAME = "unidades.db";
    private static final Integer DB_VERSION = 1;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static UnidadDBAccess instance;
    Cursor c = null;

    private UnidadDBAccess (Context context){
        this.openHelper = new DatabaseCSVOpenHelper(context,DB_NAME,DB_VERSION);
    }

    public static UnidadDBAccess getInstance(Context context){
        if (instance==null) {
            instance = new UnidadDBAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if (db!=null) {
            this.db.close();
        }
    }

    public Unidad getUnidadPorID(Integer idBuscar){
        Unidad unidadActual=null;

        String[] columnas = {
                "id",
                UnidadContrato.UnidadEntry.COLUMNA_NOMBRE,
                UnidadContrato.UnidadEntry.COLUMNA_DESCRIPCION,
                UnidadContrato.UnidadEntry.COLUMNA_EDITABLE
        };

        String where = "id"  + " =?";
        String[] argumentos = {idBuscar.toString()};
        Cursor cursor = db.query(UnidadContrato.UnidadEntry.NOMBRE_TABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);


        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
            int columnaNombreIndex = cursor.getColumnIndex(UnidadContrato.UnidadEntry.COLUMNA_NOMBRE);
            int columnaDescripcionIndex = cursor.getColumnIndex(UnidadContrato.UnidadEntry.COLUMNA_DESCRIPCION);
            int columnaEditableIndex = cursor.getColumnIndex(UnidadContrato.UnidadEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                String descripcionActual = cursor.getString(columnaDescripcionIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                unidadActual= new Unidad(IDActual,nombreActual,descripcionActual, editableActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
        }

        return unidadActual;
    }


    public ArrayList<Unidad> getUnidadAll() {
        ArrayList<Unidad> unidades = new ArrayList<>();

        String[] columnas = {
                "id",
                UnidadContrato.UnidadEntry.COLUMNA_NOMBRE,
                UnidadContrato.UnidadEntry.COLUMNA_DESCRIPCION,
                UnidadContrato.UnidadEntry.COLUMNA_EDITABLE
        };

        String orderby = UnidadContrato.UnidadEntry.COLUMNA_NOMBRE + " ASC";
        String where = null;
        String[] argumentos = null;

        Cursor cursor = db.query(UnidadContrato.UnidadEntry.NOMBRE_TABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                orderby);


        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
            int columnaNombreIndex = cursor.getColumnIndex(UnidadContrato.UnidadEntry.COLUMNA_NOMBRE);
            int columnaDescripcionIndex = cursor.getColumnIndex(UnidadContrato.UnidadEntry.COLUMNA_DESCRIPCION);
            int columnaEditableIndex = cursor.getColumnIndex(UnidadContrato.UnidadEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                String descripcionActual = cursor.getString(columnaDescripcionIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                Unidad unidadActual= new Unidad(IDActual,nombreActual, descripcionActual,editableActual);
                unidades.add(unidadActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
        }

        return unidades;
    }

    public ArrayList<Object> getUnidadesPorNombre(String buscarSTR) {
        ArrayList<Object> unidades = new ArrayList<>();

        String[] columnas = {
                "id",
                UnidadContrato.UnidadEntry.COLUMNA_NOMBRE,
                UnidadContrato.UnidadEntry.COLUMNA_DESCRIPCION,
                UnidadContrato.UnidadEntry.COLUMNA_EDITABLE
        };

        String where =  " UPPER(" + UnidadContrato.UnidadEntry.COLUMNA_NOMBRE  + ") LIKE ?";
        String [] argumentos= {"%" + buscarSTR.toUpperCase() + "%"};

        String orderby = UnidadContrato.UnidadEntry.COLUMNA_NOMBRE + " ASC";

        Cursor cursor = db.query(UnidadContrato.UnidadEntry.NOMBRE_TABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                orderby);

        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
            int columnaNombreIndex = cursor.getColumnIndex(UnidadContrato.UnidadEntry.COLUMNA_NOMBRE);
            int columnaDescripcionIndex = cursor.getColumnIndex(UnidadContrato.UnidadEntry.COLUMNA_DESCRIPCION);
            int columnaEditableIndex = cursor.getColumnIndex(UnidadContrato.UnidadEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                String descripcionActual = cursor.getString(columnaDescripcionIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                Unidad unidadActual= new Unidad(IDActual,nombreActual, descripcionActual,editableActual);
                unidades.add(unidadActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
        }
        return unidades;
    }

    public Integer InsertarUnidad(Unidad nuevaUnidad) {
        ContentValues values = new ContentValues();
        values.put(UnidadContrato.UnidadEntry.COLUMNA_NOMBRE,nuevaUnidad.getMyNOMBRE());
        values.put(UnidadContrato.UnidadEntry.COLUMNA_DESCRIPCION,nuevaUnidad.getMyDESCRIPCION());
        values.put(UnidadContrato.UnidadEntry.COLUMNA_EDITABLE,1);

        long NuevaIDFilaLNG = db.insert(UnidadContrato.UnidadEntry.NOMBRE_TABLA, null,values);
        Integer NuevaIDFila = Long.valueOf(NuevaIDFilaLNG).intValue();
        return NuevaIDFila;
    }

    public void EliminarUnidad(Integer unidadId) {
        String whereClause = "id" + "=?";
        String whereArgs[] = {unidadId.toString()};

        db.delete(UnidadContrato.UnidadEntry.NOMBRE_TABLA, whereClause,whereArgs);
    }

    public void UpdateUnidad(Unidad unidadActual) {
        ContentValues values = new ContentValues();
        values.put(UnidadContrato.UnidadEntry.COLUMNA_NOMBRE,unidadActual.getMyNOMBRE());
        values.put(UnidadContrato.UnidadEntry.COLUMNA_DESCRIPCION,unidadActual.getMyDESCRIPCION());
        values.put(UnidadContrato.UnidadEntry.COLUMNA_EDITABLE,1);

        String whereClause = "id" + "=?";
        String whereArgs[] = {unidadActual.getMyID().toString()};
        db.update(UnidadContrato.UnidadEntry.NOMBRE_TABLA, values, whereClause, whereArgs);
    }

    public Integer UnidadEnUso(Context contexto,Integer unidadId) {
        Integer enUso = -1;
        AlimentoTablaDBAccess alimentoTablaDBA = AlimentoTablaDBAccess.getInstance(contexto);
        alimentoTablaDBA.open();
        Integer cantUsoAlimentoTabla = alimentoTablaDBA.getUnidadenUso(unidadId);
        alimentoTablaDBA.close();

        AlimentoHelper alimentoDBA = new AlimentoHelper(contexto);
        Integer cantUsoAlimento = alimentoDBA.getUnidadenUso(unidadId);
        alimentoDBA.close();

        if ((!(cantUsoAlimentoTabla == -1)) && (!(cantUsoAlimento == -1))) {
            enUso = cantUsoAlimentoTabla + cantUsoAlimento;
        }

        return enUso;
    }
}