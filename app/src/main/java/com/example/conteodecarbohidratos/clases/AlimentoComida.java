package com.example.conteodecarbohidratos.clases;

import java.io.Serializable;

public class AlimentoComida  implements Serializable {
    Integer myId;
    String myNombre;
    String myMarca;
    String myCategoria;
    Integer myEditable;
    Integer myCarbsNoCuenta;
    String myUnidadPorcion;
    Integer myCantidadPorcion;
    Integer myCarbohidratosPorcion;
    Integer myUsaGramosComida;
    String  myUnidadComida;
    Integer myCantidadComida;
    Integer myCarbohidratosComida;
    Integer myTiempoEspera;

    public AlimentoComida(Integer myId, String myNombre, String myMarca, String myCategoria, Integer myEditable, Integer myCarbsNoCuenta, String myUnidadPorcion, Integer myCantidadPorcion, Integer myCarbohidratosPorcion, Integer myUsaGramosComida, String myUnidadComida, Integer myCantidadComida, Integer myCarbohidratosComida, Integer myTiempoEspera) {
        this.myId = myId;
        this.myNombre = myNombre;
        this.myMarca = myMarca;
        this.myCategoria = myCategoria;
        this.myEditable = myEditable;
        this.myCarbsNoCuenta = myCarbsNoCuenta;
        this.myUnidadPorcion = myUnidadPorcion;
        this.myCantidadPorcion = myCantidadPorcion;
        this.myCarbohidratosPorcion = myCarbohidratosPorcion;
        this.myUsaGramosComida = myUsaGramosComida;
        this.myUnidadComida = myUnidadComida;
        this.myCantidadComida = myCantidadComida;
        this.myCarbohidratosComida = myCarbohidratosComida;
        this.myTiempoEspera = myTiempoEspera;
    }

    public Integer getMyId() {
        return myId;
    }

    public void setMyId(Integer myId) {
        this.myId = myId;
    }

    public String getMyNombre() {
        return myNombre;
    }

    public void setMyNombre(String myNombre) {
        this.myNombre = myNombre;
    }

    public String getMyMarca() {
        return myMarca;
    }

    public void setMyMarca(String myMarca) {
        this.myMarca = myMarca;
    }

    public String getMyCategoria() {
        return myCategoria;
    }

    public void setMyCategoria(String myCategoria) {
        this.myCategoria = myCategoria;
    }

    public String getMyUnidadPorcion() {
        return myUnidadPorcion;
    }

    public void setMyUnidadPorcion(String myUnidadPorcion) {
        this.myUnidadPorcion = myUnidadPorcion;
    }

    public Integer getMyCantidadPorcion() {
        return myCantidadPorcion;
    }

    public void setMyCantidadPorcion(Integer myCantidadPorcion) {
        this.myCantidadPorcion = myCantidadPorcion;
    }

    public Integer getMyCarbohidratosPorcion() {
        return myCarbohidratosPorcion;
    }

    public void setMyCarbohidratosPorcion(Integer myCarbohidratosPorcion) {
        this.myCarbohidratosPorcion = myCarbohidratosPorcion;
    }

    public Integer getMyUsaGramosComida() {
        return myUsaGramosComida;
    }

    public void setMyUsaGramosComida(Integer myUsaGramosComida) {
        this.myUsaGramosComida = myUsaGramosComida;
    }

    public String getMyUnidadComida() {
        return myUnidadComida;
    }

    public void setMyUnidadComida(String myUnidadComida) {
        this.myUnidadComida = myUnidadComida;
    }

    public Integer getMyCantidadComida() {
        return myCantidadComida;
    }

    public void setMyCantidadComida(Integer myCantidadComida) {
        this.myCantidadComida = myCantidadComida;
    }

    public Integer getMyCarbohidratosComida() {
        return myCarbohidratosComida;
    }

    public void setMyCarbohidratosComida(Integer myCarbohidratosComida) {
        this.myCarbohidratosComida = myCarbohidratosComida;
    }

    public Integer getMyCarbsNoCuenta() {
        return myCarbsNoCuenta;
    }

    public void setMyCarbsNoCuenta(Integer myCarbsNoCuenta) {
        this.myCarbsNoCuenta = myCarbsNoCuenta;
    }

    public Integer getMyTiempoEspera() {
        return myTiempoEspera;
    }

    public void setMyTiempoEspera(Integer myTiempoEspera) {
        this.myTiempoEspera = myTiempoEspera;
    }

    public Integer getMyEditable() {
        return myEditable;
    }

    public void setMyEditable(Integer myEditable) {
        this.myEditable = myEditable;
    }
}
