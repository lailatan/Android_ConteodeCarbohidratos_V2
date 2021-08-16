package com.example.conteodecarbohidratos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.AlimentoEncabezado;
import com.example.conteodecarbohidratos.clases.Categoria;
import com.example.conteodecarbohidratos.clases.Nota;

import java.util.ArrayList;


public class NotaHelper extends SQLiteOpenHelper {
    private static String NOMBRE_DB = "notas.db";

    private static final int VERSION_DB = 1;

    public NotaHelper(Context context){

        super(context, NOMBRE_DB, null, VERSION_DB);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //creo DB usando los strings declarado en el contracto

        String SQL_CREAR_TABLA_NOTAS = "CREATE TABLE " + NotaContrato.NotaEntry.NOMBRE_TABLA + "("
                + NotaContrato.NotaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NotaContrato.NotaEntry.COLUMNA_TITULO + "	TEXT NOT NULL,"
                + NotaContrato.NotaEntry.COLUMNA_DESCRIPCION + " TEXT NOT NULL,"
                + NotaContrato.NotaEntry.COLUMNA_FECHA + " TEXT NOT NULL,"
                + NotaContrato.NotaEntry.COLUMNA_EDITABLE + " INTEGER NOT NULL DEFAULT 1"
                + ");";
        db.execSQL(SQL_CREAR_TABLA_NOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<Nota> getNotasPorTitulo(String buscarSTR) {
        ArrayList<Nota> notas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] columnas = {
                NotaContrato.NotaEntry._ID,
                NotaContrato.NotaEntry.COLUMNA_TITULO,
                NotaContrato.NotaEntry.COLUMNA_DESCRIPCION,
                NotaContrato.NotaEntry.COLUMNA_FECHA,
                NotaContrato.NotaEntry.COLUMNA_EDITABLE
        };

        String where = " UPPER(" + NotaContrato.NotaEntry.COLUMNA_TITULO  + ") LIKE ?";
        String []argumentos = {"%" + buscarSTR.toUpperCase() + "%"};

        String orderby = NotaContrato.NotaEntry._ID + " DESC";

        Cursor cursor = db.query(NotaContrato.NotaEntry.NOMBRE_TABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                orderby);
        try {
            int columnaIDIndex = cursor.getColumnIndex(NotaContrato.NotaEntry._ID);
            int columnaTituloIndex = cursor.getColumnIndex(NotaContrato.NotaEntry.COLUMNA_TITULO);
            int columnaDescripcionIndex = cursor.getColumnIndex(NotaContrato.NotaEntry.COLUMNA_DESCRIPCION);
            int columnaFechaIndex = cursor.getColumnIndex(NotaContrato.NotaEntry.COLUMNA_FECHA);
            int columnaEditableIndex = cursor.getColumnIndex(NotaContrato.NotaEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                String tituloActual = cursor.getString(columnaTituloIndex);
                String descripcionActual = cursor.getString(columnaDescripcionIndex);
                String fechaActual = cursor.getString(columnaFechaIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                Nota notaActual= new Nota(IDActual,tituloActual,descripcionActual,
                        fechaActual,editableActual);
                notas.add(notaActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
            db.close();
        }
        return notas;
    }

    public Integer InsertarNota(Nota nuevoNota) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NotaContrato.NotaEntry.COLUMNA_TITULO,nuevoNota.getMyTITULO());
        values.put(NotaContrato.NotaEntry.COLUMNA_DESCRIPCION,nuevoNota.getMyDESCRIPCION());
        values.put(NotaContrato.NotaEntry.COLUMNA_FECHA,nuevoNota.getMyFECHA());
        values.put(NotaContrato.NotaEntry.COLUMNA_EDITABLE,1);

        long NuevaIDFilaLNG = db.insert(NotaContrato.NotaEntry.NOMBRE_TABLA, null,values);
        Integer NuevaIDFila = Long.valueOf(NuevaIDFilaLNG).intValue();
        db.close();
        return NuevaIDFila;
    }

    public void EliminarNota(Integer notaId) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = NotaContrato.NotaEntry._ID + "=?";
        String whereArgs[] = {notaId.toString()};

        db.delete(NotaContrato.NotaEntry.NOMBRE_TABLA, whereClause,whereArgs);
        db.close();

    }

    public void updateNota(Nota notaActual) {
        //AlimentoPersHelper mDbHelper = new AlimentoPersHelper(contexto);
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NotaContrato.NotaEntry.COLUMNA_TITULO,notaActual.getMyTITULO());
        values.put(NotaContrato.NotaEntry.COLUMNA_DESCRIPCION,notaActual.getMyDESCRIPCION());
        values.put(NotaContrato.NotaEntry.COLUMNA_FECHA,notaActual.getMyFECHA());

        String whereClause = NotaContrato.NotaEntry._ID + "=?";
        String whereArgs[] = {notaActual.getMyID().toString()};
        db.update(NotaContrato.NotaEntry.NOMBRE_TABLA, values, whereClause, whereArgs);
        db.close();
    }

}