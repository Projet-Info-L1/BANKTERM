package com.example.MODELE;

public class History {
    private String operaName;
    private String operaInfo;

    public History(String operaName, String operaInfo){
        this.operaName = operaName;
        this.operaInfo = operaInfo;
    }

    public String getOperaName(){
        return operaName;
    }
    public String getOperaInfo(){
        return operaInfo;
    }

    public void setOperaName(String operaName){
        this.operaName = operaName;
    }
    public void setOperInfo(String operaInfo){
        this.operaInfo = operaInfo;
    }

}
