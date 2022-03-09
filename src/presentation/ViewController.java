package presentation;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ViewController {
    private final Scanner scanner;

    public ViewController() {
        scanner = new Scanner(System.in);
    }

    public void showMenuPersistance () {
        System.out.println("\nThe IEEE needs to know where your allegiance lies.\n");
        System.out.println("\tI) People's Front of Engineering (CSV)");
        System.out.println("\tII) Engineering People's Front (JSON)");
    }

    public void showMainMenu() {
        System.out.println();
        System.out.println("\tA) The Composer");
        System.out.println("\tB) This year's Conductor");
    }

    public void showCompositorMenu() {
        System.out.println();
        System.out.println("\t1) Manage Trials");
        System.out.println("\t2) Manage Editions");
        System.out.println();
        System.out.println("\t3) Exit");
    }

    public void showSubMenuTrials () {
        System.out.println();
        System.out.println("\ta) Create Trial");
        System.out.println("\tb) Show Trials");
        System.out.println("\tc) Delete Trial");
        System.out.println();
        System.out.println("\td) Back");
    }

    public void showMenuEditions () {
        System.out.println();
        System.out.println("\ta) Create Edition");
        System.out.println("\tb) List Editions");
        System.out.println("\tc) Duplicate Edition");
        System.out.println("\td) Delete Edition");
        System.out.println();
        System.out.println("\te) Back");
    }

    public void showTypesTrials () {
        System.out.println("\n\t--- Trial types ---\n");
        System.out.println("\t1) Paper Publication");
        System.out.println("\t2) Master Studies");
        System.out.println("\t3) Doctoral Thesis Defense");
        System.out.println("\t4) Budget Request\n");

    }

    public int askForInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nThat's not a valid integer, try again!\n");
            } finally {
                scanner.nextLine();
            }
        }
    }

    public String askForString (String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public void showMessage (String message) {
        System.out.println(message);
    }

    public void showMessageLine (String message) { System.out.print(message); }

    public void showList (List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.println("\t " + (i+1) + ") " + items.get(i));
        }
    }

    public void showListGuion (List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.println("\t " + (i+1) + "- " + items.get(i) + " " + "(Paper publication)");
        }
    }

    public void showStartingMenu () {
        System.out.println("I) People’s Front of Engineering (CSV)\nII) Engineering People’s Front (JSON)\n");
    }

    public void showLogo () {
        System.out.print("_____ _           _____     _       _ \n" +
                "/__   \\|__   ___ /__   \\___(_) __ _| |___ \n" +
                " / /\\/ '_ \\ / _ \\  / /\\/'__| |/ _` | / __|\n" +
                "/ /  | | | | ___/ / /  | | | | (_| | \\__ \\\n" +
                "\\/   |_| |_|\\___| \\/   |_| |_|\\__,_|_|___/\n");
    }
}