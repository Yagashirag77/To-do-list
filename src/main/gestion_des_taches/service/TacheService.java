package main.gestion_des_taches.service;

import main.gestion_des_taches.dao.TacheDAO;
import main.gestion_des_taches.dao.ProjetDAO;
import main.gestion_des_taches.model.Tache;
import main.gestion_des_taches.model.Projet;
import main.gestion_des_taches.model.Label;
import main.gestion_des_taches.dao.LabelDAO;
import main.gestion_des_taches.dao.CategorieDAO;




import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TacheService {
    private final TacheDAO tacheDAO;
    private final ProjetDAO projetDAO;
//    private final LabelDAO labelDAO;
    private final CategorieDAO categorieDAO;

    public TacheService() {
        this.tacheDAO = new TacheDAO();
        this.projetDAO = new ProjetDAO();
//        this.labelDAO = new LabelDAO();
        this.categorieDAO = new CategorieDAO();
    }

    public Tache ajouterTache(String titre, String description, LocalDate dateExp, LocalTime time,
                              int priorite, String statut, int utilisateurId, int projetId, int categorieId) {

        Tache tache = new Tache(titre, description, dateExp, time, priorite, statut,
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

    public Optional<Tache> getByTitre(String titre) {
        return tacheDAO.findByTitre(titre);
    }

    public boolean update(Tache tache) {
        return tacheDAO.update(tache);
    }

    public boolean delete(int id) {
        // Suppression de la tâche
        return tacheDAO.delete(id);
    }

//    public void modifierStatut(int tacheId, String statut) {
//        Optional<Tache> optionalTache = tacheDAO.findById(tacheId);
//        if (optionalTache.isPresent()) {
//            Tache tache = optionalTache.get();
//            tache.setStatut(statut);
//            tacheDAO.update(tache);
//        } else {
//            throw new IllegalArgumentException("Tâche non trouvée");
//        }
//    }

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

    public int recupererProjetId(String nomProjet) {
        Optional<Projet> pr = projetDAO.findByNom(nomProjet);
        if (pr.isPresent()) {
            return pr.get().getId();
        } else {
            throw new RuntimeException("Projet introuvable: " + nomProjet);
        }
    }

    public int recupererLabelId(String nomLabel) {
        Optional<Label> lbl = LabelDAO.findByNom(nomLabel);
        if (lbl.isPresent()) {
            return lbl.get().getId();
        } else {
            throw new RuntimeException("Label introuvable");
        }
    }
}