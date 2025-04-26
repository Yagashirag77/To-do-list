package main.gestion_des_taches.model;

public class Label {
    private int id;
    private String nom;
    private int utilisateur_id;

    public Label() {
    }

    public Label(String nom) {
        this.nom = nom;
    }

    public Label(int id, String nom, int utilisateur_id) {
        this.id = id;
        this.nom = nom;
        this.utilisateur_id = utilisateur_id;
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

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    @Override
    public String toString() {
        return nom;
    }
}