import business.DataSourceOptions;
import business.EditionManager;
import business.ManagersTrials.*;
import business.TeamManager;
import presentation.*;
import presentation.managers.BudgetController;
import presentation.managers.DoctoralController;
import presentation.managers.MasterController;
import presentation.managers.PaperController;

import java.io.IOException;

public class Main {
    public static void main (String[] args) throws IOException {
        /*EditionDAO editionDAO = new EditionDAO();
        TeamDAO teamDAO = new TeamDAO();
        TrialDAO trialDAO = new TrialDAO();

        EditionManager editionManager = new EditionManager(editionDAO);
        TrialManager trialManager = new TrialManager(trialDAO);
        TeamManager teamManager = new TeamManager(teamDAO);
        ViewController viewController = new ViewController();

        GameLogic gameLogic = new GameLogic(viewController);
        CompositorController compositorController = new CompositorController(trialManager, editionManager, viewController, teamManager);
        ConductorController conductorController = new ConductorController(trialManager, editionManager, teamManager, viewController, gameLogic);
        ControllerManager controllerManager = new ControllerManager(compositorController, conductorController, viewController);
        */
        DataSourceOptions dataSourceOptions = null;
        ViewController viewController = new ViewController();
        /**
         * Preguntamos por el tipo de archivo que vamos a usar CSV O JSON
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
        EditionManager editionManager = new EditionManager(dataSourceOptions);
        PaperPublicationManager paperPublicationManager = new PaperPublicationManager(dataSourceOptions);
        MasterManager masterManager = new MasterManager(dataSourceOptions);
        DoctoralManager doctoralManager = new DoctoralManager(dataSourceOptions);
        BudgetManager budgetManager = new BudgetManager(dataSourceOptions);
        GenericTrialManager genericTrialManager = new GenericTrialManager(dataSourceOptions);
        TeamManager teamManager = new TeamManager(dataSourceOptions);

        GameExecutor gameExecutor = new GameExecutor();

        BudgetController budgetController = new BudgetController(budgetManager, viewController);
        DoctoralController doctoralController = new DoctoralController(viewController, doctoralManager);
        MasterController masterController = new MasterController(viewController, masterManager, genericTrialManager);
        PaperController paperController = new PaperController(viewController, paperPublicationManager);

        CompositorController compositorController = new CompositorController(viewController, budgetController, doctoralController, masterController, paperController, editionManager, genericTrialManager, budgetManager, doctoralManager, masterManager, paperPublicationManager);
        ConductorController conductorController = new ConductorController(paperPublicationManager, editionManager, teamManager, viewController, gameExecutor, genericTrialManager);
        /**
         * Le pasamos el tipo al Controller manager
         */
        ControllerManager controllerManager = new ControllerManager(viewController, compositorController, conductorController);
        controllerManager.run(option);
    }
}
