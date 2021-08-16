package com.example.conteodecarbohidratos.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.conteodecarbohidratos.clases.Alimento;
import com.example.conteodecarbohidratos.clases.AlimentoEncabezado;
import com.example.conteodecarbohidratos.clases.Categoria;

import java.util.ArrayList;

public class AlimentoTablaDBAccess {
    private static final String DB_NAME = "alimentos_tabla.db";
    private static final Integer DB_VERSION = 1;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static AlimentoTablaDBAccess instance;
    Cursor c = null;
    private static Boolean dbDeApuntes;
    private static Context contexto;

    private AlimentoTablaDBAccess (Context context){
        this.openHelper = new DatabaseCSVOpenHelper(context,DB_NAME,DB_VERSION);
    }

    public static AlimentoTablaDBAccess getInstance(Context context){
        if (instance==null) {
            instance = new AlimentoTablaDBAccess(context);
            contexto=context;
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

    public ArrayList<Alimento> getAlimentosPorNombreYCategoria(String buscarSTR, Integer categoriaId) {
        ArrayList<Alimento> alimentos = new ArrayList<>();

        String[] columnas = {
                "id",
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

        if (categoriaId==0){
            where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE  + ") LIKE ?";
            argumentos = new String[1];
            argumentos[0]= "%" + buscarSTR.toUpperCase() + "%";
        } else {
            where = " UPPER(" + AlimentoContrato.AlimentoEntry.COLUMNA_NOMBRE  + ") LIKE ? AND " + AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA  + " =?";
            argumentos = new String[2];
            argumentos[0]= "%" + buscarSTR.toUpperCase() + "%";
            argumentos[1]=  categoriaId.toString();
        }

        String orderby = AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " ASC";
        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSTABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                orderby);

        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
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
        }

        return alimentos;
    }


    public ArrayList<Object> getAlimentosPorNombreYCategoriaConHeader(String buscarSTR, Integer categoriaId) {
        Integer catogoriaHeader=0;
        ArrayList<Object> alimentos = new ArrayList<>();

        String[] columnas = {
                "id",
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
        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSTABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                orderby);

        try {
            int columnaIDIndex = cursor.getColumnIndex("id");
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
        }

        return alimentos;
    }

    public Integer getCategoriaenUso(Integer categoriaId){
        Integer enUso=-1;

        String[] columnas = {"count(" + "id" + ")"};

        String where = AlimentoContrato.AlimentoEntry.COLUMNA_CATEGORIA + " =?";
        String[] argumentos = {categoriaId.toString()};

        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSTABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);

        try {
            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()) { enUso = cursor.getInt(0); }
        } finally {cursor.close();}
        return enUso;
    }

    public Integer getMarcaenUso(Integer marcaId){
        Integer enUso=-1;

        String[] columnas = {"count(" + "id" + ")"};

        String where = AlimentoContrato.AlimentoEntry.COLUMNA_MARCA + " =?";
        String[] argumentos = {marcaId.toString()};

        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSTABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);

        try {
            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()) { enUso = cursor.getInt(0); }
        } finally {cursor.close();}
        return enUso;
    }

    public Integer getUnidadenUso(Integer unidadId){
        Integer enUso=-1;

        String[] columnas = {"count(" + "id" + ")"};

        String where = AlimentoContrato.AlimentoEntry.COLUMNA_PORCION_UNIDAD + " =?";
        String[] argumentos = {unidadId.toString()};

        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSTABLA,
                columnas,
                where,
                argumentos,
                null,
                null,
                null);

        try {
            //itero el por el indice hata que Move next sea false que es cuando llegue al ultimo
            while (cursor.moveToNext()) { enUso = cursor.getInt(0); }
        } finally {cursor.close();}
        return enUso;
    }

    public Alimento getAlimentosPorId(Integer alimentoId) {
        Alimento alimentoActual=null;

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

        Cursor cursor = db.query(AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSTABLA,
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
        }
        return alimentoActual;
    }

    public ArrayList<Object> getTodosAlimentosBusquedaConHeader(Context contexto,String buscarSTR, Integer categoriaId, Integer marcaId) {
        Integer catogoriaHeader=0;
        ArrayList<Object> alimentos = new ArrayList<>();

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
                " from " + AlimentoContrato.AlimentoEntry.NOMBRE_TABLA_ALIMENTOSTABLA + " where " + sql_where + orderby;

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