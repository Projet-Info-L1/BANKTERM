package com.example.MODELE;

public class Deposit {
      private String name;
    private double depositSolde;

    public Deposit(String name, double depositSolde){
        this.name = name;
        this.depositSolde = depositSolde;
    }

    public String getName(){
        return name;
    }

    public double getDepositSolde(){
        return depositSolde;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDepositSolde(double depositSolde){
        this.depositSolde = depositSolde;
    }


}
