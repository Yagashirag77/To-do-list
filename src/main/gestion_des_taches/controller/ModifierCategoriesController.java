package main.gestion_des_taches.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.gestion_des_taches.model.Categorie;
import main.gestion_des_taches.model.Projet;
import main.gestion_des_taches.model.Session;
import main.gestion_des_taches.model.Utilisateur;
import main.gestion_des_taches.service.CategorieService;
import main.gestion_des_taches.service.ProjetService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ModifierCategoriesController {

    @FXML
    private ListView<String> categorieListView;

    @FXML
    private TextField nouvelleCategorieField;

    @FXML
    public void initialize() {
        Utilisateur utilisateur = Session.getUtilisateurConnecte();
        rafraichirListes(utilisateur.getId());

        categorieListView.setOnMouseClicked(event -> {
            String selected = categorieListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                nouvelleCategorieField.setText(selected);
            }
        });
    }

    private void rafraichirListes(int userId) {

        CategorieService categorieService = new CategorieService();
        List<Categorie> cats = categorieService.getByUserId(userId);
//        System.out.println(userId);
//        projets = projetService.getByUtilisateur(userId);
//        categorieListView.getItems().clear(); // Toujours clear avant remplir
        if (!cats.isEmpty()) {
            for (Categorie cat : cats) {
                System.out.println(cat.getNom());
                categorieListView.getItems().add(cat.getNom());
            }
        } else {
            categorieListView.getItems().add("Aucune categorie :(");
        }

    }
        @FXML
    public void ajouterCategorie() {
        String nom = nouvelleCategorieField.getText();

        if (nom.isEmpty()) {
            showError("Le nom de la catégorie est vide !");
            return;
        }

        // Vérifier si la catégorie existe déjà (insensible à la casse)
        for (String cat : categorieListView.getItems()) {
            if (cat.equalsIgnoreCase(nom)) {
                showError("Cette catégorie existe déjà !");
                return;
            }
        }

//        categorieListView.getItems().add(nom);
            int userId = Session.getUtilisateurId();
            CategorieService categorieService = new CategorieService();
            Categorie res = categorieService.creer(nom, userId);
            if (res != null) {
                System.out.println("categorie ajoute");
                showSuccess("Categorie ajoute avec succes!");
                allerAccueil();
            }
            else{
                System.out.println("categorie non ajoute");
                showError("Erreur lors de l'ajouter categorie :(");
            }
        nouvelleCategorieField.clear();
//        showSuccess("Catégorie ajoutée !");
    }

    @FXML
    public void modifierCategorie() {
        String selected = categorieListView.getSelectionModel().getSelectedItem();
        String nouveauNom = nouvelleCategorieField.getText();

        if (selected == null || nouveauNom.isEmpty()) {
            showError("Sélectionnez une catégorie et entrez un nouveau nom !");
            return;
        }

        // Vérifier si le nouveau nom existe déjà
        for (String cat : categorieListView.getItems()) {
            if (cat.equalsIgnoreCase(nouveauNom)) {
                showError("Une catégorie avec ce nom existe déjà !");
                return;
            }
        }

//        int index = categorieListView.getSelectionModel().getSelectedIndex();
//        categorieListView.getItems().set(index, nouveauNom);
//        showSuccess("Catégorie modifiée !");

//        .clear();
        int userId = Session.getUtilisateurConnecte().getId();

        CategorieService categorieService = new CategorieService();
        Optional<Categorie> pr = categorieService.getByNom(selected);
        System.out.println("Nom: " + pr.get().getNom() + " | ID: " +  pr.get().getId());
        Categorie newcat = new Categorie(pr.get().getId(),nouveauNom, userId);
        System.out.println("NOUVEAU PROJET //Nom: " + newcat.getNom() + " | ID: " +  newcat.getId());

        boolean res = categorieService.mettreAJour(newcat);
        if (res) {
            showSuccess("Categorie modifie avec success!");
            nouvelleCategorieField.clear();
            allerAccueil();
        }
        else {
            showError("Erreur lors de la modification de la categorie :(");
        }

    }

    @FXML
    public void supprimerCategorie() {
        String selected = categorieListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Aucune catégorie sélectionnée !");
            return;
        }
//        boolean conf =confirmerSuppression(selected);

            int userId = Session.getUtilisateurConnecte().getId();

            CategorieService categorieService = new CategorieService();
            Optional<Categorie> pr = categorieService.getByNom(selected);
            System.out.println("Nom: " + pr.get().getNom() + " | ID: " +  pr.get().getId());


            boolean res = categorieService.supprimer(pr.get().getId());
            if (res) {
                showSuccess("Categorie suprimmee avec success!");
                nouvelleCategorieField.clear();
                allerAccueil();
            }
            else {
                showError("Erreur lors de la supression de la categorie :(");
            }

//        categorieListView.getItems().remove(selected);
//        showSuccess("Catégorie supprimée !");
//        .clear();

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
        chargerPage("/fxml/taches_sans_projet.fxml");
    }

    @FXML
    private void allerLabels() {
        chargerPage("/fxml/modifier_labels.fxml");
    }

    @FXML
    private void allerCategories() {
        // Déjà sur la page catégories
    }

    @FXML
    private void seDeconnecter() {
        chargerPage("/fxml/login.fxml");
    }

    private void chargerPage(String chemin) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(chemin));
            Stage stage = (Stage) categorieListView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
