ackage com.javafx.Models;

public class Retrait {
    private double retraitSolde;
    private String name;

    public Retrait(double retraitSolde, String name){
        this.name = name;
        this.retraitSolde = retraitSolde;
    }

    public double getRetraitSolde(){
        return retraitSolde;
    }
    public String getName(){
        return name;
    }

    public void setRetraitSolde(double retraitSolde){
        this.retraitSolde = retraitSolde;
    }
    public void setName(String name){
        this.name = name;
    }
}
