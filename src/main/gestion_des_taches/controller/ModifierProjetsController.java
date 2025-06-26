package main.gestion_des_taches.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.gestion_des_taches.model.Projet;
import main.gestion_des_taches.model.Session;
import main.gestion_des_taches.model.Utilisateur;
import main.gestion_des_taches.service.ProjetService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class ModifierProjetsController {
    @FXML
    private Button modifierButton;

    @FXML
    private ListView<String> projetListView;

    @FXML
    private TextField nouveauProjetField;

    @FXML
    public void initialize() {
        //3yet l service bach yjib les projets

        //jib les projets f variable
        //afficher les projets

        Utilisateur utilisateur = Session.getUtilisateurConnecte();
        rafraichirListes(utilisateur.getId());



        projetListView.setOnMouseClicked(event -> {
            String selected = projetListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                nouveauProjetField.setText(selected);
            }
        });
    }
    private void rafraichirListes(int userId) {

        ProjetService projetService = new ProjetService();
        List<Projet> projets = projetService.getByUtilisateur(userId);
//        System.out.println(userId);
//        projets = projetService.getByUtilisateur(userId);
        projetListView.getItems().clear(); // Toujours clear avant remplir
        if (!projets.isEmpty()) {
            for (Projet projet : projets) {
                projetListView.getItems().add(projet.getNom());
            }
        } else {
            projetListView.getItems().add("Aucun projet :(");
        }

    }
    @FXML
    public void modifierProjet() {
        String selected = projetListView.getSelectionModel().getSelectedItem();
        String nouveauNom = nouveauProjetField.getText();
        System.out.println(nouveauNom);

        if (selected == null || nouveauNom.isEmpty()) {
            showError("Sélectionnez un projet et entrez un nouveau nom !");
            return;
        }

        for (String projet : projetListView.getItems()) {
            if (projet.equalsIgnoreCase(nouveauNom)) {
                showError("Un projet avec ce nom existe déjà !");
                return;
            }
        }
        int userId = Session.getUtilisateurConnecte().getId();

        ProjetService projetService = new ProjetService();
        Optional<Projet> pr = projetService.getByNom(selected);
        System.out.println("Nom: " + pr.get().getNom() + " | ID: " +  pr.get().getId());
        Projet newPr = new Projet(pr.get().getId() ,nouveauNom, pr.get().getDescription(), userId);
        System.out.println("NOUVEAU PROJET //Nom: " + newPr.getNom() + " | ID: " +  newPr.getId());

        boolean res = projetService.update(newPr);
        if (res) {
            showSuccess("Projet modifie avec success");
            nouveauProjetField.clear();
            allerVersMain();
        }
        else {
            showError("Erreur lors de la modification du projet :(");
        }


    }

    @FXML
    public void supprimerProjet() {
        String selected = projetListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Aucun projet sélectionné !");
            return;
        }
        boolean conf =confirmerSuppression(selected);
        if (conf) {
            int userId = Session.getUtilisateurConnecte().getId();

            ProjetService projetService = new ProjetService();
            Optional<Projet> pr = projetService.getByNom(selected);
            System.out.println("Nom: " + pr.get().getNom() + " | ID: " +  pr.get().getId());


            boolean res = projetService.delete(pr.get().getId());
            if (res) {
                showSuccess("Projet suprimme avec success");
                nouveauProjetField.clear();
                allerVersMain();
            }
            else {
                showError("Erreur lors de la supression du projet :(");
            }

        }
    }

    private boolean confirmerSuppression(String element) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer " + element + " ?");

        ButtonType oui = new ButtonType("Oui");
        ButtonType non = new ButtonType("Non");

        confirmation.getButtonTypes().setAll(oui, non);

        return confirmation.showAndWait().orElse(non) == oui;
    }

    @FXML
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
        // Déjà sur la page Projets
    }

    @FXML
    private void allerTaches() {
        chargerPage("/fxml/taches_sans_projet.fxml");
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
            Stage stage = (Stage) projetListView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void allerVersMain() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            Stage stage = (Stage) modifierButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show(); // <-- c'est bien aussi d'ajouter show() !
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur lors du chargement de la page principale.");
        }
    }
}
