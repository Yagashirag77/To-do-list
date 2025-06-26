package main.gestion_des_taches.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class TachesSansProjetController {

    @FXML
    private ListView<String> tacheListView;

    @FXML
    private TextField nouvelleTacheField;

    @FXML
    public void initialize() {
        tacheListView.getItems().addAll("Acheter du pain", "Réviser Java", "Envoyer email important");

        tacheListView.setOnMouseClicked(event -> {
            String selected = tacheListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                nouvelleTacheField.setText(selected);
            }
        });
    }

    @FXML
    public void modifierTache() {
        String selected = tacheListView.getSelectionModel().getSelectedItem();
        String nouveauTitre = nouvelleTacheField.getText();

        if (selected == null || nouveauTitre.isEmpty()) {
            showError("Sélectionnez une tâche et entrez un nouveau titre !");
            return;
        }

        for (String tache : tacheListView.getItems()) {
            if (tache.equalsIgnoreCase(nouveauTitre)) {
                showError("Une tâche avec ce titre existe déjà !");
                return;
            }
        }

        int index = tacheListView.getSelectionModel().getSelectedIndex();
        tacheListView.getItems().set(index, nouveauTitre);
        showSuccess("Tâche modifiée !");
        nouvelleTacheField.clear();
    }

    @FXML
    public void supprimerTache() {
        String selected = tacheListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Aucune tâche sélectionnée !");
            return;
        }

        tacheListView.getItems().remove(selected);
        showSuccess("Tâche supprimée !");
        nouvelleTacheField.clear();
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

    // Navigation Sidebar
    @FXML
    private void allerAccueil() {
        chargerPage("/fxml/main.fxml");
    }

    @FXML
    private void allerProjets() {
        chargerPage("/fxml/modifier_projets.fxml");
    }

    @FXML
    private void allerTaches() {
        // Déjà sur la page tâches
    }

    @FXML
    private void allerLabels() {
        chargerPage("/fxml/modifier_labels.fxml");
    }

    @FXML
    private void allerCategories() {
        chargerPage("/fxml/modifier_categories.fxml");
    }

    @FXML
    private void seDeconnecter() {
        chargerPage("/fxml/login.fxml");
    }

    private void chargerPage(String chemin) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(chemin));
            Stage stage = (Stage) tacheListView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
