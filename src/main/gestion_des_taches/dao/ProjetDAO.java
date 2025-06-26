package main.gestion_des_taches.dao;

import main.gestion_des_taches.config.Dbconfig;
import main.gestion_des_taches.model.Projet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjetDAO {

    public Projet save(Projet projet) {
        String sql = "INSERT INTO projets (nom, description, utilisateur_id) VALUES (?, ?, ?)";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, projet.getNom());
            statement.setString(2, projet.getDescription());
            statement.setInt(3, projet.getUtilisateurId());

            if (statement.executeUpdate() > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        projet.setId(generatedKeys.getInt(1));
                    }
                }
            }

            return projet;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du projet", e);
        }
    }

    public Optional<Projet> findById(int id) {
        String sql = "SELECT * FROM projets WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Projet projet = new Projet();
                    projet.setId(resultSet.getInt("id"));
                    projet.setNom(resultSet.getString("nom"));
                    projet.setDescription(resultSet.getString("description"));
                    projet.setUtilisateurId(resultSet.getInt("utilisateur_id"));

                    return Optional.of(projet);
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du projet", e);
        }
    }

    public List<Projet> findByUtilisateurId(int utilisateurId) {
        String sql = "SELECT * FROM projets WHERE utilisateur_id = ?";
        List<Projet> projets = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, utilisateurId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Projet projet = new Projet();
                    projet.setId(resultSet.getInt("id"));
                    projet.setNom(resultSet.getString("nom"));
                    projet.setDescription(resultSet.getString("description"));
                    projet.setUtilisateurId(resultSet.getInt("utilisateur_id"));

                    projets.add(projet);
                }
            }

            return projets;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des projets de l'utilisateur", e);
        }
    }

    public List<Projet> findAll() {
        String sql = "SELECT * FROM projets";
        List<Projet> projets = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Projet projet = new Projet();
                projet.setId(resultSet.getInt("id"));
                projet.setNom(resultSet.getString("nom"));
                projet.setDescription(resultSet.getString("description"));
                projet.setUtilisateurId(resultSet.getInt("utilisateur_id"));

                projets.add(projet);
            }

            return projets;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les projets", e);
        }
    }

    public Optional<Projet> findByNom(String nom) {
        String sql = "SELECT * FROM projets WHERE nom = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nom);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Projet projet = new Projet();
                    projet.setId(resultSet.getInt("id"));
                    projet.setNom(resultSet.getString("nom"));
                    projet.setDescription(resultSet.getString("description"));
                    projet.setUtilisateurId(resultSet.getInt("utilisateur_id"));

                    return Optional.of(projet);
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du projet par nom", e);
        }
    }

    public boolean update(Projet projet) {
        String sql = "UPDATE projets SET nom = ?, description = ?, utilisateur_id = ? WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, projet.getNom());
            statement.setString(2, projet.getDescription());
            statement.setInt(3, projet.getUtilisateurId());
            statement.setInt(4, projet.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du projet", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM projets WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du projet", e);
        }
    }
}
