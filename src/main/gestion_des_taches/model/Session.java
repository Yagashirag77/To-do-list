package main.gestion_des_taches.model;

import main.gestion_des_taches.model.Utilisateur;

public class Session {
    private static Utilisateur utilisateurConnecte;

    public static void setUtilisateurConnecte(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }

    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public static int getUtilisateurId() {
        return utilisateurConnecte != null ? utilisateurConnecte.getId() : -1;
    }

    public static void logout() {
        utilisateurConnecte = null;
    }
}
