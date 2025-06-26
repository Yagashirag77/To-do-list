package main.gestion_des_taches.dao;

import main.gestion_des_taches.config.Dbconfig;
import  main.gestion_des_taches.model.Categorie;
import main.gestion_des_taches.model.Projet;

import java.sql.*;
//(pour remplacer les valeurs nulles ou abscentes)
import java.util.Optional;
//import des tableaux dynamiques (b7al les vecteurs li f cpp)
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {

    public Categorie save(Categorie categorie) {
        String sql = "INSERT INTO categories (nom, utilisateur_id) VALUES (?,?)";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, categorie.getNom());
            statement.setInt(2, categorie.getUtilisateur_id());
            if (statement.executeUpdate() > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                categorie.setId(generatedKeys.getInt(1));
            }
        }

            return categorie;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la catégorie", e);
        }
    }

    public Optional<Categorie> findById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(resultSet.getInt("id"));
                categorie.setNom(resultSet.getString("nom"));

                return Optional.of(categorie);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la catégorie", e);
        }
    }

    public List<Categorie> findAll() {
        String sql = "SELECT * FROM categories";
        List<Categorie> categories = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(resultSet.getInt("id"));
                categorie.setNom(resultSet.getString("nom"));

                categories.add(categorie);
            }

            return categories;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des catégories", e);
        }
    }

    public boolean update(Categorie categorie) {
        String sql = "UPDATE categories SET nom = ? WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, categorie.getNom());
            statement.setInt(2, categorie.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la catégorie", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM categories WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la catégorie", e);
        }
    }

    public Optional<Categorie> findByNom(String nom) {
        String sql = "SELECT * FROM categories WHERE nom = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nom);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Categorie c = new Categorie();
                    c.setId(resultSet.getInt("id"));
                    c.setNom(resultSet.getString("nom"));
                    c.setUtilisateur_id(resultSet.getInt("utilisateur_id"));

                    return Optional.of(c);
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du projet par nom", e);
        }
    }

    public List<Categorie> findByUserId(int id) {
        String sql = "SELECT * FROM categories WHERE utilisateur_id = ?";
        List<Categorie> categories = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(resultSet.getInt("id"));
                categorie.setNom(resultSet.getString("nom"));
                categorie.setUtilisateur_id(resultSet.getInt("utilisateur_id"));

                categories.add(categorie);
            }
//            System.out.println(categories.size());
            for (Categorie c : categories) {
                System.out.println(c.getNom() + " " + c.getUtilisateur_id());
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la catégorie", e);
        }
    }
}
