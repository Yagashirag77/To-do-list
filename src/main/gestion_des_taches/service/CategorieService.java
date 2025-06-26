package main.gestion_des_taches.service;

import main.gestion_des_taches.dao.CategorieDAO;
import main.gestion_des_taches.model.Categorie;
import main.gestion_des_taches.model.Projet;

import java.util.List;
import java.util.Optional;

public class CategorieService {
    private final CategorieDAO categorieDAO;

    public CategorieService() {
        this.categorieDAO = new CategorieDAO();
    }

    public Categorie creer(String nom, int user_id) {
        Categorie categorie = new Categorie(nom, user_id);
        return categorieDAO.save(categorie);
    }

    public Optional<Categorie> getByNom(String nom) {
        return categorieDAO.findByNom(nom);
    }

    public List<Categorie> getByUserId (int userId) {
        return categorieDAO.findByUserId(userId);
    }

    public Optional<Categorie> getById(int id) {
        return categorieDAO.findById(id);
    }

    public List<Categorie> getAll() {
        return categorieDAO.findAll();
    }

    public boolean supprimer(int id) {
        return categorieDAO.delete(id);
    }

    public boolean mettreAJour(Categorie categorie) {
        return categorieDAO.update(categorie);
    }


}