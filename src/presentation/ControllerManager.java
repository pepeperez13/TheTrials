package presentation;


import business.DataSourceOptions;
import business.EditionManager;
import business.ManagersTrials.BudgetManager;
import business.ManagersTrials.DoctoralManager;
import business.ManagersTrials.MasterManager;
import business.ManagersTrials.PaperPublicationManager;
import business.TeamManager;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ControllerManager {
    private final CompositorController compositor;
    private final ConductorController conductor;
    private final ViewController view;
    private EditionManager editionManager;
    private TeamManager teamManager;
    private BudgetManager budgetManager;
    private DoctoralManager doctoralManager;
    private MasterManager masterManager;
    private PaperPublicationManager paperPublicationManager;

    public ControllerManager(CompositorController compositor, ConductorController conductor, ViewController view, PaperPublicationManager paperPublicationManager, EditionManager editionManager, TeamManager teamManager, BudgetManager budgetManager, DoctoralManager doctoralManager, MasterManager masterManager) {
        this.compositor = compositor;
        this.conductor = conductor;
        this.view = view;
        this.paperPublicationManager = paperPublicationManager;
        this.editionManager = editionManager;
        this.teamManager = teamManager;
        this.budgetManager = budgetManager;
        this.doctoralManager = doctoralManager;
        this.masterManager = masterManager;
    }

    public void run () throws IOException {
        String mode;
        String option;
        DataSourceOptions modeOp;
        int finalIndex = 0;

        view.showMessage("\nThe IEEE needs to know where your allegiance lies.\n");
        view.showStartingMenu();
        do {
            option = view.askForString("Pick a faction: ");
            switch (option) {
                case "I":
                    paperPublicationManager = new PaperPublicationManager(DataSourceOptions.CSV);
                    budgetManager = new BudgetManager(DataSourceOptions.CSV);
                    doctoralManager = new DoctoralManager(DataSourceOptions.CSV);
                    masterManager = new MasterManager(DataSourceOptions.CSV);
                    editionManager = new EditionManager(DataSourceOptions.CSV);
                    teamManager = new TeamManager(DataSourceOptions.CSV);
                    view.showMessage("\nLoading data from CSV files...\n");
                    break;
                case "II":
                    paperPublicationManager = new PaperPublicationManager(DataSourceOptions.JSON);
                    budgetManager = new BudgetManager(DataSourceOptions.JSON);
                    doctoralManager = new DoctoralManager(DataSourceOptions.JSON);
                    masterManager = new MasterManager(DataSourceOptions.JSON);
                    editionManager = new EditionManager(DataSourceOptions.JSON);
                    teamManager = new TeamManager(DataSourceOptions.JSON);
                    view.showMessage("\nLoading data from JSON files...\n");
                    break;
                default: view.showMessage("\nInvalid option");
            }
        } while (option != "I" && option != "II");
        view.showLogo();


        do {
            view.showMainMenu();

            mode = view.askForString("\nEnter a role: ");
            switch (mode) {
                case "A" -> executeCompositor();
                case "B" -> finalIndex = executeConductor(finalIndex);
                default -> view.showMessage("\nIncorrect option. Option must be one of the above [A, B]");
            }
        } while (true);
    }

    private void executeCompositor () {
        compositor.run();
    }

    private int executeConductor(int finalIndex) throws IOException {
        return conductor.run(finalIndex);
    }
}
