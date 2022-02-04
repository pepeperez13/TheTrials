package presentation;

import business.EditionManager;
import business.ManagersTrials.BudgetManager;
import business.ManagersTrials.DoctoralManager;
import business.ManagersTrials.MasterManager;
import business.ManagersTrials.PaperPublicationManager;
import business.TeamManager;

import javax.print.Doc;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class CompositorController {
    private PaperPublicationManager publicationManager;
    private MasterManager masterManager;
    private EditionManager editionManager;
    private ViewController view;
    private TeamManager teamManager;
    private DoctoralManager doctoralManager;
    private BudgetManager budgetManager;

    public CompositorController(PaperPublicationManager publicationManager, EditionManager editionManager, ViewController view,
                                TeamManager teamManager, MasterManager masterManager, DoctoralManager doctoralManager, BudgetManager budgetManager) {
        this.publicationManager = publicationManager;
        this.editionManager = editionManager;
        this.view = view;
        this.teamManager = teamManager;
        this.masterManager = masterManager;
        this.doctoralManager = doctoralManager;
        this.budgetManager = budgetManager;
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
        if (compruebaError (trialName)) {
            String journalName = view.askForString("Enter the journal's name: ");
            if (compruebaError(journalName)) {
                String quartile = view.askForString("Enter the journal's quartile: ");
                if (compruebaError(quartile)) {
                    int accepted = view.askForInteger("Enter the acceptance probability: ");
                    if (compruebaError(String.valueOf(accepted))) {
                        int revision = view.askForInteger("Enter the revision probability: ");
                        if (compruebaError(String.valueOf(revision))) {
                            int rejection = view.askForInteger("Enter the rejection probability: ");
                            if (compruebaError(String.valueOf(rejection))) {
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
        if (compruebaError(trialName)) {
            String masterName = view.askForString("\nEnter the master's name: ");
            if (compruebaError(masterName)) {
                int ECTS = view.askForInteger("\nEnter the master's ECTS number: ");
                if (compruebaError(String.valueOf(ECTS))) {
                    int creditPass = view.askForInteger("\nEnter the credit pass probability: ");
                    if (compruebaError(String.valueOf(creditPass))){
                        masterManager.addMasterManager(trialName, masterName, ECTS, creditPass);
                    }
                }
            }
        }
    }

    private void addDoctoralThesis() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (compruebaError(trialName)) {
            String thesis = view.askForString("\nEnter the thesis field of study: ");
            if (compruebaError(thesis)) {
                int difficulty = view.askForInteger("\nEnter the defense difficulty: ");
                if (compruebaError(String.valueOf(difficulty))) {
                    doctoralManager.addDoctoralThesis(trialName, thesis, difficulty);
                }
            }
        }
    }

    private void addBudgetRequest() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (compruebaError(trialName)) {
            String entityName = view.askForString("\nEnter the entity's name: ");
            if (compruebaError(entityName)) {
                int budget = view.askForInteger("\nEnter the budget amount: ");
                if (compruebaError(String.valueOf(budget))) {
                    budgetManager.addBudget(trialName, entityName, budget);
                }
            }
        }
    }

    private boolean compruebaError (String aux) {
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
