package main;

import main.gestion_des_taches.config.Dbconfig;
import main.gestion_des_taches.Console;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import main.gestion_des_taches.model.Utilisateur;

public class BackEndTest {
    public static void main(String[] args) {

        System.out.printf("Hello and welcome!");
        Dbconfig db = new Dbconfig();

        if (db != null) {
            System.out.println("Database connection established!");
        }
        else {
            System.out.println("No database connection established!");
        }
        if(db.getConnection() != null) {
            System.out.println("Database connection established and created!");
        };

        Utilisateur currentUser = null;

        Scanner scanner = new Scanner(System.in);
        Console consoleUI = new Console(scanner);
        consoleUI.start();

        db.closeConnection();

    }
}