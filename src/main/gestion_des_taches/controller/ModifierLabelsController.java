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

public class ModifierLabelsController {

    @FXML
    private ListView<String> labelListView;

    @FXML
    private TextField nouveauLabelField;

    @FXML
    public void initialize() {
        labelListView.getItems().addAll("Urgent", "Important", "Personnel");

        labelListView.setOnMouseClicked(event -> {
            String selected = labelListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                nouveauLabelField.setText(selected);
            }
        });
    }

    @FXML
    public void ajouterLabel() {
        String nom = nouveauLabelField.getText();

        if (nom.isEmpty()) {
            showError("Le nom du label est vide !");
            return;
        }

        for (String label : labelListView.getItems()) {
            if (label.equalsIgnoreCase(nom)) {
                showError("Ce label existe déjà !");
                return;
            }
        }

        labelListView.getItems().add(nom);
        nouveauLabelField.clear();
        showSuccess("Label ajouté !");
    }

    @FXML
    public void modifierLabel() {
        String selected = labelListView.getSelectionModel().getSelectedItem();
        String nouveauNom = nouveauLabelField.getText();

        if (selected == null || nouveauNom.isEmpty()) {
            showError("Sélectionnez un label et entrez un nouveau nom !");
            return;
        }

        for (String label : labelListView.getItems()) {
            if (label.equalsIgnoreCase(nouveauNom)) {
                showError("Un label avec ce nom existe déjà !");
                return;
            }
        }

        int index = labelListView.getSelectionModel().getSelectedIndex();
        labelListView.getItems().set(index, nouveauNom);
        showSuccess("Label modifié !");
        nouveauLabelField.clear();
    }

    @FXML
    public void supprimerLabel() {
        String selected = labelListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Aucun label sélectionné !");
            return;
        }

        labelListView.getItems().remove(selected);
        showSuccess("Label supprimé !");
        nouveauLabelField.clear();
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
        chargerPage("/fxml/taches_sans_projets.fxml");
    }

    @FXML
    private void allerLabels() {
        // Déjà sur la page labels
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
            Stage stage = (Stage) labelListView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
