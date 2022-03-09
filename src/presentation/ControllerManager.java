package presentation;


import business.DataSourceOptions;
import business.EditionManager;
import business.ManagersTrials.*;
import business.TeamManager;

import java.io.IOException;

public class ControllerManager {

    private final ViewController viewController;
    private CompositorController compositorController;
    private ConductorController conductorController;
    private GenericTrialManager genericTrialManager;

    public ControllerManager(ViewController viewController, CompositorController compositorController, ConductorController conductorController, GenericTrialManager genericTrialManager) {
        this.viewController = viewController;
        this.compositorController = compositorController;
        this.conductorController = conductorController;
        this.genericTrialManager = genericTrialManager;
    }

    public void run (String option) throws IOException {
        do {
            switch (option) {
                case "I" -> {
                    new PaperPublicationManager(DataSourceOptions.CSV, genericTrialManager);
                    new BudgetManager(DataSourceOptions.CSV, genericTrialManager);
                    new DoctoralManager(DataSourceOptions.CSV, genericTrialManager);
                    new MasterManager(DataSourceOptions.CSV, genericTrialManager);
                    new EditionManager(DataSourceOptions.CSV);
                    new TeamManager(DataSourceOptions.CSV);
                    viewController.showMessage("\nLoading data from CSV files...\n");
                    mainLoop();
                }
                case "II" -> {
                    new PaperPublicationManager(DataSourceOptions.JSON, genericTrialManager);
                    new BudgetManager(DataSourceOptions.JSON, genericTrialManager);
                    new DoctoralManager(DataSourceOptions.JSON, genericTrialManager);
                    new MasterManager(DataSourceOptions.JSON, genericTrialManager);
                    new EditionManager(DataSourceOptions.JSON);
                    new TeamManager(DataSourceOptions.JSON);
                    viewController.showMessage("\nLoading data from JSON files...\n");
                    mainLoop();
                }
                default -> viewController.showMessage("\nInvalid option");
            }
        } while (!option.equals("I") && !option.equals("II"));
    }

    private void mainLoop() throws IOException {
        viewController.showLogo();
        int finalIndex = 0;
        do {
            viewController.showMainMenu();
            String mode = viewController.askForString("\nEnter a role: ");

            switch (mode) {
                case "A" -> executeCompositor();
                case "B" -> finalIndex = executeConductor(finalIndex);
                default -> viewController.showMessage("\nIncorrect option. Option must be one of the above [A, B]");
            }
            // EL programa sólo acabará cuando se hayan ejecutado todas las pruebas de la edición actual
        } while (finalIndex < conductorController.getNumberOfTrials());
    }

    private void executeCompositor () throws IOException {
        compositorController.run();
    }

    private int executeConductor(int finalIndex) throws IOException {
        return conductorController.run(finalIndex);
    }
}