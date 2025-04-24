package main.gestion_des_taches.service;

import main.gestion_des_taches.dao.UtilisateurDAO;
import main.gestion_des_taches.model.Utilisateur;

import java.util.List;
import java.util.Optional;

public class UtilisateurService {
    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurService() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    public Utilisateur creer(String nom, String email, String motDePasse) {
        // Vérifie si l'email existe déjà
        if (utilisateurDAO.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }

        Utilisateur utilisateur = new Utilisateur(nom, email, motDePasse);
        return utilisateurDAO.save(utilisateur);
    }

    public Optional<Utilisateur> connexion(String email, String motDePasse) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurDAO.findByEmail(email);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            // Vérification simplifiée du mot de passe (en production, utiliser une méthode de hachage sécurisée)
            if (utilisateur.getMotDePasse().equals(motDePasse)) {
                return Optional.of(utilisateur);
            }
        }

        return Optional.empty();
    }

    public Optional<Utilisateur> getById(int id) {
        return utilisateurDAO.findById(id);
    }

    public Optional<Utilisateur> getByEmail(String email) {
        return utilisateurDAO.findByEmail(email);
    }

    public List<Utilisateur> getAll() {
        return utilisateurDAO.findAll();
    }

    public boolean update(Utilisateur utilisateur) {
        // Vérifie si l'email existe déjà pour un autre utilisateur
        Optional<Utilisateur> existingUser = utilisateurDAO.findByEmail(utilisateur.getEmail());
        if (existingUser.isPresent() && existingUser.get().getId() != utilisateur.getId()) {
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }

        return utilisateurDAO.update(utilisateur);
    }

    public boolean delete(int id) {
        return utilisateurDAO.delete(id);
    }
}