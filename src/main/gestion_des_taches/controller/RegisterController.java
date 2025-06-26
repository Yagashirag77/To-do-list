package main.gestion_des_taches.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import main.gestion_des_taches.config.Dbconfig;
import main.gestion_des_taches.service.UtilisateurService;
import java.util.Optional;
import main.gestion_des_taches.model.Utilisateur;

public class RegisterController {

    @FXML private TextField nomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void inscrireUtilisateur() {
        String nom = nomField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || nom.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs !");
            return;
        }

        try {
            UtilisateurService utilisateurService = new UtilisateurService();
            Utilisateur res = utilisateurService.registerUtilisateur(nom, email, password);

            if (res != null) {
                messageLabel.setText("Compte créé avec succès !");
                allerVersLogin();
            } else {
                messageLabel.setText("Erreur lors de la création du compte.");
            }

        } catch (IllegalArgumentException e) {
            messageLabel.setText(e.getMessage()); // (ex: "Cet email est déjà utilisé")
        } catch (Exception e) {
            messageLabel.setText("Erreur inattendue lors de la création du compte.");
            e.printStackTrace(); // Pour debug si besoin
        }
    }


    @FXML
    private void allerVersLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Stage stage = (Stage) nomField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
