package main.gestion_des_taches.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.gestion_des_taches.service.ProjetService;
import main.gestion_des_taches.service.TacheService;
import main.gestion_des_taches.model.Projet;
import main.gestion_des_taches.model.Tache;
import main.gestion_des_taches.model.Utilisateur;
import main.gestion_des_taches.model.Session;
import main.gestion_des_taches.dao.ProjetDAO;

import javax.swing.*;


// (imports inchangés)

public class MainController {
    //liaison entre les pages du projets
    private static int projetSelectionne = 1;

    public static int getProjetSelectionne() {
        return projetSelectionne;
    }

    @FXML
    private ListView<String> projetListView;

    @FXML
    private ListView<String> tacheListView;

    @FXML
    private ListView<String> tachesSansProjetListView;

    @FXML
    private Button modfierTache;

    private static List<String> projets = new ArrayList<>();
    private static List<String> taches = new ArrayList<>();
    @FXML private Tache getClickedTache() {
        String selectedTitre = tacheListView.getSelectionModel().getSelectedItem();
        TacheService tacheService = new TacheService();
        Optional<Tache> tacheOptional = tacheService.getByTitre(selectedTitre);
        Tache t = tacheOptional.get();
//      popupTache(t);
        return t;
    }


    @FXML
    public void initialize() {
        Utilisateur utilisateur = Session.getUtilisateurConnecte();
        rafraichirListes(utilisateur.getId());

        projetListView.setOnMouseClicked(event ->
        {
//            projetListView.setStyle("-fx-control-inner-background: white;");

            tacheListView.getItems().clear();
            String selected = projetListView.getSelectionModel().getSelectedItem();
            ProjetService projetService = new ProjetService();
            Optional<Projet> res = projetService.getByNom(selected);
            if (res.isPresent()) {
                int id = res.get().getId();
                projetSelectionne =id;
                List<Tache> taches = projetService.getTaches(id);
                if (!taches.isEmpty()) {
                    for (Tache tache : taches) {

                        tacheListView.getItems().add(tache.getTitre());
                        tacheListView.setCellFactory(CheckBoxListCell.forListView(
                                task -> {
                                    BooleanProperty observable = new SimpleBooleanProperty();

                                    observable.addListener((obs, wasSelected, isNowSelected) -> {
                                        if (isNowSelected) {
                                            tache.setStatut("terminé"); // changer statut
//                                            tacheListView.setStyle("-fx-background-color: #ff0000;");
                                            System.out.println(isNowSelected + tache.getStatut());

                                            TacheService service = new TacheService();
                                            service.update(tache); // mettre à jour dans la base
                                        }
                                    });

                                    return observable;
                                }
                        ));

                        //9ad l click dyal les taches (pop ups)
                        tacheListView.setOnMouseClicked(eventt -> {
                            getClickedTache();
                        });
                    }
                }
                else {
                    tacheListView.getItems().add("Acune tache dans le projet");
                }
            }
        });

    } //b7ala creyiti implicitement wa7ed la classe interface EventHandler
    // o overriditi l method handle bach t geger l evenement dyal l click


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
            projetListView.getItems().add("Aucun projet !");
        }

        tacheListView.getItems().setAll(taches);

    }

    @FXML
    private void ajouterProjet() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/projet.fxml"));
            Stage stage = (Stage) projetListView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void appelpopup() {
        popupTache(getClickedTache());
    }

    @FXML
    private void popupTache(Tache tache) {

        Stage popupStage = new Stage();
        popupStage.setTitle("Modifier");

        // Création des éléments
        javafx.scene.control.TextField titreField = new javafx.scene.control.TextField(tache.getTitre());
        titreField.getStyleClass().add("input-field");
        javafx.scene.control.TextArea descriptionField = new javafx.scene.control.TextArea(tache.getDescription());
        descriptionField.getStyleClass().add("input-field");
        javafx.scene.control.Button enregistrerButton = new javafx.scene.control.Button("Enregistrer");
        enregistrerButton.getStyleClass().add("button");
        javafx.scene.control.Button supprimerButton = new javafx.scene.control.Button("Supprimer");
        supprimerButton.getStyleClass().add("button-danger");

        // Enregistrer
        enregistrerButton.setOnAction(e -> {
            String nouveauTitre = titreField.getText();
            String nouvelleDescription = descriptionField.getText();

            // Mets à jour l'objet tâche
            tache.setTitre(nouveauTitre);
            tache.setDescription(nouvelleDescription);

            // Appelle ton Service/DAO pour enregistrer dans la base ici
            TacheService tacheService = new TacheService();
            tacheService.update(tache);  // (faut avoir une méthode update)

            popupStage.close(); // Fermer la fenêtre après
            rafraichirListes(Session.getUtilisateurId()); // (optionnel) Rafraîchir liste
        });

        // Supprimer
        supprimerButton.setOnAction(e -> {
            // Appelle ton Service/DAO pour supprimer de la base ici
            TacheService tacheService = new TacheService();
            tacheService.delete(tache.getId());

            popupStage.close(); // Fermer la fenêtre après
            rafraichirListes(Session.getUtilisateurId()); // (optionnel) Rafraîchir liste
        });

        // Organisation dans une VBox
        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(10);
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #F5F5F5;");
        vbox.getStyleClass().add("card");
        vbox.getChildren().addAll(
                new javafx.scene.control.Label("Titre:"), titreField,
                new javafx.scene.control.Label("Description:"), descriptionField,
                enregistrerButton, supprimerButton
        );

        Scene scene = new Scene(vbox, 300, 300);
        popupStage.setScene(scene);
        popupStage.show();
    }


    @FXML
    private void ajouterTache() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/tache.fxml"));
            Stage stage = (Stage) projetListView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ajouterNouveauProjet(String projetNom) {
        projets.add(projetNom);
    }

    public static void ajouterTacheSansProjet(String tacheTitre) {
        taches.add(tacheTitre);
    }

    @FXML
    private void voirDetailProjet() {
        String selected = projetListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showInfo("Détail du Projet", "Projet sélectionné : " + selected);
        } else {
            showError("Veuillez sélectionner un projet !");
        }
    }

    @FXML
    private void voirDetailTache() {
        String selected = tacheListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showInfo("Détail de la Tâche", "Tâche sélectionnée : " + selected);
        } else {
            showError("Veuillez sélectionner une tâche !");
        }
    }



    private void showInfo(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
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
        int userId = Session.getUtilisateurConnecte().getId();
        rafraichirListes(userId);
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
        chargerPage("/fxml/modifier_categories.fxml");
    }

    @FXML
    private void seDeconnecter() {
        showError("Vouz devez vous connecter");
        Session.logout();
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




}
