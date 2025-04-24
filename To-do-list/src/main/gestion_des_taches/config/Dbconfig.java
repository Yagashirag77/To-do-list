package main.gestion_des_taches.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Dbconfig {
    private static final String URL = "jdbc:sqlite:src/main/database/gestion_taches_db.db";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
                initializeDatabase();
            } catch (SQLException e) {
                System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
                throw new RuntimeException("Impossible de se connecter à la base de données", e);
            }
        }
        return connection;
    }

    private static void initializeDatabase() {
        try (Statement statement = connection.createStatement()) {
            // Création des tables
            statement.execute("CREATE TABLE IF NOT EXISTS utilisateurs (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL, " +
                    "email TEXT UNIQUE NOT NULL, " +
                    "mot_de_passe TEXT NOT NULL)");

            statement.execute("CREATE TABLE IF NOT EXISTS categories (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL)");

            statement.execute("CREATE TABLE IF NOT EXISTS labels (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL)");

            statement.execute("CREATE TABLE IF NOT EXISTS projets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL, " +
                    "description TEXT, " +
                    "utilisateur_id INTEGER, " +
                    "FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id))");

            statement.execute("CREATE TABLE IF NOT EXISTS taches (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titre TEXT NOT NULL, " +
                    "description TEXT, " +
                    "date_echeance DATE, " +
                    "time TIME, " +
                    "priorite INTEGER DEFAULT 2, " +
                    "statut TEXT DEFAULT 'en cours', " +
                    "utilisateur_id INTEGER, " +
                    "projet_id INTEGER, " +
                    "categorie_id INTEGER, " +
                    "FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id), " +
                    "FOREIGN KEY (projet_id) REFERENCES projets(id), " +
                    "FOREIGN KEY (categorie_id) REFERENCES categories(id))");

//            statement.execute("CREATE TABLE IF NOT EXISTS tache_labels (" +
//                    "tache_id INTEGER, " +
//                    "label_id INTEGER, " +
//                    "PRIMARY KEY (tache_id, label_id), " +
//                    "FOREIGN KEY (tache_id) REFERENCES taches(id), " +
//                    "FOREIGN KEY (label_id) REFERENCES labels(id))");

            // Insertion de quelques données par défaut
            insertDefaultData(statement);

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la base de données : " + e.getMessage());
            throw new RuntimeException("Erreur lors de l'initialisation de la base de données", e);
        }
    }

    private static void insertDefaultData(Statement statement) throws SQLException {
        // Ajouter quelques catégories par défaut
        statement.execute("INSERT OR IGNORE INTO categories (id, nom) VALUES (1, 'Travail')");
        statement.execute("INSERT OR IGNORE INTO categories (id, nom) VALUES (2, 'Personnel')");
        statement.execute("INSERT OR IGNORE INTO categories (id, nom) VALUES (3, 'Études')");

        // Ajouter quelques labels par défaut
        statement.execute("INSERT OR IGNORE INTO labels (id, nom) VALUES (1, 'Urgent')");
        statement.execute("INSERT OR IGNORE INTO labels (id, nom) VALUES (2, 'Important')");
        statement.execute("INSERT OR IGNORE INTO labels (id, nom) VALUES (3, 'En attente')");
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}