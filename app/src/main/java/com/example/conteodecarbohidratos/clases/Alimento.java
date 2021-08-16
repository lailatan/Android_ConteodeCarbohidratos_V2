package com.example.conteodecarbohidratos.clases;
import android.provider.BaseColumns;

import java.io.Serializable;

public class Alimento implements Serializable {
    Integer myID;
    Integer myCATEGORIA;
    String myIMAGEN;
    Integer myMARCA;
    String myNOMBRE;
    Integer myCARBSNOCUENTA;
    Integer myPORCION_UNIDAD;
    Integer myPORCION_CANTIDAD;
    Integer myPORCION_GRAMOS;
    Integer myPORCION_CARBHIDRATOS;
    Integer myTIEMPOESPERA;
    Integer myABSORCIONRAP;
    Integer myAPTOCEL;
    String myINGREDIENTES;
    String myOBSERVACIONES;
    Integer myEDITABLE;

    public Alimento(Integer myID, Integer myCATEGORIA, String myIMAGEN, Integer myMARCA, String myNOMBRE, Integer myCARBSNOCUENTA, Integer myPORCION_UNIDAD, Integer myPORCION_CANTIDAD, Integer myPORCION_GRAMOS, Integer myPORCION_CARBHIDRATOS, Integer myTIEMPOESPERA, Integer myABSORCIONRAP, Integer myAPTOCEL, String myINGREDIENTES, String myOBSERVACIONES, Integer myEDITABLE) {
        this.myID = myID;
        this.myCATEGORIA = myCATEGORIA;
        this.myIMAGEN = myIMAGEN;
        this.myMARCA = myMARCA;
        this.myNOMBRE = myNOMBRE;
        this.myCARBSNOCUENTA = myCARBSNOCUENTA;
        this.myPORCION_UNIDAD = myPORCION_UNIDAD;
        this.myPORCION_CANTIDAD = myPORCION_CANTIDAD;
        this.myPORCION_GRAMOS = myPORCION_GRAMOS;
        this.myPORCION_CARBHIDRATOS = myPORCION_CARBHIDRATOS;
        this.myTIEMPOESPERA = myTIEMPOESPERA;
        this.myABSORCIONRAP = myABSORCIONRAP;
        this.myAPTOCEL = myAPTOCEL;
        this.myINGREDIENTES = myINGREDIENTES;
        this.myOBSERVACIONES = myOBSERVACIONES;
        this.myEDITABLE = myEDITABLE;
    }

    public Integer getMyID() {
        return myID;
    }

    public void setMyID(Integer myID) {
        this.myID = myID;
    }

    public Integer getMyCATEGORIA() {
        return myCATEGORIA;
    }

    public void setMyCATEGORIA(Integer myCATEGORIA) {
        this.myCATEGORIA = myCATEGORIA;
    }

    public String getMyIMAGEN() {
        return myIMAGEN;
    }

    public void setMyIMAGEN(String myIMAGEN) {
        this.myIMAGEN = myIMAGEN;
    }

    public Integer getMyMARCA() {
        return myMARCA;
    }

    public void setMyMARCA(Integer myMARCA) {
        this.myMARCA = myMARCA;
    }

    public String getMyNOMBRE() {
        return myNOMBRE;
    }

    public void setMyNOMBRE(String myNOMBRE) {
        this.myNOMBRE = myNOMBRE;
    }

    public Integer getMyPORCION_UNIDAD() {
        return myPORCION_UNIDAD;
    }

    public void setMyPORCION_UNIDAD(Integer myPORCION_UNIDAD) {
        this.myPORCION_UNIDAD = myPORCION_UNIDAD;
    }

    public Integer getMyPORCION_CANTIDAD() {
        return myPORCION_CANTIDAD;
    }

    public void setMyPORCION_CANTIDAD(Integer myPORCION_CANTIDAD) {
        this.myPORCION_CANTIDAD = myPORCION_CANTIDAD;
    }

    public Integer getMyPORCION_GRAMOS() {
        return myPORCION_GRAMOS;
    }

    public void setMyPORCION_GRAMOS(Integer myPORCION_GRAMOS) {
        this.myPORCION_GRAMOS = myPORCION_GRAMOS;
    }

    public Integer getMyPORCION_CARBHIDRATOS() {
        return myPORCION_CARBHIDRATOS;
    }

    public void setMyPORCION_CARBHIDRATOS(Integer myPORCION_CARBHIDRATOS) {
        this.myPORCION_CARBHIDRATOS = myPORCION_CARBHIDRATOS;
    }

    public Integer getMyTIEMPOESPERA() {
        return myTIEMPOESPERA;
    }

    public void setMyTIEMPOESPERA(Integer myTIEMPOESPERA) {
        this.myTIEMPOESPERA = myTIEMPOESPERA;
    }

    public Integer getMyABSORCIONRAP() {
        return myABSORCIONRAP;
    }

    public void setMyABSORCIONRAP(Integer myABSORCIONRAP) {
        this.myABSORCIONRAP = myABSORCIONRAP;
    }

    public Integer getMyAPTOCEL() {
        return myAPTOCEL;
    }

    public void setMyAPTOCEL(Integer myAPTOCEL) {
        this.myAPTOCEL = myAPTOCEL;
    }

    public String getMyINGREDIENTES() {
        return myINGREDIENTES;
    }

    public void setMyINGREDIENTES(String myINGREDIENTES) {
        this.myINGREDIENTES = myINGREDIENTES;
    }

    public String getMyOBSERVACIONES() {
        return myOBSERVACIONES;
    }

    public void setMyOBSERVACIONES(String myOBSERVACIONES) {
        this.myOBSERVACIONES = myOBSERVACIONES;
    }

    public Integer getMyEDITABLE() {
        return myEDITABLE;
    }

    public void setMyEDITABLE(Integer myEDITABLE) {
        this.myEDITABLE = myEDITABLE;
    }

    public Integer getMyCARBSNOCUENTA() {
        return myCARBSNOCUENTA;
    }

    public void setMyCARBSNOCUENTA(Integer myCARBSNOCUENTA) {
        this.myCARBSNOCUENTA = myCARBSNOCUENTA;
    }
}

