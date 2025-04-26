package main.gestion_des_taches.dao;

import main.gestion_des_taches.config.Dbconfig;
import  main.gestion_des_taches.model.Utilisateur;

import java.sql.*;
import java.util.Optional; //classe fiha ya chi 7aja ya walo (sans null)
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    public Utilisateur save(Utilisateur utilisateur) {
        //la requette
        String sql = "INSERT INTO utilisateurs (nom, email, mot_de_passe) VALUES (?, ?, ?)";

        try (Connection connection = Dbconfig.getConnection(); // connexion avec db

             //preparation du statement
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // retour des cles primaires

            //remplissage de la requette (index, value)
            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotDePasse());



            if (statement.executeUpdate() > 0) { //execution du statement et retour de nbr de lignes inseres (si au moins 1)
            ResultSet generatedKeys = statement.getGeneratedKeys(); //(creation d une liste ResultSet et recuperation des cles primaires)
                if (generatedKeys.next()) { //retour true si il ya une ligne
                    utilisateur.setId(generatedKeys.getInt(1));//(recuperation de la attribution de la cle primaire avec le setter du ID)
                }
                generatedKeys.close(); //Fermer le ResultSet
            }
            return utilisateur; // Envoyer l user
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'utilisateur", e);
        }
    }

    public Optional<Utilisateur> findById(int id) {
        String sql = "SELECT * FROM utilisateurs WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));

                return Optional.of(utilisateur);
            }

            return Optional.empty();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur", e);
        }
    }

    public Optional<Utilisateur> findByEmail(String email) { // <> smithum generics f java
        String sql = "SELECT * FROM utilisateurs WHERE email = ?";

        try (Connection connection = Dbconfig.getConnection(); //connexion avec db
             PreparedStatement statement = connection.prepareStatement(sql)) { //preparation du statement

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur(); //creer une nouvelle instance utilisateur

                //recuperer les données from the ResultSet
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));

                return Optional.of(utilisateur); //creer une instance Optional et la retourner
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur par email", e);
        }
    }

    public List<Utilisateur> findAll() {
        String sql = "SELECT * FROM utilisateurs";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));

                utilisateurs.add(utilisateur);
            }

            return utilisateurs;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs", e);
        }
    }

    public boolean update(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateurs SET nom = ?, email = ?, mot_de_passe = ? WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotDePasse());
            statement.setInt(4, utilisateur.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur", e);
        }
    }
}