package com.example.conteodecarbohidratos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.conteodecarbohidratos.clases.Categoria;

import java.util.ArrayList;

public class CategoriaDBAccess {
    private static final String DB_NAME = "categorias.db";
    private static final Integer DB_VERSION = 1;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static CategoriaDBAccess instance;
    Cursor c = null;

    private CategoriaDBAccess (Context context){
        this.openHelper = new DatabaseCSVOpenHelper(context,DB_NAME,DB_VERSION);
    }

    public static CategoriaDBAccess getInstance(Context context){
        if (instance==null) {
            instance = new CategoriaDBAccess(context);
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

    public Categoria getCategoriaPorID(Integer idBuscar){
        Categoria categoriaActual=null;

        String[] columnas = {
                "id",
                CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE,
                CategoriaContrato.CategoriaEntry.COLUMNA_DESCRIPCION,
                CategoriaContrato.CategoriaEntry.COLUMNA_EDITABLE
        };

        String where = "id"  + " =?";
        String[] argumentos = {idBuscar.toString()};
        Cursor cursor = db.query(CategoriaContrato.CategoriaEntry.NOMBRE_TABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);


        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
            int columnaNombreIndex = cursor.getColumnIndex(CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE);
            int columnaDescripcionIndex = cursor.getColumnIndex(CategoriaContrato.CategoriaEntry.COLUMNA_DESCRIPCION);
            int columnaEditableIndex = cursor.getColumnIndex(CategoriaContrato.CategoriaEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                String descripcionActual = cursor.getString(columnaDescripcionIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                categoriaActual= new Categoria(IDActual,nombreActual, descripcionActual,editableActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
        }

        return categoriaActual;
    }


    public ArrayList<Categoria> getCategoriaAll() {
        ArrayList<Categoria> categorias = new ArrayList<>();

        String[] columnas = {
                "id",
                CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE,
                CategoriaContrato.CategoriaEntry.COLUMNA_DESCRIPCION,
                CategoriaContrato.CategoriaEntry.COLUMNA_EDITABLE
        };

        String orderby = CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE + " ASC";
        String where = null;
        String[] argumentos = null;

        Cursor cursor = db.query(CategoriaContrato.CategoriaEntry.NOMBRE_TABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                orderby);

        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
            int columnaNombreIndex = cursor.getColumnIndex(CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE);
            int columnaDescripcionIndex = cursor.getColumnIndex(CategoriaContrato.CategoriaEntry.COLUMNA_DESCRIPCION);
            int columnaEditableIndex = cursor.getColumnIndex(CategoriaContrato.CategoriaEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                String descripcionActual = cursor.getString(columnaDescripcionIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                Categoria categoriaActual= new Categoria(IDActual,nombreActual, descripcionActual,editableActual);
                categorias.add(categoriaActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
        }

        return categorias;
    }

    public ArrayList<Object> getCategoriasPorNombre(String buscarSTR) {
        ArrayList<Object> categorias = new ArrayList<>();

        String[] columnas = {
                "id",
                CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE,
                CategoriaContrato.CategoriaEntry.COLUMNA_DESCRIPCION,
                CategoriaContrato.CategoriaEntry.COLUMNA_EDITABLE
        };

        String where =  " UPPER(" + CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE  + ") LIKE ?";
        String [] argumentos= {"%" + buscarSTR.toUpperCase() + "%"};

        String orderby = CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE + " ASC";

        Cursor cursor = db.query(CategoriaContrato.CategoriaEntry.NOMBRE_TABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                orderby);

        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
            int columnaNombreIndex = cursor.getColumnIndex(CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE);
            int columnaDescripcionIndex = cursor.getColumnIndex(CategoriaContrato.CategoriaEntry.COLUMNA_DESCRIPCION);
            int columnaEditableIndex = cursor.getColumnIndex(CategoriaContrato.CategoriaEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                String descripcionActual = cursor.getString(columnaDescripcionIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                Categoria categoriaActual= new Categoria(IDActual,nombreActual, descripcionActual,editableActual);
                categorias.add(categoriaActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
        }
        return categorias;
    }

    public Integer InsertarCategoria(Categoria nuevaCategoria) {

        ContentValues values = new ContentValues();

        values.put(CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE,nuevaCategoria.getMyNOMBRE());
        values.put(CategoriaContrato.CategoriaEntry.COLUMNA_DESCRIPCION,nuevaCategoria.getMyDESCRIPCION());
        values.put(CategoriaContrato.CategoriaEntry.COLUMNA_EDITABLE,1);

        long NuevaIDFilaLNG = db.insert(CategoriaContrato.CategoriaEntry.NOMBRE_TABLA, null,values);
        Integer NuevaIDFila = Long.valueOf(NuevaIDFilaLNG).intValue();
        return NuevaIDFila;
    }

    public void EliminarCategoria(Integer categoriaId) {

        String whereClause = "id" + "=?";
        String whereArgs[] = {categoriaId.toString()};

        db.delete(CategoriaContrato.CategoriaEntry.NOMBRE_TABLA, whereClause,whereArgs);
    }

    public void UpdateCategoria(Categoria categoriaActual) {

        ContentValues values = new ContentValues();

        values.put(CategoriaContrato.CategoriaEntry.COLUMNA_NOMBRE,categoriaActual.getMyNOMBRE());
        values.put(CategoriaContrato.CategoriaEntry.COLUMNA_DESCRIPCION,categoriaActual.getMyDESCRIPCION());
        values.put(CategoriaContrato.CategoriaEntry.COLUMNA_EDITABLE,1);

        String whereClause = "id" + "=?";
        String whereArgs[] = {categoriaActual.getMyID().toString()};
        db.update(CategoriaContrato.CategoriaEntry.NOMBRE_TABLA, values, whereClause, whereArgs);
    }


    public Integer CategoriaEnUso(Context contexto,Integer categoriaId) {
        Integer enUso = -1;
        AlimentoTablaDBAccess alimentoTablaDBA = AlimentoTablaDBAccess.getInstance(contexto);
        alimentoTablaDBA.open();
        Integer cantUsoAlimentoTabla = alimentoTablaDBA.getCategoriaenUso(categoriaId);
        alimentoTablaDBA.close();

        AlimentoHelper alimentoDBA = new AlimentoHelper(contexto);
        Integer cantUsoAlimento = alimentoDBA.getCategoriaenUso(categoriaId);
        alimentoDBA.close();

        if ((!(cantUsoAlimentoTabla == -1)) && (!(cantUsoAlimento == -1))) {
            enUso = cantUsoAlimentoTabla + cantUsoAlimento;
        }
        return enUso;
    }

}
