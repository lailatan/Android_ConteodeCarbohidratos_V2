package com.example.conteodecarbohidratos.db;

import android.provider.BaseColumns;

public class MarcaContrato {

    private MarcaContrato(){}


    public static final class MarcaEntry implements BaseColumns {

        /**
         * Nombre de la tabla
         */
        public final static String NOMBRE_TABLA = "marcas";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMNA_NOMBRE = "nombre";
        public final static String COLUMNA_DESCRIPCION = "descripcion";
        public final static String COLUMNA_EDITABLE = "editable";
    }
}
