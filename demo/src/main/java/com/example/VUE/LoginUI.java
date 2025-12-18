package com.example.VUE;


import com.example.Controleur.Controleur;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class LoginUI extends StackPane {

    private VBox container;
    private EventsContol event;
    private Runnable onSuccessfulLogin;

    public LoginUI(EventsContol eventController, Runnable onSuccessfulLogin) {
        this.event = eventController;
        this.onSuccessfulLogin = onSuccessfulLogin;
        container = new VBox(20);
        container.setMaxWidth(400);
        container.setMaxHeight(Double.MIN_VALUE);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #fff; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 20,0,0,5);");
        
        loginView();
        
        this.getChildren().add(container);
        this.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea 0%, #764ba2 100%);");
    }

    private void loginView() {
        container.getChildren().clear();

        Label title = new Label("Connexion");
        title.setTextFill(Color.BLACK);
        title.setFont(Font.font(30));
        title.setStyle("-fx-font-weight: bold;");
        title.setAlignment(Pos.TOP_CENTER);
        
        TextField numCompteField = new TextField();
        numCompteField.setPromptText("Numéro de compte");
        PasswordField pinField = new PasswordField();
        pinField.setPromptText("Code PIN");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(5);
        form.add(new Label("Numéro de compte: "), 0, 0);
        form.add(numCompteField, 1, 0);
        form.add(new Label("Code PIN: "), 0, 1);
        form.add(pinField, 1, 1);

        Button loginButton = new Button("Se connecter");
        loginButton.setStyle("-fx-font-size: 12px; -fx-padding: 8px 40px;");

        loginButton.setOnAction(e -> {
            try {
                if (numCompteField.getText().isEmpty() || pinField.getText().isEmpty()) {
                    showAlert("Erreur", "Veuillez remplir tous les champs", Alert.AlertType.WARNING);
                    return;
                }

                int numCompte = Integer.parseInt(numCompteField.getText());
                int pin = Integer.parseInt(pinField.getText());
                
                if (event.login(numCompte, pin)) {
                    showAlert("Succès", "Connexion réussie!", Alert.AlertType.INFORMATION);
                    if (onSuccessfulLogin != null) {
                        onSuccessfulLogin.run();
                    }
                } else {
                    showAlert("Erreur", "Identifiants invalides", Alert.AlertType.ERROR);
                    pinField.clear();
                }
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "Veuillez entrer des nombres valides", Alert.AlertType.ERROR);
            }
        });

        HBox text = new HBox();
        Hyperlink toSignIn = new Hyperlink("S'inscrire.");
        toSignIn.setOnAction(e -> signInView());
        Text message = new Text("Vous n'avez pas de compte? ");
        text.getChildren().addAll(message, toSignIn);
        text.setAlignment(Pos.CENTER);

        container.getChildren().addAll(title, form, loginButton, text);
    }

    private void signInView() {
        container.getChildren().clear();

        GridPane form = new GridPane();
        Label title = new Label("Inscription");
        title.setTextFill(Color.BLACK);
        title.setAlignment(Pos.TOP_CENTER);
        title.setFont(Font.font(30));
        title.setStyle("-fx-font-weight: bold;");
        
        TextField nameField = new TextField();
        nameField.setPromptText("Nom");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Prénom");
        TextField ageField = new TextField();
        ageField.setPromptText("Âge");
        PasswordField pinField = new PasswordField();
        pinField.setPromptText("Code PIN (4 chiffres)");
        PasswordField confirmPinField = new PasswordField();
        confirmPinField.setPromptText("Confirmer le Code PIN");
        
        form.setHgap(10);
        form.setVgap(5);
        form.setAlignment(Pos.CENTER);

        form.add(new Label("Nom: "), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Prénom: "), 0, 1);
        form.add(lastNameField, 1, 1);
        form.add(new Label("Âge: "), 0, 2);
        form.add(ageField, 1, 2);
        form.add(new Label("Code PIN: "), 0, 3);
        form.add(pinField, 1, 3);
        form.add(new Label("Confirmer le Code PIN: "), 0, 4);
        form.add(confirmPinField, 1, 4);

        Button signInButton = new Button("S'inscrire");
        signInButton.setStyle("-fx-font-size: 12px; -fx-padding: 8px 40px;");

        signInButton.setOnAction(e -> {
            try {
                if (nameField.getText().isEmpty() || lastNameField.getText().isEmpty() || 
                    ageField.getText().isEmpty() || pinField.getText().isEmpty() || 
                    confirmPinField.getText().isEmpty()) {
                    showAlert("Erreur", "Veuillez remplir tous les champs", Alert.AlertType.WARNING);
                    return;
                }

                if (!pinField.getText().equals(confirmPinField.getText())) {
                    showAlert("Erreur", "Les codes PIN ne correspondent pas", Alert.AlertType.ERROR);
                    return;
                }

                if (pinField.getText().length() != 4) {
                    showAlert("Erreur", "Le code PIN doit contenir 4 chiffres", Alert.AlertType.ERROR);
                    return;
                }

                String name = nameField.getText();
                String lastName = lastNameField.getText();
                int age = Integer.parseInt(ageField.getText());
                int pin = Integer.parseInt(pinField.getText());

                if (event.signIn(name, lastName, age, pin)) {
                    showAlert("Succès", "Compte créé avec succès!\nNuméro de compte: " + event.getCurrentCompte().getNumCompte(),
                            Alert.AlertType.INFORMATION);
                    if (onSuccessfulLogin != null) {
                        onSuccessfulLogin.run();
                    }
                } else {
                    showAlert("Erreur", "Erreur lors de la création du compte", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "Veuillez entrer des nombres valides", Alert.AlertType.ERROR);
            }
        });

        HBox text = new HBox();
        Hyperlink toLogin = new Hyperlink("Se connecter");
        toLogin.setOnAction(e -> loginView());
        Text message = new Text("Vous avez déjà un compte? ");
        text.getChildren().addAll(message, toLogin);
        text.setAlignment(Pos.CENTER);

        container.getChildren().addAll(title, form, signInButton, text);
    }

    public EventsContol getEvent() {
        return event;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
