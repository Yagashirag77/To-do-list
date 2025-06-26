package main.gestion_des_taches.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbconfig {
    private static final String URL = "jdbc:sqlite:database/gestion_taches_db.db";
    private static boolean databaseInitialized = false;

    public static Connection getConnection() {
        try {
            if (!databaseInitialized) {
                initializeDatabase();
                databaseInitialized = true;
            }
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            throw new RuntimeException("Impossible de se connecter à la base de données", e);
        }
    }

    private static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {

            // Création des tables
            statement.execute("CREATE TABLE IF NOT EXISTS utilisateurs (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL, " +
                     "email TEXT UNIQUE NOT NULL, " +
                    "mot_de_passe TEXT NOT NULL)");

            statement.execute("CREATE TABLE IF NOT EXISTS categories (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL, " +
                    "utilisateur_id INTEGER NOT NULL, " +
                    "FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id))");

            statement.execute("CREATE TABLE IF NOT EXISTS labels (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL, " +
                    "utilisateur_id INTEGER NOT NULL, " +
                    "FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id))");

            statement.execute("CREATE TABLE IF NOT EXISTS projets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL, " +
                    "description TEXT, " +
                    "utilisateur_id INTEGER NOT NULL, " +
                    "FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id))");

            statement.execute("CREATE TABLE IF NOT EXISTS taches (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titre TEXT NOT NULL, " +
                    "description TEXT, " +
                    "date_echeance DATE, " +
                    "time TIME, " +
                    "priorite INTEGER DEFAULT 2, " +
                    "statut TEXT DEFAULT 'en cours', " +
                    "utilisateur_id INTEGER NOT NULL, " +
                    "projet_id INTEGER, " +
                    "label_id INTEGER, " +
                    "categorie_id INTEGER, " +
                    "FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id), " +
                    "FOREIGN KEY (label_id) REFERENCES labels(id), " +
                    "FOREIGN KEY (projet_id) REFERENCES projets(id), " +
                    "FOREIGN KEY (categorie_id) REFERENCES categories(id))");

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la base de données : " + e.getMessage());
            throw new RuntimeException("Erreur lors de l'initialisation de la base de données", e);
        }
    }
}
