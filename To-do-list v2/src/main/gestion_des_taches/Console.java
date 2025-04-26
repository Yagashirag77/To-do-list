package main.gestion_des_taches;

import java.util.Scanner;
import main.gestion_des_taches.model.Tache;
import main.gestion_des_taches.model.Utilisateur;
import main.gestion_des_taches.model.Label;
import main.gestion_des_taches.model.Projet;
import main.gestion_des_taches.model.Categorie;
import main.gestion_des_taches.service.TacheService;
import main.gestion_des_taches.service.ProjetService;
import main.gestion_des_taches.service.LabelService;
import main.gestion_des_taches.service.CategorieService;
import main.gestion_des_taches.service.UtilisateurService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class Console {
    private final Scanner scanner;
    private final UtilisateurService userService;
    private final TacheService taskService;
    private Utilisateur currentUser;

    public Console(Scanner scanner) {
        this.scanner = scanner;
        this.userService = new UtilisateurService();
        this.taskService = new TacheService();
        this.currentUser = null;
    }

    public void start() {
        while (true) {
            loginMenu();
            String choix = scanner.nextLine();
            try {
                switch (choix) {
                    case "1" -> handleRegister();
                    case "2" -> handleLogin();
                    case "0" -> {
                        System.out.println("Au revoir !");
                        return;
                    }
                    default -> System.out.println("Choix invalide.");
                }
            } catch (RuntimeException e) {
                System.err.println("Erreur : " + e.getMessage());
            }
        }
    }


    private void loginMenu() {
        System.out.println("\n=== MENU Console ToDo ===");
        System.out.println("1) S'inscrire");
        System.out.println("2) Se connecter");
        System.out.println("0) Quitter");
        System.out.print("Choix : ");
    }

    private void handleRegister() {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();
        Utilisateur u = userService.registerUtilisateur(nom, email, mdp);
        System.out.println("Enregistré ! Votre ID : " + u.getId());
        this.currentUser = u;
        homeMenu();
    }

    private void handleLogin() {
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();

        Utilisateur u = userService.loginUtilisateur(email, mdp)
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe invalide"));
        System.out.println("Connecté en tant que : " + u.getNom());
        this.currentUser = u;
        homeMenu();
    }

    public void homeMenu() {
        boolean exit = false;
        int id = currentUser.getId();
        while (!exit) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1) Afficher toutes les tâches");
            System.out.println("2) Gérer les tâches (ajouter, modifier, supprimer)");
            System.out.println("3) Afficher tous les projets");
            System.out.println("4) Gérer les projets (ajouter, modifier, supprimer)");
            System.out.println("5) Gérer les labels (ajouter, modifier, supprimer)");
            System.out.println("6) Gérer les catégories (ajouter, modifier, supprimer)");
            System.out.println("0) Quitter");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();
            switch (choix) {
                case "1" -> showAllTasks();   // méthode à implémenter
                case "2" -> manageTasksMenu();          // méthode à implémenter
                case "3" -> showAllProjects();          // méthode à implémenter
                case "4" -> manageProjectsMenu();       // méthode à implémenter
                case "5" -> manageLabelsMenu();         // méthode à implémenter
                case "6" -> manageCategoriesMenu();     // méthode à implémenter
                case "0" -> {
                    exit = true;
                    System.out.println("Fermeture du menu principal.");
                }
                default -> System.out.println("Choix invalide, réessayez.");
            }
        }
    }


    private void handleLogout() {
        currentUser = null;
        System.out.println("Déconnecté.");
    }
}
