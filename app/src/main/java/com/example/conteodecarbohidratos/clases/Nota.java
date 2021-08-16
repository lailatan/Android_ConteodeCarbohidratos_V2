package com.example.conteodecarbohidratos.clases;

import java.io.Serializable;

public class Nota implements Serializable {
    Integer myID;
    String myTITULO;
    String myDESCRIPCION;
    String myFECHA;
    Integer myEDITABLE;

    public Nota(Integer myID, String myTITULO, String myDESCRIPCION, String myFECHA, Integer myEDITABLE) {
        this.myID = myID;
        this.myTITULO = myTITULO;
        this.myDESCRIPCION = myDESCRIPCION;
        this.myFECHA = myFECHA;
        this.myEDITABLE = myEDITABLE;
    }

    public Integer getMyID() {
        return myID;
    }

    public void setMyID(Integer myID) {
        this.myID = myID;
    }

    public String getMyTITULO() {
        return myTITULO;
    }

    public void setMyTITULO(String myTITULO) {
        this.myTITULO = myTITULO;
    }

    public String getMyDESCRIPCION() {
        return myDESCRIPCION;
    }

    public void setMyDESCRIPCION(String myDESCRIPCION) {
        this.myDESCRIPCION = myDESCRIPCION;
    }

    public String getMyFECHA() {
        return myFECHA;
    }

    public void setMyFECHA(String myFECHA) {
        this.myFECHA = myFECHA;
    }

    public Integer getMyEDITABLE() {
        return myEDITABLE;
    }

    public void setMyEDITABLE(Integer myEDITABLE) {
        this.myEDITABLE = myEDITABLE;
    }
}
