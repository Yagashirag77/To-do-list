package main.gestion_des_taches.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Tache {
    private int id;
    private String titre;
    private String description;
    private LocalDate dateExp;
    private LocalTime time;
    private Integer priorite; // 1: haute, 2: moyenne, 3: basse
    private String statut; // "en cours" ou "terminée"
    private int utilisateurId;
    private int projetId;
//    private Integer labelId;
    private int categorieId;

    //Constructeurs
    public Tache() {
        this.titre = "Nouvelle Tache";
        this.priorite = 2; // Priorité moyenne par défaut
        this.statut = "A faire"; // Statut par défaut
    }

    public Tache(String titre, String description, LocalDate dateExp, LocalTime time,
                 int priorite, String statut, int utilisateurId, int projetId, int categorieId) {
        this.titre = titre;
        this.description = description;
        this.dateExp = dateExp;
        this.time = time;
        this.priorite = priorite;
        this.statut = statut;
        this.utilisateurId = utilisateurId;
        this.projetId = projetId;
        this.categorieId = categorieId;
//        if (labelId != null) {
//            this.labelId = labelId;
//        }
    }

//    public Tache(int id, String titre, String description, LocalDate dateExp, LocalTime time,
//                 int priorite, String statut, int utilisateurId, int projetId, int categorieId, Integer labelId) {
//        this.id = id;
//        this.titre = titre;
//        this.description = description;
//        this.dateExp = dateExp;
//        this.time = time;
//        this.priorite = priorite;
//        this.statut = statut;
//        this.utilisateurId = utilisateurId;
//        this.projetId = projetId;
//        this.categorieId = categorieId;
//
//    }

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

    public LocalDate getDateExp() {
        return dateExp;
    }

    public void setDateExp(LocalDate dateExp) {
        this.dateExp = dateExp;
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

//    public Integer getLabelId() {
//        return labelId;
//    }
//
//    public void setLabelId(Integer labelId) {
//        this.labelId = labelId;
//    }

    // Modifier le status
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