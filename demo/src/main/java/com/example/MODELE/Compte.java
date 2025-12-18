package com.example.MODELE;

import com.example.models.User; // use the actual package

public class Compte {
    private int numCompte;
    private User user;
    private double solde;
    private int pin;

    public Compte(int numCompte, User user, int pin, double solde) {
        this.numCompte = numCompte;
        this.user = user;
        this.pin = pin;
        this.solde = solde;
    }

    public int getNumCompte(){
        return numCompte;
    }
    public int getPin(){
        return pin;
    }
    public double getSolde(){
        return solde;
    }
    public User getUser(){
        return user;
    }

    public void setNumCompte(int numCompte){
        this.numCompte = numCompte;
    }
    public void setPin(int pin){
        this.pin = pin;
    }
    public void setSolde(double solde){
        this.solde = solde;
    }
    public void setUser(User user){
        this.user = user;
    }
}

package com.example.MODELE;

public class User {
    private String name;
    public User(String name) { this.name = name; }
    public String getName() { return name; }
}
