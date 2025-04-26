package main.gestion_des_taches.dao;

import main.gestion_des_taches.config.Dbconfig;
import  main.gestion_des_taches.model.Categorie;

import java.sql.*;
//(pour remplacer les valeurs nulles ou abscentes)
import java.util.Optional;
//import des tableaux dynamiques (b7al les vecteurs li f cpp)
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {

    public Categorie save(Categorie categorie) {
        String sql = "INSERT INTO categories (nom) VALUES (?)";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, categorie.getNom());

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
}
