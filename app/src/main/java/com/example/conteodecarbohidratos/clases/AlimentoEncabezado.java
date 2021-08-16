package com.example.conteodecarbohidratos.clases;

public class AlimentoEncabezado {
    private String encabezado;
    private String iconoEncabezado;


    public AlimentoEncabezado(String encabezadoEntrada, String iconoEntrada) {
        this.encabezado = encabezadoEntrada;
        this.iconoEncabezado = iconoEntrada;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezadoEntrada) {
        this.encabezado = encabezadoEntrada;
    }

    public String getIconoEncabezado() {
        return iconoEncabezado;
    }

    public void setIconoEncabezado( String iconoEntrada) {
        this.iconoEncabezado = iconoEntrada;
    }

}
