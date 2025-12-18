package com.example.VUE;

import com.example.Controleur.Controleur;
import com.example.MODELE.Compte;
import com.example.MODELE.User;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;

public class UserDashboard extends StackPane {

    private BorderPane layout;
    private EventsContol event;
    private Runnable onLogout;
    private Label soldeLabel;
    private VBox historyContainer;

    public UserDashboard(EventsContol event, Runnable onLogout) {
        this.event = event;
        this.onLogout = onLogout;
        
        layout = new BorderPane();
        layout.setStyle("-fx-background-color: #f1f1f1; -fx-background-radius: 20 20;");

        // Barre de navigation
        HBox navBar = createNavBar();
        layout.setTop(navBar);

        // Barre latérale
        VBox sideBar = createSideBar();
        layout.setLeft(sideBar);

        // Zone principale
        BorderPane mainContain = new BorderPane();
        mainContain.setCenter(accountView());
        layout.setCenter(mainContain);

        // Gestion des clics de menu - récupérer les hyperlinks depuis la VBox du menu
        VBox menuBox = (VBox) sideBar.getChildren().get(1);
        Hyperlink account = (Hyperlink) menuBox.getChildren().get(0);
        Hyperlink deposit = (Hyperlink) menuBox.getChildren().get(1);
        Hyperlink withdrawal = (Hyperlink) menuBox.getChildren().get(2);
        Hyperlink transfer = (Hyperlink) menuBox.getChildren().get(3);
        Hyperlink history = (Hyperlink) menuBox.getChildren().get(4);
        Hyperlink logout = (Hyperlink) menuBox.getChildren().get(5);

        account.setOnAction(e -> mainContain.setCenter(accountView()));
        deposit.setOnAction(e -> mainContain.setCenter(depositView()));
        withdrawal.setOnAction(e -> mainContain.setCenter(withdrawalView()));
        transfer.setOnAction(e -> mainContain.setCenter(transferView()));
        history.setOnAction(e -> mainContain.setCenter(historyView()));
        logout.setOnAction(e -> logout());

        this.getChildren().add(layout);
        this.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea 0%, #764ba2 100%);");
    }

    private HBox createNavBar() {
        HBox navBar = new HBox();
        navBar.setPadding(new Insets(15, 12, 15, 12));
        navBar.setMaxWidth(Double.MAX_VALUE);
        navBar.setStyle("-fx-background-color: #000");
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setSpacing(10);
        
        Label title = new Label("BANKTERM");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox.setHgrow(title, javafx.scene.layout.Priority.ALWAYS);
                
        navBar.getChildren().add(title);
        return navBar;
    }

    private VBox createSideBar() {
        VBox sideBar = new VBox();
        sideBar.setPadding(new Insets(10));
        sideBar.setSpacing(15);
        sideBar.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc;");
        sideBar.setPrefWidth(150);

        Label menuLabel = new Label("Menu");
        menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox menuBox = new VBox();
        menuBox.setSpacing(8);

        Hyperlink account = new Hyperlink("👤 Compte");
        Hyperlink deposit = new Hyperlink("💰 Dépôt");
        Hyperlink withdrawal = new Hyperlink("💸 Retrait");
        Hyperlink transfer = new Hyperlink("🔄 Virement");
        Hyperlink history = new Hyperlink("📜 Historique");
        Hyperlink logout = new Hyperlink("🚪 Déconnexion");

        menuBox.getChildren().addAll(account, deposit, withdrawal, transfer, history, logout);

        sideBar.getChildren().addAll(menuLabel, menuBox);
        return sideBar;
    }

    public Node accountView() {
        VBox accountPane = new VBox();
        accountPane.setPadding(new Insets(20));
        accountPane.setSpacing(15);
        accountPane.setStyle("-fx-background-color: #fff; -fx-border-radius: 10;");

        Compte compte = event.getCurrentCompte();
        User user = compte.getUser();

        Label title = new Label("Mon Compte");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setStyle("-fx-padding: 10;");

        addField(grid, 0, "Numéro de compte:", String.valueOf(compte.getNumCompte()));
        addField(grid, 1, "Nom:", user.getName());
        addField(grid, 2, "Prénom:", user.getLastName());
        addField(grid, 3, "Âge:", String.valueOf(user.getAge()));

        soldeLabel = new Label("Solde: " + String.format("%.2f CFA", compte.getSolde()));
        soldeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #28a745;");

        accountPane.getChildren().addAll(title, grid, soldeLabel);
        return accountPane;
    }

    public Node depositView() {
        VBox depositPane = new VBox();
        depositPane.setPadding(new Insets(20));
        depositPane.setSpacing(15);

        Label title = new Label("Effectuer un Dépôt");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        TextField montantField = new TextField();
        montantField.setPromptText("Montant à déposer");

        Button validate = new Button("Valider");
        validate.setStyle("-fx-font-size: 14px; -fx-padding: 8px 30px;");

        validate.setOnAction(e -> {
            try {
                if (montantField.getText().isEmpty()) {
                    showAlert("Erreur", "Veuillez entrer un montant", Alert.AlertType.WARNING);
                    return;
                }
                
                double montant = Double.parseDouble(montantField.getText());
                double newSolde = event.deposit("Dépôt en espèces", montant);
                
                if (newSolde >= 0) {
                    showAlert("Succès", "Dépôt de " + montant + " CFA effectué", Alert.AlertType.INFORMATION);
                    updateSoldeDisplay();
                    montantField.clear();
                }
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "Veuillez entrer un nombre valide", Alert.AlertType.ERROR);
            }
        });

        form.add(new Label("Montant:"), 0, 0);
        form.add(montantField, 1, 0);
        form.add(validate, 2, 0);

        depositPane.getChildren().addAll(title, form);
        return depositPane;
    }

    public Node withdrawalView() {
        VBox withdrawalPane = new VBox();
        withdrawalPane.setPadding(new Insets(20));
        withdrawalPane.setSpacing(15);

        Label title = new Label("Effectuer un Retrait");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        TextField montantField = new TextField();
        montantField.setPromptText("Montant à retirer");

        Button validate = new Button("Valider");
        validate.setStyle("-fx-font-size: 14px; -fx-padding: 8px 30px;");

        validate.setOnAction(e -> {
            try {
                if (montantField.getText().isEmpty()) {
                    showAlert("Erreur", "Veuillez entrer un montant", Alert.AlertType.WARNING);
                    return;
                }
                
                double montant = Double.parseDouble(montantField.getText());
                double newSolde = event.withdrawal("Retrait en espèces", montant);
                
                if (newSolde >= 0) {
                    showAlert("Succès", "Retrait de " + montant + " CFA effectué", Alert.AlertType.INFORMATION);
                    updateSoldeDisplay();
                    montantField.clear();
                } else {
                    showAlert("Erreur", "Solde insuffisant", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "Veuillez entrer un nombre valide", Alert.AlertType.ERROR);
            }
        });

        form.add(new Label("Montant:"), 0, 0);
        form.add(montantField, 1, 0);
        form.add(validate, 2, 0);

        withdrawalPane.getChildren().addAll(title, form);
        return withdrawalPane;
    }

    public Node transferView() {
        VBox transferPane = new VBox();
        transferPane.setPadding(new Insets(20));
        transferPane.setSpacing(15);

        Label title = new Label("Effectuer un Virement");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        TextField montantField = new TextField();
        montantField.setPromptText("Montant à transférer");
        TextField numCompteField = new TextField();
        numCompteField.setPromptText("Numéro de compte du destinataire");

        Button validate = new Button("Valider");
        validate.setStyle("-fx-font-size: 14px; -fx-padding: 8px 30px;");

        validate.setOnAction(e -> {
            try {
                if (montantField.getText().isEmpty() || numCompteField.getText().isEmpty()) {
                    showAlert("Erreur", "Veuillez remplir tous les champs", Alert.AlertType.WARNING);
                    return;
                }
                
                double montant = Double.parseDouble(montantField.getText());
                int receiverNum = Integer.parseInt(numCompteField.getText());
                double newSolde = event.transfer(montant, receiverNum);
                
                if (newSolde >= 0) {
                    showAlert("Succès", "Virement de " + montant + " CFA effectué", Alert.AlertType.INFORMATION);
                    updateSoldeDisplay();
                    montantField.clear();
                    numCompteField.clear();
                } else {
                    showAlert("Erreur", "Virement échoué. Vérifiez les informations", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "Veuillez entrer des nombres valides", Alert.AlertType.ERROR);
            }
        });

        form.add(new Label("Montant:"), 0, 0);
        form.add(montantField, 1, 0);
        form.add(new Label("N° compte destinataire:"), 0, 1);
        form.add(numCompteField, 1, 1);
        form.add(validate, 2, 1);

        transferPane.getChildren().addAll(title, form);
        return transferPane;
    }

    public Node historyView() {
        VBox historyPane = new VBox();
        historyPane.setPadding(new Insets(20));
        historyPane.setSpacing(15);

        Label title = new Label("Historique des Transactions");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        historyContainer = new VBox();
        historyContainer.setSpacing(8);
        historyContainer.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; -fx-border-radius: 5;");

        ScrollPane scrollPane = new ScrollPane(historyContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

        refreshHistory();

        historyPane.getChildren().addAll(title, scrollPane);
        return historyPane;
    }

    private void refreshHistory() {
        if (historyContainer == null) return;
        
        historyContainer.getChildren().clear();
        Compte compte = event.getCurrentCompte();
        List<Map<String, Object>> transactions = event.getHistory().getHistorySorted(compte.getNumCompte());

        if (transactions.isEmpty()) {
            Label empty = new Label("Aucune transaction pour le moment");
            empty.setStyle("-fx-text-fill: #999;");
            historyContainer.getChildren().add(empty);
        } else {
            for (Map<String, Object> tx : transactions) {
                Label txLabel = new Label(event.getHistory().formatTransaction(tx));
                txLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333; -fx-padding: 8; -fx-background-color: #fff; -fx-border-radius: 3;");
                txLabel.setWrapText(true);
                historyContainer.getChildren().add(txLabel);
            }
        }
    }

    private void updateSoldeDisplay() {
        if (soldeLabel != null) {
            Compte compte = event.getCurrentCompte();
            soldeLabel.setText("Solde: " + String.format("%.2f CFA", compte.getSolde()));
        }
    }

    private void addField(GridPane grid, int row, String label, String value) {
        Label labelNode = new Label(label);
        labelNode.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label valueNode = new Label(value);
        valueNode.setStyle("-fx-font-size: 14px;");
        grid.add(labelNode, 0, row);
        grid.add(valueNode, 1, row);
    }

    private void logout() {
        event.logout();
        if (onLogout != null) {
            onLogout.run();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
