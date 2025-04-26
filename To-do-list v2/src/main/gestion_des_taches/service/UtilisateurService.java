package main.gestion_des_taches.service;

import main.gestion_des_taches.dao.UtilisateurDAO;
import main.gestion_des_taches.model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UtilisateurService {
    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurService() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    public Utilisateur registerUtilisateur(String nom, String email, String motDePasse) {
        // Vérifie si l'email existe déjà
        if (utilisateurDAO.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }
        String mdpHash = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
        Utilisateur utilisateur = new Utilisateur(nom, email, mdpHash);
        return utilisateurDAO.save(utilisateur);
    }

    public Optional<Utilisateur> loginUtilisateur(String email, String motDePasse) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurDAO.findByEmail(email);

        if (optionalUtilisateur.isEmpty()) {
            throw new IllegalArgumentException("Email invalide");
        }

        Utilisateur utilisateur = optionalUtilisateur.get();
        // Vérification simplifiée du mot de passe (en production, utiliser une méthode de hachage sécurisée)
        if (!BCrypt.checkpw(motDePasse, utilisateur.getMotDePasse())) {
            throw new IllegalArgumentException("Mot de passe incorrect");
        }
        return Optional.of(utilisateur);
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
