import business.EditionManager;
import business.ManagersTrials.*;
import presentation.CompositorController;
import presentation.ControllerManager;
import presentation.ViewController;
import presentation.managers.BudgetController;
import presentation.managers.DoctoralController;
import presentation.managers.MasterController;
import presentation.managers.PaperController;

import java.io.IOException;

public class Main {
    public static void main (String[] args) throws IOException {
        ViewController viewController = new ViewController();
        /**
         * Preguntamos por el tipo de archivo que vamos a usar CSV O JSON
         */
        String option;
        viewController.showMessage("\nThe IEEE needs to know where your allegiance lies.\n");
        viewController.showStartingMenu();
        option = viewController.askForString("Pick a faction: ");
        /**
         * Le pasamos el tipo al Controller manager
         */
        ControllerManager controllerManager = new ControllerManager(viewController);
        controllerManager.run(option);
    }
}
