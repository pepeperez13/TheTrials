import business.DataSourceOptions;
import business.EditionManager;
import business.ManagersTrials.*;
import business.TeamManager;
import presentation.*;
import presentation.controllers.BudgetController;
import presentation.controllers.DoctoralController;
import presentation.controllers.MasterController;
import presentation.controllers.PaperController;

import java.io.IOException;

public class Main {
    public static void main (String[] args) throws IOException {
        DataSourceOptions dataSourceOptions = null;
        ViewController viewController = new ViewController();
        /**
         * Preguntamos por el tipo de archivo que vamos a usar CSV O JSON
         * Debemos hacer esta parte aqui en el main, ya que si no no podríamos pasar como parametro el dataSourceOptions
         * a todas las clases que lo necesitan
         */
        String option;
        viewController.showMessage("\nThe IEEE needs to know where your allegiance lies.\n");
        viewController.showStartingMenu();

        option = viewController.askForString("Pick a faction: ");
        if (option.equals("I")) {
            dataSourceOptions = DataSourceOptions.CSV;
        } else if (option.equals("II")) {
            dataSourceOptions = DataSourceOptions.JSON;
        }

        /**
         * Constructores
         */
        GenericTrialManager genericTrialManager = new GenericTrialManager(dataSourceOptions);
        BudgetManager budgetManager = new BudgetManager(dataSourceOptions, genericTrialManager);
        PaperPublicationManager paperManager = new PaperPublicationManager(dataSourceOptions, genericTrialManager);
        MasterManager masterManager = new MasterManager(dataSourceOptions, genericTrialManager);
        DoctoralManager doctoralManager = new DoctoralManager(dataSourceOptions, genericTrialManager);
        EditionManager editionManager = new EditionManager(dataSourceOptions);

        TeamManager teamManager = new TeamManager(dataSourceOptions);

        GameExecutor gameExecutor = new GameExecutor(teamManager, budgetManager, paperManager, masterManager, doctoralManager, viewController);

        BudgetController budgetController = new BudgetController(budgetManager, viewController, genericTrialManager);
        DoctoralController doctoralController = new DoctoralController(viewController, doctoralManager, genericTrialManager);
        MasterController masterController = new MasterController(viewController, masterManager, genericTrialManager);
        PaperController paperController = new PaperController(viewController, paperManager, genericTrialManager);

        CompositorController compositorController = new CompositorController(viewController, budgetController, doctoralController, masterController, paperController, editionManager, genericTrialManager, budgetManager, doctoralManager, masterManager, paperManager);
        ConductorController conductorController = new ConductorController(editionManager, teamManager, viewController, gameExecutor, genericTrialManager);

        /**
         * Le pasamos el tipo al Controller manager
         */
        ControllerManager controllerManager = new ControllerManager(viewController, compositorController, conductorController);
        controllerManager.run(option);
    }
}
