package com.example.conteodecarbohidratos.clases;

import java.io.Serializable;

public class Unidad implements Serializable {
    Integer myID;
    String myNOMBRE;
    String myDESCRIPCION;
    Integer myEDITABLE;

    public Unidad(Integer myID, String myNOMBRE, String myDESCRIPCION, Integer myEDITABLE) {
        this.myID = myID;
        this.myNOMBRE = myNOMBRE;
        this.myDESCRIPCION = myDESCRIPCION;
        this.myEDITABLE = myEDITABLE;
    }

    public Integer getMyID() {
        return myID;
    }

    public void setMyID(Integer myID) {
        this.myID = myID;
    }

    public String getMyNOMBRE() {
        return myNOMBRE;
    }

    public void setMyNOMBRE(String myNOMBRE) {
        this.myNOMBRE = myNOMBRE;
    }

    public String getMyDESCRIPCION() {
        return myDESCRIPCION;
    }

    public void setMyDESCRIPCION(String myDESCRIPCION) {
        this.myDESCRIPCION = myDESCRIPCION;
    }

    public Integer getMyEDITABLE() {
        return myEDITABLE;
    }

    public void setMyEDITABLE(Integer myEDITABLE) {
        this.myEDITABLE = myEDITABLE;
    }
}