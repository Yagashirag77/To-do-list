package main.gestion_des_taches.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.gestion_des_taches.model.Session;
import main.gestion_des_taches.service.ProjetService;
import main.gestion_des_taches.model.Projet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjetController {

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ListView<String> listeTaches;

    @FXML
    private Button addButton;

    // Stocker les tâches temporairement
    private static List<String> tachesTemp = new ArrayList<>();

    @FXML
    public void initialize() {
        // Recharger les tâches ajoutées temporairement
        listeTaches.getItems().setAll(tachesTemp);
    }

    @FXML
    public void ajouterTacheAuProjet() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/tache.fxml"));
            Stage stage = (Stage) nomField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void creerProjet() {
        String nom = nomField.getText();
        String description = descriptionField.getText();

        if (nom.isEmpty() || description.isEmpty()) {
            showError("Le nom et la description du projet sont obligatoires !");
            return;
        }
        ProjetService projetService = new ProjetService();
        int userId = Session.getUtilisateurId();
        Projet res = projetService.creer(nom, description, userId);
        if (res != null) {
            System.out.println("Projet ajoute");
        }
        else{
            System.out.println("Projet non ajoute");
        }


//        if (tachesTemp.isEmpty()) {
//            showError("Vous devez ajouter au moins une tâche au projet !");
//            return;
//        }

        // Réinitialiser après création
        nomField.clear();
        descriptionField.clear();
        listeTaches.getItems().clear();
        tachesTemp.clear();

        showSuccess("Projet créé avec succès !");
        allerVersMain();

    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode appelée par TacheController pour ajouter une tâche
    public static void ajouterTache(String tache) {
        tachesTemp.add(tache);
    }

    private void allerVersMain() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show(); // <-- c'est bien aussi d'ajouter show() !
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur lors du chargement de la page principale.");
        }
    }

}
