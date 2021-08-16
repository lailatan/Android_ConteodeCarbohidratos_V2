package com.example.conteodecarbohidratos.db;

import android.provider.BaseColumns;

public class NotaContrato {

    private NotaContrato(){}


    public static final class NotaEntry implements BaseColumns {

        /**
         * Nombre de la tabla
         */
        public final static String NOMBRE_TABLA = "notas";

        //public final static String _ID = BaseColumns._ID;
        public final static String _ID = "id";
        public final static String COLUMNA_TITULO = "titulo";
        public final static String COLUMNA_DESCRIPCION = "descripcion";
        public final static String COLUMNA_FECHA = "fecha";
        public final static String COLUMNA_EDITABLE = "editable";
    }
}
