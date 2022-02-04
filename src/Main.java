import presentation.ControllerManager;
import presentation.ViewController;

import java.io.IOException;

public class Main {
    public static void main (String[] args) throws IOException {
        /**
         * Preguntamos por el tipo de archivo que vamos a usar CSV O JSON
         */
        String option;
        ViewController viewController = new ViewController();
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
