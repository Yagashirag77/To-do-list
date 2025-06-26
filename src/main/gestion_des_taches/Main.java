package main.gestion_des_taches;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.gestion_des_taches.config.Dbconfig;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Connexion à la base avant de charger l'interface
//        Dbconfig.getConnection();
//        System.out.println("Connection successful!");

        // Chargement de la page FXML (register)
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("To-Do List");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Fermer la connexion à la fin proprement
//        Dbconfig.closeConnection();
    }

    public static void main(String[] args) {
        launch(args); // JUSTE launch(), pas de getConnection ici !
    }
}
