package com.example.conteodecarbohidratos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.conteodecarbohidratos.MarcaActivity;
import com.example.conteodecarbohidratos.clases.Marca;
import com.example.conteodecarbohidratos.clases.Nota;
import com.example.conteodecarbohidratos.db.MarcaContrato.MarcaEntry;

import java.util.ArrayList;
import java.util.List;

public class MarcaDBAccess {
    private static final String DB_NAME = "marcas.db";
    private static final Integer DB_VERSION = 1;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static MarcaDBAccess instance;
    Cursor c = null;

    private MarcaDBAccess (Context context){
        this.openHelper = new DatabaseCSVOpenHelper(context,DB_NAME,DB_VERSION);
    }

    public static MarcaDBAccess getInstance(Context context){
        if (instance==null) {
            instance = new MarcaDBAccess(context);
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

    public Marca getMarcaPorID(Integer idBuscar){
        Marca marcaActual=null;

        String[] columnas = {
                "id",
                MarcaEntry.COLUMNA_NOMBRE,
                MarcaEntry.COLUMNA_DESCRIPCION,
                MarcaEntry.COLUMNA_EDITABLE
        };

        String where = "id"  + " =?";
        String[] argumentos = {idBuscar.toString()};
        Cursor cursor = db.query(MarcaEntry.NOMBRE_TABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);


        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
            int columnaNombreIndex = cursor.getColumnIndex(MarcaEntry.COLUMNA_NOMBRE);
            int columnaDescripcionIndex = cursor.getColumnIndex(MarcaEntry.COLUMNA_DESCRIPCION);
            int columnaEditableIndex = cursor.getColumnIndex(MarcaEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                String descripcionActual = cursor.getString(columnaDescripcionIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                marcaActual= new Marca(IDActual,nombreActual, descripcionActual,editableActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
        }

        return marcaActual;
    }


    public ArrayList<Marca> getMarcaAll() {
        ArrayList<Marca> marcas = new ArrayList<>();

        String[] columnas = {
                "id",
                MarcaEntry.COLUMNA_NOMBRE,
                MarcaEntry.COLUMNA_DESCRIPCION,
                MarcaEntry.COLUMNA_EDITABLE
        };

        String orderby = MarcaEntry.COLUMNA_NOMBRE + " ASC";

        Cursor cursor = db.query(MarcaEntry.NOMBRE_TABLA,
                columnas,
                null,
                null,
                null,
                null,
                orderby);

        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
            int columnaNombreIndex = cursor.getColumnIndex(MarcaEntry.COLUMNA_NOMBRE);
            int columnaDescripcionIndex = cursor.getColumnIndex(MarcaEntry.COLUMNA_DESCRIPCION);
            int columnaEditableIndex = cursor.getColumnIndex(MarcaEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                String descripcionActual = cursor.getString(columnaDescripcionIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                Marca marcaActual= new Marca(IDActual,nombreActual, descripcionActual,editableActual);
                marcas.add(marcaActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
        }
        return marcas;
    }

    public ArrayList<Object> getMarcasPorNombre(String buscarSTR) {
    ArrayList<Object> marcas = new ArrayList<>();

    String[] columnas = {
            "id",
            MarcaEntry.COLUMNA_NOMBRE,
            MarcaEntry.COLUMNA_DESCRIPCION,
            MarcaEntry.COLUMNA_EDITABLE
    };

        String where = " UPPER(" + MarcaEntry.COLUMNA_NOMBRE  + ") LIKE ?";
        String [] argumentos= {"%" + buscarSTR.toUpperCase() + "%"};

        String orderby = MarcaEntry.COLUMNA_NOMBRE + " ASC";

        Cursor cursor = db.query(MarcaEntry.NOMBRE_TABLA,
            columnas,
            where,
            argumentos,
            null,
            null,
            orderby);

    try {
        int columnaIDIndex = cursor.getColumnIndex("id");
        int columnaNombreIndex = cursor.getColumnIndex(MarcaEntry.COLUMNA_NOMBRE);
        int columnaDescripcionIndex = cursor.getColumnIndex(MarcaEntry.COLUMNA_DESCRIPCION);
        int columnaEditableIndex = cursor.getColumnIndex(MarcaEntry.COLUMNA_EDITABLE);

        //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
        while (cursor.moveToNext()){
            int IDActual = cursor.getInt(columnaIDIndex);
            String nombreActual = cursor.getString(columnaNombreIndex);
            String descripcionActual = cursor.getString(columnaDescripcionIndex);
            int editableActual = cursor.getInt(columnaEditableIndex);

            Marca marcaActual= new Marca(IDActual,nombreActual, descripcionActual,editableActual);
            marcas.add(marcaActual);
        }
    } finally {
        //cierro cursor
        cursor.close();
    }
    return marcas;
    }

    public Integer InsertarMarca(Marca nuevaMarca) {

        ContentValues values = new ContentValues();

        values.put(MarcaEntry.COLUMNA_NOMBRE,nuevaMarca.getMyNOMBRE());
        values.put(MarcaEntry.COLUMNA_DESCRIPCION,nuevaMarca.getMyDESCRIPCION());
        values.put(MarcaEntry.COLUMNA_EDITABLE,1);

        long NuevaIDFilaLNG = db.insert(MarcaEntry.NOMBRE_TABLA, null,values);
        Integer NuevaIDFila = Long.valueOf(NuevaIDFilaLNG).intValue();
        return NuevaIDFila;
    }

    public void EliminarMarca(Integer marcaId) {

        String whereClause = "id" + "=?";
        String whereArgs[] = {marcaId.toString()};

        db.delete(MarcaEntry.NOMBRE_TABLA, whereClause,whereArgs);

    }

    public void UpdateMarca(Marca marcaActual) {

        ContentValues values = new ContentValues();

        values.put(MarcaEntry.COLUMNA_NOMBRE,marcaActual.getMyNOMBRE());
        values.put(MarcaEntry.COLUMNA_DESCRIPCION,marcaActual.getMyDESCRIPCION());
        values.put(MarcaEntry.COLUMNA_EDITABLE,1);

        String whereClause = "id" + "=?";
        String whereArgs[] = {marcaActual.getMyID().toString()};
        db.update(MarcaEntry.NOMBRE_TABLA, values, whereClause, whereArgs);
    }


    public Integer MarcaEnUso(Context contexto,Integer marcaId) {
        Integer enUso = -1;
        AlimentoTablaDBAccess alimentoTablaDBA = AlimentoTablaDBAccess.getInstance(contexto);
        alimentoTablaDBA.open();
        Integer cantUsoAlimentoTabla = alimentoTablaDBA.getMarcaenUso(marcaId);
        alimentoTablaDBA.close();

        AlimentoHelper alimentoDBA = new AlimentoHelper(contexto);
        Integer cantUsoAlimento = alimentoDBA.getMarcaaenUso(marcaId);
        alimentoDBA.close();

        if ((!(cantUsoAlimentoTabla == -1)) && (!(cantUsoAlimento == -1))) {
            enUso = cantUsoAlimentoTabla + cantUsoAlimento;
        }

        return enUso;
    }
}