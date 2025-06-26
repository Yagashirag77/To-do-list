package main.gestion_des_taches.dao;

import main.gestion_des_taches.config.Dbconfig;
import  main.gestion_des_taches.model.Label;
import main.gestion_des_taches.model.Projet;

import java.sql.*;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;


public class LabelDAO {

    public Label save(Label label) {
        String sql = "INSERT INTO labels (nom, ) VALUES (?)";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, label.getNom());

            if (statement.executeUpdate() > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                label.setId(generatedKeys.getInt(1));
            }
        }

        return label;
        }
        catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du label", e);
        }
    }

    public Optional<Label> findById(int id) {
        String sql = "SELECT * FROM labels WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Label label = new Label();
                label.setId(resultSet.getInt("id"));
                label.setNom(resultSet.getString("nom"));

                return Optional.of(label); //creer une instance Optional<Label>
            }

            return Optional.empty();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du label : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la recherche du label", e);
        }
    }

    public List<Label> findAll() {
        String sql = "SELECT * FROM labels";
        List<Label> labels = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Label label = new Label();
                label.setId(resultSet.getInt("id"));
                label.setNom(resultSet.getString("nom"));

                labels.add(label);
            }

            return labels;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des labels : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération des labels", e);
        }
    }

    public List<Label> findByTacheId(int tacheId) {
        String sql = "SELECT l.* FROM labels l JOIN tache_labels tl ON l.id = tl.label_id WHERE tl.tache_id = ?";
        List<Label> labels = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, tacheId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Label label = new Label();
                label.setId(resultSet.getInt("id"));
                label.setNom(resultSet.getString("nom"));

                labels.add(label);
            }

            return labels;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des labels de la tâche : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la recherche des labels de la tâche", e);
        }
    }

    public boolean update(Label label) {
        String sql = "UPDATE labels SET nom = ? WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, label.getNom());
            statement.setInt(2, label.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du label", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM labels WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du label", e);
        }
    }

    public static Optional<Label> findByNom(String nom) {
        String sql = "SELECT * FROM labels WHERE nom = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Label label = new Label();
                label.setId(resultSet.getInt("id"));
                label.setNom(resultSet.getString("nom"));
                label.setUtilisateur_id(resultSet.getInt("utilisateur_id"));

                return Optional.of(label); //creer une instance Optional<Projet>
            }

            return Optional.empty(); //Creer une instance Optional Vide
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du projet", e);
        }

    }
}
