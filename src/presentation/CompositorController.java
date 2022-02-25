package presentation;

import business.EditionManager;
import business.ManagersTrials.GenericTrialManager;
import presentation.managers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class CompositorController {
    private ViewController view;
    private BudgetController budgetController;
    private DoctoralController doctoralController;
    private EditionController editionController;
    private MasterController masterController;
    private PaperController paperController;
    private TeamController teamController;
    private EditionManager editionManager;
    private GenericTrialManager genericTrialManager;

    public CompositorController(ViewController view, BudgetController budgetController, DoctoralController doctoralController, EditionController editionController, MasterController masterController, PaperController paperController, TeamController teamController, EditionManager editionManager, GenericTrialManager genericTrialManager) {
        this.view = view;
        this.budgetController = budgetController;
        this.doctoralController = doctoralController;
        this.editionController = editionController;
        this.masterController = masterController;
        this.paperController = paperController;
        this.teamController = teamController;
        this.editionManager = editionManager;
        this.genericTrialManager = genericTrialManager;
    }

    public void run () throws IOException {
        int option;

        view.showMessage("\nEntering management mode...");
        do {
            view.showCompositorMenu();
            option = view.askForInteger("\nEnter an option: ");

            switch (option){
                case 1:
                    manageTrials();
                    break;
                case 2:
                    manageEditions();
                    break;
                case 3:
                    view.showMessage("\nShutting down...");
                    break;
                default:
                    view.showMessage("\nIncorrect option. Option must be one of the above [1, 2, 3]");
            }
        } while (option != 3);
    }

    private void manageTrials () throws IOException {
        String option;
        do {
            view.showSubMenuTrials();
            option = view.askForString("\nEnter an option: ");
            switch (option) {
                case "a":
                    addTrial();
                    view.showMessage("\nThe trial was created successfully!");
                    break;
                case "b":
                    listTrials();
                    break;
                case "c":
                    deleteTrial();
                    break;
                case "d":
                    //Vuelves al menu principal
                    break;
                default:
                    view.showMessage("\nIncorrect option. Option must be one of the above [a, b, c, d]");
            }
        } while (!option.equals("d"));
    }

    private void addTrial () throws IOException {
        view.showTypesTrials();
        int type_trial = view.askForInteger("Enter the trial's type: ");

        switch (type_trial) {
            case 1:
                paperController.add();
                break;
            case 2:
                masterController.add();
                break;
            case 3:
                doctoralController.add();
                break;
            case 4:
                budgetController.add();
                break;
            default:
                view.showMessage("\nInvalid option");
        }
    }

    /**Falta ver si esto se gestiona desde aqui o desde el Controller de cada clase particular**/
    private void listTrials () throws FileNotFoundException {
        // Como mostramos todos? Que cada manager muestre su tipo de trials?
        // Otra alternativa es tener un fichero que guarde los nombres
        // de todos los trials (independientemente del tipo)
        if (!genericTrialManager.getTrials().isEmpty()) {
            int numTrial = askForInput("\nHere are the current trials, do you want to see more details or go back?", 1);
            if (numTrial > 0 && numTrial <= genericTrialManager.getTrials().size()) {
                switch (genericTrialManager.getTrialTypeByIndex(numTrial)) {
                    case DOCTORAL -> {
                        doctoralController.showDoctoral(numTrial);
                    }
                    case BUDGET -> {
                        budgetController.showBudget(numTrial);
                    }
                    case PAPER -> {
                        paperController.showPaper(numTrial);
                    }
                    case MASTER -> {
                        masterController.showMaster(numTrial);
                    }
                }
            }
        }
    }

    /**Falta ver si esto se gestiona desde aqui o desde el Controller de cada clase particular**/
    private void deleteTrial () throws FileNotFoundException {

    }

    private void manageEditions () throws IOException {

        String option_edition;
        do {
            view.showMenuEditions();
            option_edition = view.askForString("\nEnter an option: ");
            switch (option_edition) {
                case "a":
                    addEdition ();
                    break;
                case "b":
                    listEditions ();
                    break;
                case "c":
                    duplicateEdition ();
                    break;
                case "d":
                    deleteEdition ();
                    break;
                case "e":
                    //menu
                    break;
                default:
                    view.showMessage("\nIncorrect option. Option must be one of the above [a, b, c, d, e]");
            }
        } while (!option_edition.equals("e"));
    }

    private void addEdition () throws IOException {
        int year, numPlayers, numTrials;
        year = view.askForInteger("\nEnter the edition's year: ");
        do {
            numPlayers = view.askForInteger("Enter the initial number of players: ");
            if (numPlayers < 0 || numPlayers > 5) {
                System.out.println("\nIncorrect option\n");
            }
        } while (numPlayers < 0 || numPlayers > 5);
        do {
            numTrials = view.askForInteger("Enter the number of trials: ");
            if (numTrials < 3 || numTrials > 12) {
                System.out.println("\nIncorrect option");
            }
        } while (numTrials < 3 || numTrials > 12);
        view.showMessage("\n\t--- Trials ---\n");
        view.showList(trialManager.getTrialsNames());

        // Guardamos los indices de las pruebas que se quieren guardar en la edición
        ArrayList<Integer> trialsIndexes = new ArrayList<>();
        view.showMessage("");
        for (int i = 0; i < numTrials; i++) {
            trialsIndexes.add(view.askForInteger("Pick a trial (" + (i + 1) + "/" + numTrials + "): ") - 1);
        }

        // Activamos las pruebas introducidas como en uso
        trialManager.setInUseByIndexes(trialsIndexes);

        // Obtenemos los nombres de las pruebas con dichos índices
        String[] names = trialManager.getTrialsNamesByIndexes(trialsIndexes);  // Array de strings donde se guardaran los nombres que necesitemos

        if (editionManager.addEdition(year, numPlayers, numTrials, names)) {
            view.showMessage("\nThe edition was created succesfully!");
        }
    }


    private void deleteEdition () throws IOException {
        if (!editionManager.getEditions().isEmpty()) {
            int numEdition = askForInput("\nWhich edition do you want to delete?", 2);
            if (numEdition > 0 && numEdition <= editionManager.getEditions().size()) {
                int year;
                year = view.askForInteger("\nEnter the edition's year for confirmation: ");
                if (editionManager.getEditionByIndex(numEdition).getYear() == year) {
                    LinkedList<String> nameTrials = editionManager.getAllTrialsNamesInUse();
                    if (editionManager.deleteEdition(year)) {
                        changeStateTrial(nameTrials);
                        view.showMessage("\nThe edition was successfully deleted.");
                    } else {
                        view.showMessage("\nEdition could not be deleted.");
                    }
                }else{
                    view.showMessage("\nThe year introduced does not match");
                }
            } else if (numEdition == editionManager.getEditions().size() + 1) {
                //Back
            } else {
                view.showMessage("\nThe introduced edition is not valid");
            }
        } else {
            view.showMessage("\nNo editions can be deleted as there are no existing editions.");
        }

    }

    private void duplicateEdition () throws IOException {
        if (!editionManager.getEditions().isEmpty()) {
            int numEdition = askForInput("\nWhich edition do you want to clone?", 2);
            if (numEdition > 0 && numEdition <= editionManager.getEditions().size()) {
                int year = view.askForInteger("\nEnter the new edition's year: ");
                int numPlayers;
                do {
                    numPlayers = view.askForInteger("Enter the new edition's initial number of players: ");
                    if (numPlayers < 0 || numPlayers > 5) {
                        System.out.println("\nIncorrect option");
                    }
                } while (numPlayers < 0 || numPlayers > 5);
                editionManager.duplicateEdition(numEdition, year, numPlayers);
                view.showMessage("\nThe edition was cloned successfully!");

            } else if (numEdition == editionManager.getEditions().size() + 1) {
                //Back
            } else {
                view.showMessage("\nThe introduced edition is not valid.");
            }
        }
        else {
            view.showMessage("\nNo editions can be duplicated as there are no existing editions.");
        }

    }

    private void listEditions () throws FileNotFoundException {
        if (!editionManager.getEditions().isEmpty()) {
            int numEdition = askForInput("\nHere are the current editions, do you want to see more details or go back?", 2);

            if (numEdition > 0 && numEdition <= editionManager.getEditions().size()) {
                view.showMessage("\nYear: " + editionManager.getEditionByIndex(numEdition).getYear());
                view.showMessage("Players: " + editionManager.getEditionByIndex(numEdition).getNumPlayers());
                view.showMessage("Trials: ");
                view.showListGuion(editionManager.getEditionTrialsNames(numEdition-1));
            } else if (numEdition == editionManager.getEditions().size() + 1) {
                //Back
            } else {
                view.showMessage("\nThe introduced option is not valid.");
            }
        } else {
            view.showMessage("\nNo editions can be shown as there are no existing editions.");
        }

    }


    private int askForInput (String message, int option) throws FileNotFoundException {
        int index;
        view.showMessage(message);
        System.out.println();
        if (option == 1) {
            view.showList(genericTrialManager.getTrialsNames());
            System.out.println();
            index = genericTrialManager.getTrialsNames().size() + 1;
            view.showMessage("\t " + index + ") Back\n");
        }else if (option == 2){
            view.showList(editionManager.getEditionsNames());
            System.out.println();
            index = editionManager.getEditions().size() + 1;
            view.showMessage("\t " + index + ") Back\n");
        }
        return view.askForInteger("Enter an option: ");
    }

    private void changeStateTrial (LinkedList<String> nameTrials) throws FileNotFoundException {
        boolean contained;
        // Buscamos para cada prueba, si está siendo usada por alguna edición
        for (String nameTrial : nameTrials) {
            contained = false;
            for (int j = 0; j < editionManager.getEditions().size(); j++) {
                if (editionManager.getEditionTrialsNames(j).contains(nameTrial)) {
                    contained = true;
                }
            }
            // En caso de no ser usada, se especifica como "no en uso"
            if (!contained) {
                trialManager.setInUnusedByIndex(trialManager.getIndexByName(nameTrial));
            }
        }
    }
}
