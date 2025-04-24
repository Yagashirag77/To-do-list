package main.gestion_des_taches.service;

import main.gestion_des_taches.dao.TacheDAO;
import main.gestion_des_taches.model.Label;
import main.gestion_des_taches.model.Tache;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TacheService {
    private final TacheDAO tacheDAO;
    private final LabelService labelService;

    public TacheService() {
        this.tacheDAO = new TacheDAO();
        this.labelService = new LabelService();
    }

    public Tache creer(String titre, String description, LocalDate dateEcheance, LocalTime time,
                       int priorite, String statut, int utilisateurId, int projetId, int categorieId) {
        Tache tache = new Tache(titre, description, dateEcheance, time, priorite, statut,
                utilisateurId, projetId, categorieId);
        return tacheDAO.save(tache);
    }

    public Optional<Tache> getById(int id) {
        return tacheDAO.findById(id);
    }

    public List<Tache> getByUtilisateur(int utilisateurId) {
        return tacheDAO.findByUtilisateurId(utilisateurId);
    }

    public List<Tache> getByProjet(int projetId) {
        return tacheDAO.findByProjetId(projetId);
    }

    public List<Tache> getByCategorie(int categorieId) {
        return tacheDAO.findByCategorieId(categorieId);
    }

    public List<Tache> getAll() {
        return tacheDAO.findAll();
    }

    public boolean update(Tache tache) {
        return tacheDAO.update(tache);
    }

    public boolean delete(int id) {
        // Suppression des relations tâche-label
        tacheLabelDAO.deleteByTacheId(id);

        // Suppression de la tâche
        return tacheDAO.delete(id);
    }

    public void ajouterLabel(int tacheId, int labelId) {


    }

    public void supprimerLabel(int tacheId, int labelId) {

    }

    public List<Label> getLabels(int tacheId) {
        return labelService.getByTacheId(tacheId);
    }

    public void modifierStatut(int tacheId, String statut) {
        Optional<Tache> optionalTache = tacheDAO.findById(tacheId);
        if (optionalTache.isPresent()) {
            Tache tache = optionalTache.get();
            tache.setStatut(statut);
            tacheDAO.update(tache);
        } else {
            throw new IllegalArgumentException("Tâche non trouvée");
        }
    }

    public void modifierPriorite(int tacheId, int priorite) {
        if (priorite < 1 || priorite > 3) {
            throw new IllegalArgumentException("La priorité doit être entre 1 et 3");
        }

        Optional<Tache> optionalTache = tacheDAO.findById(tacheId);
        if (optionalTache.isPresent()) {
            Tache tache = optionalTache.get();
            tache.setPriorite(priorite);
            tacheDAO.update(tache);
        } else {
            throw new IllegalArgumentException("Tâche non trouvée");
        }
    }
}