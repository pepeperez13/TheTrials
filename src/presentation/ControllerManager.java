package presentation;


import business.DataSourceOptions;
import business.EditionManager;
import business.ManagersTrials.BudgetManager;
import business.ManagersTrials.DoctoralManager;
import business.ManagersTrials.MasterManager;
import business.ManagersTrials.PaperPublicationManager;
import business.TeamManager;

import java.io.IOException;

public class ControllerManager {
    /*private final CompositorController compositor;
    private final ConductorController conductor;
    private final ViewController view;
    private EditionController editionManager;
    private TeamController teamManager;
    private BudgetController budgetManager;
    private DoctoralController doctoralManager;
    private MasterController masterManager;
    private PaperPublicationManager paperPublicationManager;

    public ControllerManager(CompositorController compositor, ConductorController conductor, ViewController view, PaperPublicationManager paperPublicationManager, EditionController editionManager, TeamController teamManager, BudgetController budgetManager, DoctoralController doctoralManager, MasterController masterManager) {
        this.compositor = compositor;
        this.conductor = conductor;
        this.view = view;
        this.paperPublicationManager = paperPublicationManager;
        this.editionManager = editionManager;
        this.teamManager = teamManager;
        this.budgetManager = budgetManager;
        this.doctoralManager = doctoralManager;
        this.masterManager = masterManager;
    }*/
    private final ViewController viewController;
    private PaperPublicationManager paperPublicationManager;
    private BudgetManager budgetManager;
    private DoctoralManager doctoralManager;
    private MasterManager masterManager;
    private EditionManager editionManager;
    private TeamManager teamManager;
    private CompositorController compositorController;
    private ConductorController conductorController;

    public ControllerManager (ViewController viewController) {
        this.viewController = viewController;
    }

    public void run (String option) throws IOException {
        do {
            switch (option) {
                case "I" -> {
                    paperPublicationManager = new PaperPublicationManager(DataSourceOptions.CSV);
                    budgetManager = new BudgetManager(DataSourceOptions.CSV);
                    doctoralManager = new DoctoralManager(DataSourceOptions.CSV);
                    masterManager = new MasterManager(DataSourceOptions.CSV);
                    editionManager = new EditionManager(DataSourceOptions.CSV);
                    teamManager = new TeamManager(DataSourceOptions.CSV);
                    viewController.showMessage("\nLoading data from CSV files...\n");
                    mainLoop();
                }
                case "II" -> {
                    paperPublicationManager = new PaperPublicationManager(DataSourceOptions.JSON);
                    budgetManager = new BudgetManager(DataSourceOptions.JSON);
                    doctoralManager = new DoctoralManager(DataSourceOptions.JSON);
                    masterManager = new MasterManager(DataSourceOptions.JSON);
                    editionManager = new EditionManager(DataSourceOptions.JSON);
                    teamManager = new TeamManager(DataSourceOptions.JSON);
                    viewController.showMessage("\nLoading data from JSON files...\n");
                    mainLoop();
                }
                default -> viewController.showMessage("\nInvalid option");
            }
        } while (!option.equals("I") && !option.equals("II"));
        viewController.showLogo();
    }

    private void mainLoop() throws IOException {
        do {
            viewController.showMainMenu();
            String mode = viewController.askForString("\nEnter a role: ");
            int finalIndex = 0;
            switch (mode) {
                case "A" -> executeCompositor();
                case "B" -> finalIndex = executeConductor(finalIndex);
                default -> viewController.showMessage("\nIncorrect option. Option must be one of the above [A, B]");
            }
        } while (true);
    }

    private void executeCompositor () throws IOException {
        compositorController.run();
    }

    private int executeConductor(int finalIndex) throws IOException {
        return conductorController.run(finalIndex);
    }
}
