package com.example.conteodecarbohidratos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.AlimentoEncabezado;
import com.example.conteodecarbohidratos.clases.Categoria;

import java.util.ArrayList;

public class AlimentoHelper extends SQLiteOpenHelper {
    private static String NOMBRE_DB = "alimentos_personales.db";

    private static final int VERSION_DB = 1;

    public AlimentoHelper(Context context){

        super(context, NOMBRE_DB, null, VERSION_DB);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //creo DB usando los strings declarado en el contracto

        String SQL_CREAR_TABLA_ALIMENTOS = "CREATE TABLE " + AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES + "("
                + AlimentoContrato.AlimentoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " INTEGER NOT NULL, "
                + AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN + "	TEXT,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_MARCA + " INTEGER NOT NULL,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE + " TEXT NOT NULL,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA + " INTEGER NOT NULL DEFAULT 0,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD + " INTEGER NOT NULL DEFAULT 0,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD + " INTEGER NOT NULL DEFAULT 0,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS + " INTEGER NOT NULL DEFAULT 0,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS + " INTEGER NOT NULL DEFAULT 0,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO + " INTEGER,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP + " INTEGER DEFAULT 2,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL + " INTEGER DEFAULT 2,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES + " TEXT,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES + " TEXT,"
                + AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE + " INTEGER NOT NULL DEFAULT 1"
                + ");";
        db.execSQL(SQL_CREAR_TABLA_ALIMENTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Alimento getAlimentosPorId(Integer alimentoId) {
        Alimento alimentoActual=null;
        SQLiteDatabase db = getReadableDatabase();

        String[] columnas = {
                AlimentoContrato.AlimentoEntry._ID,
                AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA,
                AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN,
                AlimentoContrato.AlimentoEntry.COLUMNA_MARCA,
                AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE,
                AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS,
                AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO,
                AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP,
                AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL,
                AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES,
                AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES,
                AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE
        };

        String where = AlimentoContrato.AlimentoEntry._ID  + " =?";
        String[] argumentos = {alimentoId.toString()};

        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);
        try {
            int columnaIDIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry._ID);
            int columnaCategoriaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA);
            int columnaImagenIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN);
            int columnaMarcaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_MARCA);
            int columnaNombreIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE);
            int columnaCarbsNoCuentaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA);
            int columnaPorcionUnidadIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD);
            int columnaPorcionCantIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD);
            int columnaPorcionGrIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS);
            int columnaCarboIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS);
            int columnaTiempoIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO);
            int columnaAbsorcionRapIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP);
            int columnaAptoCelIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL);
            int columnaIngredientesIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES);
            int columnaObservacionesIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES);
            int columnaEditableIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                int categoriaActual = cursor.getInt(columnaCategoriaIndex);
                String imagenActual = cursor.getString(columnaImagenIndex);
                int marcaActual = cursor.getInt(columnaMarcaIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                int carbsNoCuenta = cursor.getInt(columnaCarbsNoCuentaIndex);
                int porcionUnidadActual = cursor.getInt(columnaPorcionUnidadIndex);
                int porcionCantActual = cursor.getInt(columnaPorcionCantIndex);
                int porcionGrActual = cursor.getInt(columnaPorcionGrIndex);
                int carboActual = cursor.getInt(columnaCarboIndex);
                Integer tiempoActual = cursor.getInt(columnaTiempoIndex);
                int absorcionActual = cursor.getInt(columnaAbsorcionRapIndex);
                int aptocelActual = cursor.getInt(columnaAptoCelIndex);
                String ingredientesActual = cursor.getString(columnaIngredientesIndex);
                String observacionesActual = cursor.getString(columnaObservacionesIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                alimentoActual= new Alimento(IDActual,categoriaActual,imagenActual,
                        marcaActual,nombreActual,carbsNoCuenta,porcionUnidadActual,porcionCantActual,porcionGrActual,carboActual,tiempoActual,absorcionActual,
                        aptocelActual,ingredientesActual,observacionesActual,editableActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
            db.close();
        }
        return alimentoActual;
    }


    public ArrayList<Alimento> getAlimentosPorNombreYCategoria(String buscarSTR, Integer categoriaId) {
        ArrayList<Alimento> alimentos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] columnas = {
                AlimentoContrato.AlimentoEntry._ID,
                AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA,
                AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN,
                AlimentoContrato.AlimentoEntry.COLUMNA_MARCA,
                AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE,
                AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS,
                AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO,
                AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP,
                AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL,
                AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES,
                AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES,
                AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE
        };

        String where="";
        String[] argumentos;
        buscarSTR.toUpperCase();
        if (categoriaId==0){
            where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE  + ") LIKE ?";
            argumentos = new String[1];
            argumentos[0]= "%" + buscarSTR.toUpperCase() + "%";
        } else {
            where =  " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE  + ") LIKE ? AND " + AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA  + " =?";
            argumentos = new String[2];
            argumentos[0]= "%" + buscarSTR.toUpperCase() + "%";
            argumentos[1]=  categoriaId.toString();
        }

        String orderby = AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " ASC";

        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES,
                columnas,
                where,
                argumentos,
                null,
                null,
                orderby);
        try {
            int columnaIDIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry._ID);
            int columnaCategoriaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA);
            int columnaImagenIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN);
            int columnaMarcaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_MARCA);
            int columnaNombreIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE);
            int columnaCarbsNoCuentaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA);
            int columnaPorcionUnidadIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD);
            int columnaPorcionCantIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD);
            int columnaPorcionGrIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS);
            int columnaCarboIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS);
            int columnaTiempoIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO);
            int columnaAbsorcionRapIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP);
            int columnaAptoCelIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL);
            int columnaIngredientesIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES);
            int columnaObservacionesIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES);
            int columnaEditableIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int IDActual = cursor.getInt(columnaIDIndex);
                int categoriaActual = cursor.getInt(columnaCategoriaIndex);
                String imagenActual = cursor.getString(columnaImagenIndex);
                int marcaActual = cursor.getInt(columnaMarcaIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                int carbsNoCuenta = cursor.getInt(columnaCarbsNoCuentaIndex);
                int porcionUnidadActual = cursor.getInt(columnaPorcionUnidadIndex);
                int porcionCantActual = cursor.getInt(columnaPorcionCantIndex);
                int porcionGrActual = cursor.getInt(columnaPorcionGrIndex);
                int carboActual = cursor.getInt(columnaCarboIndex);
                Integer tiempoActual = cursor.getInt(columnaTiempoIndex);
                int absorcionActual = cursor.getInt(columnaAbsorcionRapIndex);
                int aptocelActual = cursor.getInt(columnaAptoCelIndex);
                String ingredientesActual = cursor.getString(columnaIngredientesIndex);
                String observacionesActual = cursor.getString(columnaObservacionesIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                Alimento alimentoActual= new Alimento(IDActual,categoriaActual,imagenActual,
                        marcaActual,nombreActual,carbsNoCuenta,porcionUnidadActual,porcionCantActual,porcionGrActual,carboActual,tiempoActual,absorcionActual,
                        aptocelActual,ingredientesActual,observacionesActual,editableActual);
                alimentos.add(alimentoActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
            db.close();
        }
        return alimentos;
    }

    public Integer InsertarAlimento(Alimento nuevoAlimento) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA,nuevoAlimento.getMyCATEGORIA());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN,nuevoAlimento.getMyIMAGEN());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_MARCA,nuevoAlimento.getMyMARCA());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE,nuevoAlimento.getMyNOMBRE());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA,nuevoAlimento.getMyCARBSNOCUENTA());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD,nuevoAlimento.getMyPORCION_UNIDAD());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD,nuevoAlimento.getMyPORCION_CANTIDAD());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS,nuevoAlimento.getMyPORCION_GRAMOS());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS,nuevoAlimento.getMyPORCION_CARBHIDRATOS());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO,nuevoAlimento.getMyTIEMPOESPERA());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP,nuevoAlimento.getMyABSORCIONRAP());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL,nuevoAlimento.getMyAPTOCEL());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES,nuevoAlimento.getMyINGREDIENTES());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES,nuevoAlimento.getMyOBSERVACIONES());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE,1);

        long NuevaIDFilaLNG = db.insert(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES, null,values);
        Integer NuevaIDFila = Long.valueOf(NuevaIDFilaLNG).intValue();
        db.close();
        return NuevaIDFila;
    }

    public void EliminarAlimento(Integer alimentoId) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = AlimentoContrato.AlimentoEntry._ID + "=?";
        String whereArgs[] = {alimentoId.toString()};

        long NuevaIDFila = db.delete(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES, whereClause,whereArgs);
        db.close();
    }

    public void updateAlimento(Alimento alimentoActual) {
        //AlimentoPersHelper mDbHelper = new AlimentoPersHelper(contexto);
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA,alimentoActual.getMyCATEGORIA());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN,alimentoActual.getMyIMAGEN());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_MARCA,alimentoActual.getMyMARCA());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE,alimentoActual.getMyNOMBRE());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA,alimentoActual.getMyCARBSNOCUENTA());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD,alimentoActual.getMyPORCION_UNIDAD());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD,alimentoActual.getMyPORCION_CANTIDAD());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS,alimentoActual.getMyPORCION_GRAMOS());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS,alimentoActual.getMyPORCION_CARBHIDRATOS());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO,alimentoActual.getMyTIEMPOESPERA());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP,alimentoActual.getMyABSORCIONRAP());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL,alimentoActual.getMyAPTOCEL());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES,alimentoActual.getMyINGREDIENTES());
        values.put(AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES,alimentoActual.getMyOBSERVACIONES());

        String whereClause = AlimentoContrato.AlimentoEntry._ID + "=?";
        String whereArgs[] = {alimentoActual.getMyID().toString()};
        db.update(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES, values, whereClause, whereArgs);
        db.close();
    }

    public ArrayList<Object> getAlimentosPorNombreCategoriaMarcaConHeader(Context contexto,String buscarSTR, Integer categoriaId, Integer marcaId) {
        Integer catogoriaHeader=0;
        ArrayList<Object> alimentos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String[] columnas = {
                AlimentoContrato.AlimentoEntry._ID,
                AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA,
                AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN,
                AlimentoContrato.AlimentoEntry.COLUMNA_MARCA,
                AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE,
                AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS,
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS,
                AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO,
                AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP,
                AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL,
                AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES,
                AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES,
                AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE
        };

        String where="";
        String[] argumentos;

        if (categoriaId==0 && marcaId==0) {
            where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE + ") LIKE ?";
            argumentos = new String[1];
            argumentos[0] = "%" + buscarSTR.toUpperCase() + "%";
        } else if (categoriaId==0) {
            where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE + ") LIKE ? AND " +
                    AlimentoContrato.AlimentoEntry.COLUMNA_MARCA + " =? ";
            argumentos = new String[2];
            argumentos[0] = "%" + buscarSTR.toUpperCase() + "%";
            argumentos[1] = marcaId.toString();
        } else if (marcaId==0) {
            where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE + ") LIKE ? AND " +
                    AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " =? ";
            argumentos = new String[2];
            argumentos[0] = "%" + buscarSTR.toUpperCase() + "%";
            argumentos[1] = categoriaId.toString();
        } else {
            where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE + ") LIKE ? AND " +
                    AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " =? AND " +
                    AlimentoContrato.AlimentoEntry.COLUMNA_MARCA + " =? ";
            argumentos = new String[3];
            argumentos[0] = "%" + buscarSTR.toUpperCase() + "%";
            argumentos[1] = categoriaId.toString();
            argumentos[2] = marcaId.toString();
        }

        String orderby = AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " ASC";
        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES,
                columnas,
                where,
                argumentos,
                null,
                null,
                orderby);

        try {
            int columnaIDIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry._ID);
            int columnaCategoriaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA);
            int columnaImagenIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN);
            int columnaMarcaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_MARCA);
            int columnaNombreIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE);
            int columnaCarbsNoCuentaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA);
            int columnaPorcionUnidadIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD);
            int columnaPorcionCantIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD);
            int columnaPorcionGrIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS);
            int columnaCarboIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS);
            int columnaTiempoIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO);
            int columnaAbsorcionRapIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP);
            int columnaAptoCelIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL);
            int columnaIngredientesIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES);
            int columnaObservacionesIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES);
            int columnaEditableIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int categoriaActual = cursor.getInt(columnaCategoriaIndex);
                int IDActual = cursor.getInt(columnaIDIndex);
                String imagenActual = cursor.getString(columnaImagenIndex);
                int marcaActual = cursor.getInt(columnaMarcaIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                int carbsNoCuenta = cursor.getInt(columnaCarbsNoCuentaIndex);
                int porcionUnidadActual = cursor.getInt(columnaPorcionUnidadIndex);
                int porcionCantActual = cursor.getInt(columnaPorcionCantIndex);
                int porcionGrActual = cursor.getInt(columnaPorcionGrIndex);
                int carboActual = cursor.getInt(columnaCarboIndex);
                Integer tiempoActual = cursor.getInt(columnaTiempoIndex);
                int absorcionActual = cursor.getInt(columnaAbsorcionRapIndex);
                int aptocelActual = cursor.getInt(columnaAptoCelIndex);
                String ingredientesActual = cursor.getString(columnaIngredientesIndex);
                String observacionesActual = cursor.getString(columnaObservacionesIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                if (!(catogoriaHeader==categoriaActual)) {
                    String categoriaSTR="";
                    CategoriaDBAccess categoriaDBA = CategoriaDBAccess.getInstance(contexto);
                    categoriaDBA.open();
                    Categoria categoriaActualObj = categoriaDBA.getCategoriaPorID(categoriaActual);
                    categoriaDBA.close();
                    if (!(categoriaActualObj==null)) categoriaSTR = categoriaActualObj.getMyNOMBRE();
                    AlimentoEncabezado headerActual = new AlimentoEncabezado(categoriaSTR,"ic_alimentos2");
                    alimentos.add(headerActual);
                    catogoriaHeader=categoriaActual;
                }

                Alimento alimentoActual= new Alimento(IDActual,categoriaActual,imagenActual,
                        marcaActual,nombreActual,carbsNoCuenta,porcionUnidadActual,porcionCantActual,porcionGrActual,carboActual,tiempoActual,absorcionActual,
                        aptocelActual,ingredientesActual,observacionesActual,editableActual);
                alimentos.add(alimentoActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
            db.close();
        }

        return alimentos;
    }

    public Integer getCategoriaenUso(Integer categoriaId){
        Integer enUso=-1;

        SQLiteDatabase db = getReadableDatabase();

        String[] columnas = {"count(" + AlimentoContrato.AlimentoEntry._ID + ")"};

        String where = AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " =?";
        String[] argumentos = {categoriaId.toString()};

        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);

        try {
            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
                while (cursor.moveToNext()) {
                    enUso = cursor.getInt(0);
                }
        } finally {
            //cierro cursor
            cursor.close();
            db.close();
        }

        return enUso;
    }

    public Integer getMarcaaenUso(Integer marcaId){
        Integer enUso=-1;

        SQLiteDatabase db = getReadableDatabase();

        String[] columnas = {"count(" + AlimentoContrato.AlimentoEntry._ID + ")"};

        String where = AlimentoContrato.AlimentoEntry.COLUMNA_MARCA + " =?";
        String[] argumentos = {marcaId.toString()};

        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);

        try {
            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()) {
                enUso = cursor.getInt(0);
            }
        } finally {
            //cierro cursor
            cursor.close();
            db.close();
        }

        return enUso;
    }

    public Integer getUnidadenUso(Integer unidadId){
        Integer enUso=-1;

        SQLiteDatabase db = getReadableDatabase();

        String[] columnas = {"count(" + AlimentoContrato.AlimentoEntry._ID + ")"};

        String where = AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD + " =?";
        String[] argumentos = {unidadId.toString()};

        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);

        try {
            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()) {
                enUso = cursor.getInt(0);
            }
        } finally {
            //cierro cursor
            cursor.close();
            db.close();
        }

        return enUso;
    }


    public ArrayList<Object> getTodosAlimentosBusquedaConHeader(Context contexto,String buscarSTR, Integer categoriaId, Integer marcaId) {
        Integer catogoriaHeader=0;
        ArrayList<Object> alimentos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String sql_where="";

        if (categoriaId==0 && marcaId==0) {
            sql_where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE + ") LIKE " + "'%" + buscarSTR.toUpperCase() + "%'";
        } else if (categoriaId==0) {
            sql_where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE + ") LIKE " + "'%" + buscarSTR.toUpperCase() + "%'" +" AND " +
                    AlimentoContrato.AlimentoEntry.COLUMNA_MARCA + " ="  + marcaId.toString();
        } else if (marcaId==0) {
            sql_where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE + ") LIKE " + "'%" + buscarSTR.toUpperCase() + "%'" +" AND " +
                    AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " =" + categoriaId.toString();
        } else {
            sql_where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE + ") LIKE " + "'%" + buscarSTR.toUpperCase() + "%'" +" AND " +
                    AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " =" + categoriaId.toString() + " AND " +
                    AlimentoContrato.AlimentoEntry.COLUMNA_MARCA + " ="  + marcaId.toString();
        }

        String orderby = " order by " + AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " ASC";
        //String sqlUnion= " ( Select * from " + AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES
        //        + " UNION ALL "
        //        + " Select * from " + AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSTABLA +" ) ";

        String sql= "Select " +
                AlimentoContrato.AlimentoEntry._ID + " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN + " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_MARCA + " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES +  " , " +
                AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE +
                " from " + AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSPERSONALES + " where " + sql_where + orderby;

        Cursor cursor =db.rawQuery(sql,null);

        try {
            int columnaIDIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry._ID);
            int columnaCategoriaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA);
            int columnaImagenIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_IMAGEN);
            int columnaMarcaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_MARCA);
            int columnaNombreIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE);
            int columnaCarbsNoCuentaIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_CARBS_NO_CUENTA);
            int columnaPorcionUnidadIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD);
            int columnaPorcionCantIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CANTIDAD);
            int columnaPorcionGrIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_GRAMOS);
            int columnaCarboIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_CARBHIDRATOS);
            int columnaTiempoIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_TIEMPO);
            int columnaAbsorcionRapIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_ABSORCIONRAP);
            int columnaAptoCelIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_APTOCEL);
            int columnaIngredientesIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_INGREDIENTES);
            int columnaObservacionesIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_OBSERVACIONES);
            int columnaEditableIndex = cursor.getColumnIndex(AlimentoContrato.AlimentoEntry.COLUMNA_EDITABLE);

            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()){
                int categoriaActual = cursor.getInt(columnaCategoriaIndex);
                int IDActual = cursor.getInt(columnaIDIndex);
                String imagenActual = cursor.getString(columnaImagenIndex);
                int marcaActual = cursor.getInt(columnaMarcaIndex);
                String nombreActual = cursor.getString(columnaNombreIndex);
                int carbsNoCuenta = cursor.getInt(columnaCarbsNoCuentaIndex);
                int porcionUnidadActual = cursor.getInt(columnaPorcionUnidadIndex);
                int porcionCantActual = cursor.getInt(columnaPorcionCantIndex);
                int porcionGrActual = cursor.getInt(columnaPorcionGrIndex);
                int carboActual = cursor.getInt(columnaCarboIndex);
                Integer tiempoActual = cursor.getInt(columnaTiempoIndex);
                int absorcionActual = cursor.getInt(columnaAbsorcionRapIndex);
                int aptocelActual = cursor.getInt(columnaAptoCelIndex);
                String ingredientesActual = cursor.getString(columnaIngredientesIndex);
                String observacionesActual = cursor.getString(columnaObservacionesIndex);
                int editableActual = cursor.getInt(columnaEditableIndex);

                if (!(catogoriaHeader==categoriaActual)) {
                    String categoriaSTR="";
                    CategoriaDBAccess categoriaDBA = CategoriaDBAccess.getInstance(contexto);
                    categoriaDBA.open();
                    Categoria categoriaActualObj = categoriaDBA.getCategoriaPorID(categoriaActual);
                    categoriaDBA.close();
                    if (!(categoriaActualObj==null)) categoriaSTR = categoriaActualObj.getMyNOMBRE();
                    AlimentoEncabezado headerActual = new AlimentoEncabezado(categoriaSTR,"ic_alimentos2");
                    alimentos.add(headerActual);
                    catogoriaHeader=categoriaActual;
                }

                Alimento alimentoActual= new Alimento(IDActual,categoriaActual,imagenActual,
                        marcaActual,nombreActual,carbsNoCuenta,porcionUnidadActual,porcionCantActual,porcionGrActual,carboActual,tiempoActual,absorcionActual,
                        aptocelActual,ingredientesActual,observacionesActual,editableActual);
                alimentos.add(alimentoActual);
            }
        } finally {
            //cierro cursor
            cursor.close();
            db.close();
        }

        return alimentos;
    }

}