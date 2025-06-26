package main.gestion_des_taches.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.gestion_des_taches.model.Categorie;
import main.gestion_des_taches.model.Projet;
import main.gestion_des_taches.model.Session;
import main.gestion_des_taches.service.ProjetService;
import main.gestion_des_taches.service.TacheService;
import main.gestion_des_taches.model.Tache;
import main.gestion_des_taches.service.CategorieService;
import main.gestion_des_taches.model.Categorie;
import main.gestion_des_taches.controller.MainController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TacheController {

    @FXML
    private TextField titreField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private DatePicker dateEcheancePicker;

    @FXML
    private TextField heureField;

    @FXML
    private ComboBox<String> prioriteComboBox;

    @FXML
    private ComboBox<String> statutComboBox;

    @FXML
    private ComboBox<String> categorieComboBox;

    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {
        prioriteComboBox.getItems().addAll("Haute", "Moyenne", "Basse");
        statutComboBox.getItems().addAll("En cours", "Terminée");
        fillCategorie();

    }
    public void fillCategorie() {
        int userId = Session.getUtilisateurId();
        CategorieService categorieService = new CategorieService();
        List<Categorie> categories = categorieService.getByUserId(userId);
        if(categories != null) {
            for (Categorie categorie : categories) {
                categorieComboBox.getItems().add(categorie.getNom());
            }
        }
        else {
            categorieComboBox.getItems().clear();
        }
    }

    @FXML
    public void creerTache() {
        String titre = titreField.getText();
        String description = descriptionField.getText();
        String dateExp = (dateEcheancePicker.getValue() != null) ? dateEcheancePicker.getValue().toString() : "";
        LocalDate date = LocalDate.parse(dateExp);
        String heure = heureField.getText();
        LocalTime heureTime = LocalTime.parse(heure);
        String priorite = prioriteComboBox.getValue();
        int pr = 0;
        switch (priorite) {
            case "Haute": pr = 3;
            break;
            case "Moyenne": pr = 2;
            break;
            case "Basse": pr = 1;
            break;
//            default: pr = 0;
        }

//        int pr = Integer.parseInt(priorite);
        String statut = statutComboBox.getValue();
        String categorie = categorieComboBox.getValue();
//        System.out.println(categorie);


        if (titre.isEmpty() || dateExp.isEmpty() || heure.isEmpty()) {
            showError("Tous les champs sont obligatoires !");
            return;
        }
        CategorieService cs = new CategorieService();
        Optional<Categorie> c = cs.getByNom(categorie);
        System.out.println(c);
        if (!c.isPresent()) {
            showError("Categorie n'existe pas :(");
            return;
        }
        TacheService tacheService = new TacheService();
        int userId = Session.getUtilisateurId();
        int projetID = MainController.getProjetSelectionne();
        Tache res = tacheService.ajouterTache(titre, description, date, heureTime, pr, statut, userId, projetID, c.get().getId());
        if (res != null) {
            System.out.println("Tache ajoute");
        }
        else{
            System.out.println("Tache non ajoute :(");
        }


//        if (tachesTemp.isEmpty()) {
//            showError("Vous devez ajouter au moins une tâche au projet !");
//            return;
//        }

        // Réinitialiser après création
        titreField.clear();
        descriptionField.clear();
        dateEcheancePicker.setValue(null);
        heureField.clear();
        prioriteComboBox.setValue(null);
        statutComboBox.setValue(null);
//        listeTaches.getItems().clear();

        showSuccess("Tache créé avec succès !");
        allerVersMain();

    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void allerVersMain() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show(); // <-- c'est bien aussi d'ajouter show() !
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur lors du chargement de la page principale :(");
        }
    }
}
