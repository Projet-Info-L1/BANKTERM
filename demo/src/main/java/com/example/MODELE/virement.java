package com.javafx.Models;

public class Virement {
    private String senderName;
    private int senderNumCompte;
    private double sendSolde;
    private String recieverName;
    private int recieverNumCompte;
    private double recievedSolde;

    public Virement(int senderNumCompte, String senderName, double sendSolde, int recieverNumCompte, String recieverName, double recievedSolde){
        this.senderNumCompte = senderNumCompte;
        this.senderName = senderName;
        this.sendSolde = sendSolde;
        this.recieverNumCompte = recieverNumCompte;
        this.recieverName = recieverName;
        this.recievedSolde = recievedSolde;
    }

    public int getSender(){
        return senderNumCompte;
    }
    public String getSenderName(){
        return senderName;
    }
    public double getSendSolde(){
        return sendSolde;
    }
    public int getReciever(){
        return recieverNumCompte;
    }
    public String getRecieverName(){
        return recieverName;
    }
    public double getRecievedSolde(){
        return recievedSolde;
    }

    public void setSender(int senderNumCompte){
        this.senderNumCompte = senderNumCompte;
    }
    public void setSenderName(String senderName){
        this.senderName = senderName;
    }
    public void setSendSolde(double sendSolde){
        this.sendSolde = sendSolde;
    }
    public void setReciever(int recieverNumCompte){
        this.recieverNumCompte = recieverNumCompte;
    }
    public void setRecieverName(String recieverName){
        this.recieverName = recieverName;
    }
    public void setRecievedSolde(double recievedSolde){
        this.recievedSolde = recievedSolde;
    }
}
