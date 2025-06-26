package main.gestion_des_taches.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import main.gestion_des_taches.service.UtilisateurService;
import java.util.Optional;
import main.gestion_des_taches.model.Utilisateur;
import main.gestion_des_taches.model.Session;


public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;


    @FXML
    private void seConnecter() {
        // 1. Lire les champs email et password
        String email = emailField.getText();
        String password = passwordField.getText();

        // 2. Vérifier que les champs sont remplis
        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Veuillez remplir tous les champs !");
            return;
        }

        // 3. Appeler la méthode du Service
        UtilisateurService utilisateurService = new UtilisateurService();

        Optional<Utilisateur> auth = utilisateurService.loginUtilisateur(email, password);

        // 4. Vérifier la réponse
        if (auth.isEmpty()) {
            errorLabel.setText("Email ou mot de passe incorrect !");

        } else {
            Utilisateur u = auth.get();
            Session.setUtilisateurConnecte(u);
            allerVersMain(); // Aller vers page principale

        }
    }

    @FXML
    private void allerVersRegister() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void allerVersMain() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
