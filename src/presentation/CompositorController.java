package presentation;

import business.Edition;
import business.EditionManager;
import business.ManagersTrials.*;
import presentation.controllers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Clase que gestiona la ejecución del menú de gestión (Compositor)
 * @author Abraham Cedeño
 * @author José Pérez
 */
public class CompositorController {
    private ViewController view;
    private BudgetController budgetController;
    private DoctoralController doctoralController;
    private MasterController masterController;
    private PaperController paperController;
    private EditionManager editionManager;
    private GenericTrialManager genericTrialManager;
    private BudgetManager budgetManager;
    private DoctoralManager doctoralManager;
    private MasterManager masterManager;
    private PaperPublicationManager paperManager;

    /**
     * Construye un nuevo CompositorController, con todos las clases que este necesita
     * @param view ViewController que gestiona la interacción por pantall con el usuario
     * @param budgetController BudgetController que gestiona las interacciones del Budget
     * @param doctoralController DoctoralController que gestiona las interacciones del Doctoral
     * @param masterController MasterController que gestiona las interacciones del Master
     * @param paperController PaperController que gestiona las interacciones del Paper
     * @param editionManager EditionManager que gestiona las ediciones
     * @param genericTrialManager GenericTrialManager que gestiona las pruebas genericas
     * @param budgetManager BudgetManager que gestiona los Budget
     * @param doctoralManager DoctoralManager que gestiona las pruebas Doctoral
     * @param masterManager MasterManager que gestiona las pruebas Master
     * @param paperManager PaperManager que gestiona las pruebas Paper
     */
    public CompositorController(ViewController view, BudgetController budgetController, DoctoralController doctoralController, MasterController masterController, PaperController paperController, EditionManager editionManager, GenericTrialManager genericTrialManager, BudgetManager budgetManager, DoctoralManager doctoralManager, MasterManager masterManager, PaperPublicationManager paperManager) {
        this.view = view;
        this.budgetController = budgetController;
        this.doctoralController = doctoralController;
        this.masterController = masterController;
        this.paperController = paperController;
        this.editionManager = editionManager;
        this.genericTrialManager = genericTrialManager;
        this.budgetManager = budgetManager;
        this.doctoralManager = doctoralManager;
        this.masterManager = masterManager;
        this.paperManager = paperManager;
    }

    /**
     * Método principal en forma de bucle, que se encarga de ejecutar los métodos hasta que lo pida el usuario
     */
    public void run () {
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

    /**
     * Se encarga de llamar a los métodos que gestionan las diferentes acciones relacionadas con la gestión de pruebas
     */
    private void manageTrials () {
        String option;
        do {
            view.showSubMenuTrials();
            option = view.askForString("\nEnter an option: ");
            switch (option) {
                case "a":
                    addTrial();
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

    /**
     * Método privado que pide los datos de una nueva prueba a registrar, revisando la validez de los datos antes
     * de cada petición. Si los datos son inválidos, se para la ejecución
     */
    private void addTrial () {
        view.showTypesTrials();
        int type_trial = view.askForInteger("Enter the trial's type: ");

        switch (type_trial) {
            case 1 -> paperController.add();
            case 2 -> masterController.add();
            case 3 -> doctoralController.add();
            case 4 -> budgetController.add();
            default -> view.showMessage("\nInvalid option");
        }
    }


    /**
     * Se encarga de listar los métodos actualmente guardados, si es que los hay
     */
    private void listTrials () {
        if (!genericTrialManager.getTrials().isEmpty()) {
            int numTrial = askForInput("\nHere are the current trials, do you want to see more details or go back?", 1);
            if (numTrial > 0 && numTrial <= genericTrialManager.getTrials().size()) {
                switch (genericTrialManager.getTrialTypeByIndex(numTrial)) {
                    case DOCTORAL -> doctoralController.showDoctoral(numTrial);
                    case BUDGET -> budgetController.showBudget(numTrial);
                    case PAPER -> paperController.showPaper(numTrial);
                    case MASTER -> masterController.showMaster(numTrial);
                }
            }
        }else{
            view.showMessage("\nNo trials can be show as there are no existing trials");
        }
    }

    /**
     * Se encarga de eliminar una prueba concreta, según los detalles que indique el usuario. Comprueba que el nombre
     * coincida con su número y que dicha prueba no esté en uso por ninguna edición
     */
    private void deleteTrial () {
        if (!genericTrialManager.getTrials().isEmpty()) {
            int numTrial = askForInput("\nWich trial do you want to delete?", 1);
            if (numTrial > 0 && numTrial <= genericTrialManager.getTrials().size()) {
                String confirmationName = view.askForString("\nEnter the trial's name for confirmation: ");

                if (genericTrialManager.getGenericalTrial(numTrial).getName().equals(confirmationName)) {
                    switch (genericTrialManager.getGenericalTrial(numTrial).getType()) {
                        case MASTER -> {
                            if (!masterManager.isInUse(confirmationName)) {
                                genericTrialManager.deleteByname(confirmationName);
                                masterManager.deleteMaster(masterManager.getIndexByName(confirmationName));
                            } else {
                                view.showMessage("\nThe trial is in use and can not be deleted.");
                            }
                        }
                        case PAPER -> {
                            if (!paperManager.isInUse(confirmationName)) {
                                genericTrialManager.deleteByname(confirmationName);
                                paperManager.deletePaper(paperManager.getIndexByName(confirmationName));
                            } else {
                                view.showMessage("\nThe trial is in use and can not be deleted.");
                            }
                        }
                        case BUDGET -> {
                            if (!budgetManager.isInUse(confirmationName)) {
                                genericTrialManager.deleteByname(confirmationName);
                                budgetManager.deleteBudget(budgetManager.getIndexByName(confirmationName));
                            } else {
                                view.showMessage("\nThe trial is in use and can not be deleted.");
                            }
                        }
                        case DOCTORAL -> {
                            if (!doctoralManager.isInUse(confirmationName)) {
                                genericTrialManager.deleteByname(confirmationName);
                                doctoralManager.deleteMaster(doctoralManager.getIndexByName(confirmationName));
                            } else {
                                view.showMessage("\nThe trial is in use and can not be deleted.");
                            }
                        }
                    }
                } else {
                    view.showMessage("\nThe name of the introduced trial does not match the previously indicated trial.");
                }
            } else {
                view.showMessage("\nInvalid option.");
            }
        } else {
            view.showMessage("\nNo trials can be deleted as there are no existing trials.");
        }
    }

    /**
     * Se encarga de llamar a los métodos que gestionan las diferentes acciones relacionadas con la gestión de ediciones
     */
    private void manageEditions () {

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

    /**
     * Se encarga de añadir una nueva edición, pidiendo los datos al usuario. La petición de datos se repetirá hasta
     * que los datos introducidos sean válidos
     */
    private void addEdition () {
        int year, numPlayers, numTrials;
        boolean repeatYear = false;
        if (!(genericTrialManager.getTrials().size() == 0)) {
            year = view.askForInteger("\nEnter the edition's year: ");
            for (Edition edition:editionManager.getEditions()) {
                if (edition.getYear() == year) {
                    repeatYear = true;
                }
            }
            if (!repeatYear) {
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
                view.showList(genericTrialManager.getTrialsNames());

                // Guardamos los indices de las pruebas que se quieren guardar en la edición
                ArrayList<Integer> trialsIndexes = new ArrayList<>();
                view.showMessage("");
                int index;
                for (int i = 0; i < numTrials; i++) {
                    do {
                        index = view.askForInteger("Pick a trial (" + (i + 1) + "/" + numTrials + "): ");
                        if (index > genericTrialManager.getTrials().size()) {
                            view.showMessage("\nIncorrect option");
                        }
                    } while (index > genericTrialManager.getTrials().size());
                    trialsIndexes.add(index - 1);
                }

                // Activamos las pruebas introducidas como en uso
                setTrialsInUse(trialsIndexes);

                // Obtenemos los nombres de las pruebas con dichos índices
                String[] names = genericTrialManager.getTrialsNamesByIndexes(trialsIndexes);  // Array de strings donde se guardaran los nombres que necesitemos

                if (editionManager.addEdition(year, numPlayers, numTrials, names)) {
                    view.showMessage("\nThe edition was created successfully!");
                }
            } else {
                view.showMessage("\nThere is already an edition for this year");
            }
        } else {
            view.showMessage("\nNo editions can be created as there are no existing trials.");
        }
    }

    /**
     * Método privado que coloca las pruebas que se hayan seleccionado para una edición como "en uso"
     * @param trialsIndexes indices de las pruebas que deben colocarse en uso
     */
    private void setTrialsInUse (ArrayList<Integer> trialsIndexes) {

        for (Integer trialsIndex : trialsIndexes) {
            // Según el tipo de prueba que se haya utilizado
            switch (genericTrialManager.getTrialTypeByIndex(trialsIndex + 1)) {
                case MASTER -> masterManager.setInUseByName(genericTrialManager.getGenericalTrial(trialsIndex + 1).getName());
                case PAPER -> paperManager.setInUseByName(genericTrialManager.getGenericalTrial(trialsIndex + 1).getName());
                case BUDGET -> budgetManager.setInUseByName(genericTrialManager.getGenericalTrial(trialsIndex + 1).getName());
                case DOCTORAL -> doctoralManager.setInUseByName(genericTrialManager.getGenericalTrial(trialsIndex + 1).getName());
            }
        }

    }

    /**
     * Se encarga de eliminar la edición de un año concreto
     */
    private void deleteEdition ()  {
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
            } else {
                if (numEdition != editionManager.getEditions().size() + 1) {
                    view.showMessage("\nThe introduced edition is not valid");
                }
            }
        } else {
            view.showMessage("\nNo editions can be deleted as there are no existing editions.");
        }

    }

    /**
     * Permite duplicar una edición ya existente. Mantiene las mismas pruebas y el mismo nuevo de jugadores, cambiando
     * únicamente el año de la misma
     */
    private void duplicateEdition ()  {
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

            } else {
                if (numEdition != editionManager.getEditions().size() + 1) {
                    view.showMessage("\nThe introduced edition is not valid.");
                }
            }
        }
        else {
            view.showMessage("\nNo editions can be duplicated as there are no existing editions.");
        }

    }

    /**
     * Método que lista las ediciones actualmente guardadas, siempre y cuando las haya. También permite mostrar info
     * concreta de una edición
     */
    private void listEditions ()  {
        if (!editionManager.getEditions().isEmpty()) {
            int numEdition = askForInput("\nHere are the current editions, do you want to see more details or go back?", 2);

            if (numEdition > 0 && numEdition <= editionManager.getEditions().size()) {
                view.showMessage("\nYear: " + editionManager.getEditionByIndex(numEdition).getYear());
                view.showMessage("Players: " + editionManager.getEditionByIndex(numEdition).getNumPlayers());
                view.showMessage("Trials: ");
                view.showListGuion(editionManager.getEditionTrialsNames(numEdition-1));
            } else {
                if (numEdition != editionManager.getEditions().size() + 1) {
                    view.showMessage("\nThe introduced option is not valid.");
                }
            }
        } else {
            view.showMessage("\nNo editions can be shown as there are no existing editions.");
        }

    }

    /**
     * Muestra una lista (pruebas o ediciones, según el parámetro opción) y pide si se va a querer mostrar información
     * detallada de algún elemento de dicha lista
     * @param message Mensaje a mostrar
     * @param option Opción binaria (1 o 2) que va a indicar al método que lista debe mostrar
     * @return Entero que permitirá saber si se debe mostrar más información o no
     */
    private int askForInput (String message, int option) {
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

    /**
     * Cambiará el estado de todas aquellas pruebas que ya no estén siendo usadas por ninguna edición
     * @param nameTrials Lista con los nombres de las pruebas guardadas
     */
    private void changeStateTrial (LinkedList<String> nameTrials) {
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
                switch (genericTrialManager.getTrialTypeByName(nameTrial)) {
                    case MASTER -> masterManager.setInNotUseByName(nameTrial);
                    case PAPER -> paperManager.setInNotUseByName(nameTrial);
                    case BUDGET -> budgetManager.setInNotUseByName(nameTrial);
                    case DOCTORAL -> doctoralManager.setInNotUseByName(nameTrial);
                }
            }
        }
    }
}
