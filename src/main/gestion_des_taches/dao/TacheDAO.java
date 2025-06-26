package main.gestion_des_taches.dao;

import main.gestion_des_taches.config.Dbconfig;
import main.gestion_des_taches.model.Tache;
import main.gestion_des_taches.model.Label;

import java.sql.*;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {

    public Tache save(Tache tache) {
        String sql = "INSERT INTO taches (titre, description, date_echeance, time, priorite, statut, utilisateur_id, projet_id, categorie_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, tache.getTitre());
            statement.setString(2, tache.getDescription());

            if (tache.getDateExp() != null) {
                statement.setDate(3, Date.valueOf(tache.getDateExp())); //valueOf jaya men java.sql.date
            } else {
                statement.setNull(3, Types.DATE);
            }

            if (tache.getTime() != null) {
                statement.setTime(4, Time.valueOf(tache.getTime()));
            } else {
                statement.setNull(4, Types.TIME);
            }

            statement.setInt(5, tache.getPriorite());
            statement.setString(6, tache.getStatut());
            statement.setInt(7, tache.getUtilisateurId());

            if (tache.getProjetId() > 0) {
                statement.setInt(8, tache.getProjetId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }

            if (tache.getCategorieId() > 0) {
                statement.setInt(9, tache.getCategorieId());
            } else {
                statement.setNull(9, Types.INTEGER);
            }

            if (statement.executeUpdate() > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                tache.setId(generatedKeys.getInt(1));
            }
        }

            return tache;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la tâche", e);
        }
    }
    private Tache extrextractTacheFromResultSet(ResultSet resultSet) throws SQLException {
        Tache tache = new Tache();
        tache.setId(resultSet.getInt("id"));
        tache.setTitre(resultSet.getString("titre"));
        tache.setDescription(resultSet.getString("description"));
        tache.setDateExp(resultSet.getDate("date_exp").toLocalDate());
        tache.setTime(resultSet.getTime("heure").toLocalTime());
        tache.setPriorite(resultSet.getInt("priorite"));
        tache.setStatut(resultSet.getString("statut"));
        tache.setUtilisateurId(resultSet.getInt("utilisateur_id"));
        tache.setProjetId(resultSet.getInt("projet_id"));
        tache.setCategorieId(resultSet.getInt("categorie_id"));
        return tache;
    }

    public Optional<Tache> findById(int id) {
        String sql = "SELECT * FROM taches WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(extractTacheFromResultSet(resultSet));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la tâche", e);
        }
    }

    public List<Tache> findByUtilisateurId(int utilisateurId) {
        String sql = "SELECT * FROM taches WHERE utilisateur_id = ?";
        List<Tache> taches = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, utilisateurId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                taches.add(extractTacheFromResultSet(resultSet));
            }

            return taches;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des tâches de l'utilisateur", e);
        }
    }

    public List<Tache> findByProjetId(int projetId) {
        String sql = "SELECT * FROM taches WHERE projet_id = ?";
        List<Tache> taches = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, projetId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                taches.add(extractTacheFromResultSet(resultSet));
            }

            return taches;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des tâches du projet", e);
        }
    }

    public List<Tache> findByCategorieId(int categorieId) {
        String sql = "SELECT * FROM taches WHERE categorie_id = ?";
        List<Tache> taches = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categorieId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                taches.add(extractTacheFromResultSet(resultSet));
            }

            return taches;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des tâches par catégorie", e);
        }
    }

    public List<Tache> findAll() {
        String sql = "SELECT * FROM taches";
        List<Tache> taches = new ArrayList<>();

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                taches.add(extractTacheFromResultSet(resultSet));
            }

            return taches;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des tâches : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération des tâches", e);
        }
    }

    public boolean update(Tache tache) {
        String sql = "UPDATE taches SET titre = ?, description = ?, date_echeance = ?, time = ?, " +
                "priorite = ?, statut = ?, utilisateur_id = ?, projet_id = ?, categorie_id = ? " +
                "WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tache.getTitre());
            statement.setString(2, tache.getDescription());

            if (tache.getDateExp() != null) {
                statement.setDate(3, Date.valueOf(tache.getDateExp()));
            } else {
                statement.setNull(3, Types.DATE);
            }

            if (tache.getTime() != null) {
                statement.setTime(4, Time.valueOf(tache.getTime()));
            } else {
                statement.setNull(4, Types.TIME);
            }

            statement.setInt(5, tache.getPriorite());
            statement.setString(6, tache.getStatut());
            statement.setInt(7, tache.getUtilisateurId());

            if (tache.getProjetId() > 0) {
                statement.setInt(8, tache.getProjetId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }

            if (tache.getCategorieId() > 0) {
                statement.setInt(9, tache.getCategorieId());
            } else {
                statement.setNull(9, Types.INTEGER);
            }

            statement.setInt(10, tache.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la tâche", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM taches WHERE id = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la tâche", e);
        }
    }

    private Tache extractTacheFromResultSet(ResultSet resultSet) throws SQLException {
        Tache tache = new Tache();
        tache.setId(resultSet.getInt("id"));
        tache.setTitre(resultSet.getString("titre"));
        tache.setDescription(resultSet.getString("description"));
        tache.setProjetId(resultSet.getInt("projet_id"));
        tache.setCategorieId(resultSet.getInt("categorie_id"));
        tache.setUtilisateurId(resultSet.getInt("utilisateur_id"));
        tache.setPriorite(resultSet.getInt("priorite"));
        tache.setStatut(resultSet.getString("statut"));

        Date dateEcheance = resultSet.getDate("date_echeance");
        if (dateEcheance != null) {
            tache.setDateExp(dateEcheance.toLocalDate());
        }

        Time time = resultSet.getTime("time");
        if (time != null) {
            tache.setTime(time.toLocalTime());
        }

        tache.setPriorite(resultSet.getInt("priorite"));
        tache.setStatut(resultSet.getString("statut"));
        tache.setUtilisateurId(resultSet.getInt("utilisateur_id"));

        int projetId = resultSet.getInt("projet_id");
        if (!resultSet.wasNull()) {
            tache.setProjetId(projetId);
        }

        int categorieId = resultSet.getInt("categorie_id");
        if (!resultSet.wasNull()) {
            tache.setCategorieId(categorieId);
        }

        return tache;
    }
    public Optional<Tache> findByTitre(String titre) {
        String sql = "SELECT * FROM taches WHERE Titre = ?";

        try (Connection connection = Dbconfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, titre);
            try
                (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    Optional<Tache> tache = Optional.of(extractTacheFromResultSet(resultSet));
                    return tache;
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des tâches de l'utilisateur", e);
        }
    }


}
