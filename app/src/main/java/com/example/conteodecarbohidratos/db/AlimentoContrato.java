package com.example.conteodecarbohidratos.db;

import android.provider.BaseColumns;

public final class AlimentoContrato {

    private AlimentoContrato() {
    }

    public static final class AlimentoEntry implements BaseColumns {

        /** Nombre de la tabla */
        public final static String NOMBRE_TABLA_ALIMENTOSTABLA = "alimentos_tabla";
        public final static String NOMBRE_TABLA_ALIMENTOSPERSONALES = "alimentos_personales";

        //public final static String _ID = BaseColumns._ID;
        public final static String _ID = "id";
        public final static String COLUMNA_CATEGORIA ="categoria";
        public final static String COLUMNA_IMAGEN = "imagen";
        public final static String COLUMNA_MARCA = "marca";
        public final static String COLUMNA_NOMBRE = "nombre";
        public final static String COLUMNA_CARBS_NO_CUENTA = "carbs_no_cuenta";
        public final static String COLUMNA_PORCION_UNIDAD = "porcion_unidades";
        public final static String COLUMNA_PORCION_CANTIDAD = "porcion_cantidad";
        public final static String COLUMNA_PORCION_GRAMOS = "porcion_gramos";
        public final static String COLUMNA_PORCION_CARBHIDRATOS = "porcion_carbohidratos";
        public final static String COLUMNA_TIEMPO = "tiempo_espera";
        public final static String COLUMNA_ABSORCIONRAP = "absorcion_rapida";
        public final static String COLUMNA_APTOCEL = "apto_celiaco";
        public final static String COLUMNA_INGREDIENTES = "ingredientes";
        public final static String COLUMNA_OBSERVACIONES = "observaciones";
        public final static String COLUMNA_EDITABLE = "editable";

/*
CREATE TABLE "alimentos" (
	"id"	INTEGER NOT NULL,
	"categoria"	INTEGER NOT NULL,
	"imagen"	TEXT,
	"marca"	INTEGER NOT NULL,
	"nombre"	TEXT NOT NULL,
	"carbs_no_cuenta"	INTEGER NOT NULL DEFAULT 0,
	"porcion"	TEXT,
	"porcion_unidades"	INTEGER NOT NULL DEFAULT 29,
	"porcion_cantidad"	INTEGER NOT NULL DEFAULT 0,
	"porcion_gramos"	INTEGER NOT NULL DEFAULT 0,
	"porcion_carbohidratos"	INTEGER NOT NULL DEFAULT 0,
	"tiempo_espera"	TEXT,
	"absorcion_rapida"	INTEGER NOT NULL DEFAULT 2,
	"apto_celiaco"	INTEGER NOT NULL DEFAULT 2,
	"ingredientes"	TEXT,
	"observaciones"	TEXT,
	"editable"	INTEGER NOT NULL,
	PRIMARY KEY("id")
))
       */

        /**
         * Valores posibles para TIPO
         */
        public static final int TIPO_NATURAL = 1;
        public static final int TIPO_FABRICADO = 2;
        public static final int TIPO_CASERO = 3;
        /**
         * Valores posibles para 	"absorcion_rapida"	INTEGER,
         */
        public static final int ABSORCION_NS = 2;
        public static final int ABSORCION_RAPIDA = 1;
        public static final int ABSORCION_LENTA = 0;

        /**
         * Valores posibles para "apto_celiaco"	INTEGER,
         */
        public static final int APTO_CELIACO_NS = 2;
        public static final int APTO_CELIACO_SI = 1;
        public static final int APTO_CELIACO_NO = 0;


        public static final String NO_SE_CONTABILIZA="No aporta Carbohidratos";

    }
}
