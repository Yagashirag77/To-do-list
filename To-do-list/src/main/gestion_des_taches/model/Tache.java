package main.gestion_des_taches.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Tache {
    private int id;
    private String titre;
    private String description;
    private LocalDate dateEcheance;
    private LocalTime time;
    private int priorite; // 1: haute, 2: moyenne, 3: basse
    private String statut; // "en cours" ou "terminée"
    private int utilisateurId;
    private int projetId;
    private int categorieId;

    public Tache() {
        this.priorite = 2; // Priorité moyenne par défaut
        this.statut = "en cours"; // Statut par défaut
    }

    public Tache(String titre, String description, LocalDate dateEcheance, LocalTime time,
                 int priorite, String statut, int utilisateurId, int projetId, int categorieId) {
        this.titre = titre;
        this.description = description;
        this.dateEcheance = dateEcheance;
        this.time = time;
        this.priorite = priorite;
        this.statut = statut;
        this.utilisateurId = utilisateurId;
        this.projetId = projetId;
        this.categorieId = categorieId;
    }

    public Tache(int id, String titre, String description, LocalDate dateEcheance, LocalTime time,
                 int priorite, String statut, int utilisateurId, int projetId, int categorieId) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateEcheance = dateEcheance;
        this.time = time;
        this.priorite = priorite;
        this.statut = statut;
        this.utilisateurId = utilisateurId;
        this.projetId = projetId;
        this.categorieId = categorieId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getProjetId() {
        return projetId;
    }

    public void setProjetId(int projetId) {
        this.projetId = projetId;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    // Méthodes du diagramme de classe
    public void modifierStatut(String nouveauStatut) {
        this.statut = nouveauStatut;
    }

    public void modifierPriorite(int nouvellePriorite) {
        this.priorite = nouvellePriorite;
    }

    // Méthodes utilitaires
    public String getPrioriteLibelle() {
        return switch (priorite) {
            case 1 -> "Haute";
            case 2 -> "Moyenne";
            case 3 -> "Basse";
            default -> "Non définie";
        };
    }

    @Override
    public String toString() {
        return titre;
    }
}