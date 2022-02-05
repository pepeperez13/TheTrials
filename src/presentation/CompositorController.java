package presentation;

import presentation.managers.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CompositorController {
    private ViewController view;
    private BudgetController budgetController;
    private DoctoralController doctoralController;
    private EditionController editionController;
    private MasterController masterController;
    private PaperController paperController;
    private TeamController teamController;

    public CompositorController(ViewController view, BudgetController budgetController, DoctoralController doctoralController, EditionController editionController, MasterController masterController, PaperController paperController, TeamController teamController) {
        this.view = view;
        this.budgetController = budgetController;
        this.doctoralController = doctoralController;
        this.editionController = editionController;
        this.masterController = masterController;
        this.paperController = paperController;
        this.teamController = teamController;
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

    private void listTrials() throws FileNotFoundException {
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
