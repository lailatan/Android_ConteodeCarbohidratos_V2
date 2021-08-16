package com.example.conteodecarbohidratos.db;

import android.provider.BaseColumns;

public class CategoriaContrato {

    private CategoriaContrato(){}

    public static final class CategoriaEntry implements BaseColumns {
        /**
         * Nombre de la tabla
         */
        public final static String NOMBRE_TABLA = "categorias";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMNA_NOMBRE = "nombre";
        public final static String COLUMNA_DESCRIPCION = "descripcion";
        public final static String COLUMNA_EDITABLE = "editable";
    }
}
