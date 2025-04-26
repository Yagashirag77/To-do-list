package main.gestion_des_taches.model;

public class Projet {
    private int id;
    private String nom;
    private String description;
    private int utilisateurId;

    public Projet() {
        this.nom = "Nouveau Projet";
    }

    public Projet(String nom, String description, int utilisateurId) {
        this.nom = nom;
        this.description = description;
        this.utilisateurId = utilisateurId;
    }

    public Projet(int id, String nom, String description, int utilisateurId) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.utilisateurId = utilisateurId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    // Ajouter une tache au projet
    public void ajouterTache(Tache tache) {
        tache.setProjetId(this.id);
    }

    public void supprimerTache(Tache tache) {
        // Cette méthode sera implémentée dans le service
    }

    @Override
    public String toString() {
        return nom;
    }
}