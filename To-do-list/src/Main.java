import main.gestion_des_taches.config.Dbconfig;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        Dbconfig db = new Dbconfig();
        db.getConnection();
        db.closeConnection();

    }
}