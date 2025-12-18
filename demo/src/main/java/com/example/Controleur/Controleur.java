package com.example.Controleur;

import com.javafx.Data.DataManager;
import com.javafx.Data.TransactionHistory;
import com.javafx.Models.Compte;
import com.javafx.Models.User;

/**
 * Contrôleur des événements bancaires
 * Gère les opérations bancaires avec persistance et historique
 */
public class EventsContol {
    private DataManager dataManager;
    private TransactionHistory history;
    private Compte currentCompte;

    public EventsContol() {
        this.dataManager = new DataManager();
        this.history = new TransactionHistory();
    }

    /**
     * Authentifie un utilisateur et charge son compte
     * @return true si l'authentification est réussie, false sinon
     */
    public boolean login(int numCompte, int pin) {
        if (dataManager.validateCredentials(numCompte, pin)) {
            currentCompte = dataManager.getCompte(numCompte);
            System.out.println("✓ Connexion réussie pour le compte: " + numCompte);
            return true;
        }
        System.out.println("✗ Identifiants invalides");
        return false;
    }

    /**
     * Crée un nouveau compte
     * @return true si la création est réussie, false sinon
     */
    public boolean signIn(String name, String lastName, int age, int pin) {
        try {
            if (pin < 1000 || pin > 9999) {
                System.out.println("✗ Le PIN doit être un nombre à 4 chiffres");
                return false;
            }

            int numCompte = (int) System.currentTimeMillis();
            User user = new User(name, lastName, age);
            Compte compte = new Compte(numCompte, user, pin, 0.0);
            
            dataManager.addCompte(compte);
            currentCompte = compte;
            
            // Enregistrer la création du compte dans l'historique
            history.recordTransaction(numCompte, "CRÉATION COMPTE", 
                    "Compte créé pour " + name + " " + lastName, 0.0, 0.0);
            
            System.out.println("✓ Compte créé avec succès");
            System.out.println("  Numéro: " + numCompte);
            System.out.println("  Titulaire: " + name + " " + lastName);
            return true;
        } catch (Exception e) {
            System.out.println("✗ Erreur lors de la création du compte: " + e.getMessage());
            return false;
        }
    }

    /**
     * Effectue un dépôt sur le compte courant
     */
    public double deposit(String description, double montant) {
        if (currentCompte == null) {
            System.out.println("✗ Aucun compte connecté");
            return -1;
        }

        if (montant <= 0) {
            System.out.println("✗ Le montant doit être positif");
            return -1;
        }

        currentCompte.setSolde(currentCompte.getSolde() + montant);
        history.recordDeposit(currentCompte.getNumCompte(), description, montant, currentCompte.getSolde());
        dataManager.updateCompte(currentCompte);
        
        System.out.println("✓ Dépôt de " + montant + " effectué");
        return currentCompte.getSolde();
    }

    /**
     * Effectue un retrait sur le compte courant
     */
    public double withdrawal(String description, double montant) {
        if (currentCompte == null) {
            System.out.println("✗ Aucun compte connecté");
            return -1;
        }

        if (montant <= 0) {
            System.out.println("✗ Le montant doit être positif");
            return -1;
        }

        if (currentCompte.getSolde() < montant) {
            System.out.println("✗ Solde insuffisant. Solde actuel: " + currentCompte.getSolde());
            return -1;
        }

        currentCompte.setSolde(currentCompte.getSolde() - montant);
        history.recordWithdrawal(currentCompte.getNumCompte(), description, montant, currentCompte.getSolde());
        dataManager.updateCompte(currentCompte);
        
        System.out.println("✓ Retrait de " + montant + " effectué");
        return currentCompte.getSolde();
    }

    /**
     * Effectue un virement vers un autre compte
     */
    public double transfer(double montant, int receiverNum) {
        if (currentCompte == null) {
            System.out.println("✗ Aucun compte connecté");
            return -1;
        }

        if (montant <= 0) {
            System.out.println("✗ Le montant doit être positif");
            return -1;
        }

        if (currentCompte.getSolde() < montant) {
            System.out.println("✗ Solde insuffisant");
            return -1;
        }

        Compte receiverCompte = dataManager.getCompte(receiverNum);
        if (receiverCompte == null) {
            System.out.println("✗ Compte destinataire introuvable");
            return -1;
        }

        // Débiter l'émetteur
        currentCompte.setSolde(currentCompte.getSolde() - montant);
        history.recordTransferSent(currentCompte.getNumCompte(), 
                currentCompte.getUser().getName() + " " + currentCompte.getUser().getLastName(),
                montant, receiverNum, 
                receiverCompte.getUser().getName() + " " + receiverCompte.getUser().getLastName(),
                currentCompte.getSolde());
        dataManager.updateCompte(currentCompte);

        // Créditer le destinataire
        receiverCompte.setSolde(receiverCompte.getSolde() + montant);
        history.recordTransferReceived(receiverNum,
                receiverCompte.getUser().getName() + " " + receiverCompte.getUser().getLastName(),
                montant, currentCompte.getNumCompte(),
                currentCompte.getUser().getName() + " " + currentCompte.getUser().getLastName(),
                receiverCompte.getSolde());
        dataManager.updateCompte(receiverCompte);

        System.out.println("✓ Virement de " + montant + " vers le compte " + receiverNum + " effectué");
        return currentCompte.getSolde();
    }

    /**
     * Récupère le compte courant
     */
    public Compte getCurrentCompte() {
        return currentCompte;
    }

    /**
     * Récupère l'historique des transactions
     */
    public TransactionHistory getHistory() {
        return history;
    }

    /**
     * Déconnecte l'utilisateur
     */
    public void logout() {
        currentCompte = null;
        System.out.println("✓ Déconnexion effectuée");
    }

}



