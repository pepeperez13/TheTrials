package presentation;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CompositorController {
    private ViewController view;

    public CompositorController(ViewController view) {
        this.view = view;
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
                //Añadir trial del tipo paper
                addPaperPublication();
                break;
            case 2:
                addMasterStudies();
                break;
            case 3:
                addDoctoralThesis();
                break;
            case 4:
                addBudgetRequest();
                break;
            default:
                view.showMessage("\nInvalid option");
        }
    }

    private void addPaperPublication() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (checkError (trialName, 1)) {
            String journalName = view.askForString("Enter the journal's name: ");
            if (checkError(journalName, 2)) {
                String quartile = view.askForString("Enter the journal's quartile: ");
                if (checkError(quartile, 3)) {
                    int accepted = view.askForInteger("Enter the acceptance probability: ");
                    if (checkError(String.valueOf(accepted), 4)) {
                        int revision = view.askForInteger("Enter the revision probability: ");
                        if (checkError(String.valueOf(revision), 5)) {
                            int rejection = view.askForInteger("Enter the rejection probability: ");
                            if (checkError(String.valueOf(rejection), 5)) {
                                publicationManager.addTrial(trialName, journalName, quartile, accepted, revision, rejection, false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void addMasterStudies() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (checkError(trialName, 1)) {
            String masterName = view.askForString("\nEnter the master's name: ");
            if (checkError(masterName, 2)) {
                int ECTS = view.askForInteger("\nEnter the master's ECTS number: ");
                if (checkError(String.valueOf(ECTS), 3)) {
                    int creditPass = view.askForInteger("\nEnter the credit pass probability: ");
                    if (checkError(String.valueOf(creditPass), 4)){
                        masterManager.addMasterManager(trialName, masterName, ECTS, creditPass);
                    }
                }
            }
        }
    }

    private void addDoctoralThesis() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (checkError(trialName, 1)) {
            String thesis = view.askForString("\nEnter the thesis field of study: ");
            if (checkError(thesis, 2)) {
                int difficulty = view.askForInteger("\nEnter the defense difficulty: ");
                if (checkError(String.valueOf(difficulty), 3)) {
                    doctoralManager.addDoctoralThesis(trialName, thesis, difficulty);
                }
            }
        }
    }

    private void addBudgetRequest() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (checkError(trialName, 1)) {
            String entityName = view.askForString("\nEnter the entity's name: ");
            if (checkError(entityName, 2)) {
                int budget = view.askForInteger("\nEnter the budget amount: ");
                if (checkError(String.valueOf(budget), 3)) {
                    budgetManager.addBudget(trialName, entityName, budget);
                }
            }
        }
    }

    private boolean checkError (String aux, int mode) throws FileNotFoundException {
        switch (mode) {
            case 1: // Comprobamos que el nombre no este vacío y que no exista
                if (!aux.isEmpty()) {
                    return !publicationManager.checkExistence(aux);
                }else{
                    return false;
                }
            case 2: // Comprobamos que el nombre no este vacío
                return !aux.isEmpty();
            case 3: // Comprobamos que sea uno de los valores posibles
                return aux.equals("Q1") || aux.equals("Q2") || aux.equals("Q3") || aux.equals("Q4");
            case 4, 5, 6: // Comprobamos que este entre 0 y 100
                return Integer.parseInt(aux) >= 0 && Integer.parseInt(aux) <= 100;
        }
        return true;
    }

    private void listTrials() throws FileNotFoundException {
        if (!publicationManager.getTrials().isEmpty() || !masterManager.getMasters().isEmpty() ||
                !doctoralManager.getDoctoralThesis().isEmpty() || !budgetManager.getBudget().isEmpty()) {
            int numTrial = view.askForInteger("\nHere are the current trials, do you want to see more details or go back?");

        }else{
            view.showMessage("\nNo trials can be listed as there are no existing trials.");
        }
    }

    private void deleteTrial() {
    }

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

    private void deleteEdition() {
    }

    private void duplicateEdition() {
    }

    private void listEditions() {
    }

    private void addEdition() {
    }
}
