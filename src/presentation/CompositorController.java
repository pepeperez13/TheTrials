package presentation;

import business.EditionManager;
import business.ManagersTrials.PaperPublicationManager;
import business.TeamManager;

import java.util.ArrayList;
import java.util.LinkedList;

public class CompositorController {
    private PaperPublicationManager publicationManager;
    private EditionManager editionManager;
    private ViewController view;
    private TeamManager teamManager;

    public CompositorController(PaperPublicationManager publicationManager, EditionManager editionManager, ViewController view, TeamManager teamManager) {
        this.publicationManager = publicationManager;
        this.editionManager = editionManager;
        this.view = view;
        this.teamManager = teamManager;
    }

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

    private void manageTrials () {
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

    private void addTrial () {
        view.showTypesTrials();
        int type_trial = view.askForInteger("Enter the trial's type: ");

        switch (type_trial) {
            case 1:
                managePaperPublication();
                break;
            case 2:
                manageMasterStudies();
                break;
            case 3:
                manageDoctoralThesis();
                break;
            case 4:
                manageBudgetRequest();
                break;
            default:
                view.showMessage("\nInvalid option");
        }
    }

    private void manageBudgetRequest() {
    }

    private void manageDoctoralThesis() {
    }

    private void manageMasterStudies() {
    }

    private void managePaperPublication() {
    }

    private void listTrials() {
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
