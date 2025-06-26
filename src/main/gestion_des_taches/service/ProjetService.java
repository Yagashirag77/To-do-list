package main.gestion_des_taches.service;

import main.gestion_des_taches.dao.ProjetDAO;
import main.gestion_des_taches.dao.TacheDAO;
import main.gestion_des_taches.model.Projet;
import main.gestion_des_taches.model.Tache;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ProjetService {
    private final ProjetDAO projetDAO;
    private final TacheDAO tacheDAO;

    public ProjetService() {
        this.projetDAO = new ProjetDAO();
        this.tacheDAO = new TacheDAO();
    }

    public Projet creer(String nom, String description, int utilisateurId) {
        Projet projet = new Projet(nom, description, utilisateurId);
        return projetDAO.save(projet);
    }

    public Optional<Projet> getById(int id) {
        return projetDAO.findById(id);
    }
    public List<Projet> getByUtilisateur(int utilisateurId) {
        List<Projet> projets;
    projets = projetDAO.findByUtilisateurId(utilisateurId);
//        System.out.println(utilisateurId);
        if(projets.isEmpty()) {
            System.out.println("Projet n'existe pas");
        }
            return projets;
    }
    public Optional<Projet> getByNom(String nom) {
        return projetDAO.findByNom(nom);
    }

    public boolean update(Projet projet) {
        return projetDAO.update(projet);
}

    public boolean delete(int id) {
        // D'abord, supprime toutes les tâches associées
        List<Tache> taches = tacheDAO.findByProjetId(id);
        TacheService tacheService = new TacheService();

        for (Tache tache : taches) {
            tacheService.delete(tache.getId());
        }

        // Ensuite, supprime le projet
        return projetDAO.delete(id);
    }

    public void ajouterTache(Projet projet, Tache tache) {
        tache.setProjetId(projet.getId());
        tacheDAO.save(tache);
    }

    public void supprimerTache(Projet projet, Tache tache) {
        if (tache.getProjetId() == projet.getId()) {
            TacheService tacheService = new TacheService();
            tacheService.delete(tache.getId());
        } else {
            throw new IllegalArgumentException("Cette tâche n'appartient pas à ce projet");
        }
    }

    public List<Tache> getTaches(int projetId) {
        return tacheDAO.findByProjetId(projetId);
    }
}