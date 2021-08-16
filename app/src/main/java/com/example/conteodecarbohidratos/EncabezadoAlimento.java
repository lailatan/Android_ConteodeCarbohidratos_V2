package com.example.conteodecarbohidratos;

public class EncabezadoAlimento {
    private String encabezado;
    private Integer iconoEncabezado;


    public EncabezadoAlimento(String encabezadoEntrada, Integer iconoEntrada) {
        this.encabezado = encabezadoEntrada;
        this.iconoEncabezado = iconoEntrada;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezadoEntrada) {
        this.encabezado = encabezadoEntrada;
    }

    public Integer getIconoEncabezado() {
        return iconoEncabezado;
    }

    public void setIconoEncabezado( Integer iconoEntrada) {
        this.iconoEncabezado = iconoEntrada;
    }

}