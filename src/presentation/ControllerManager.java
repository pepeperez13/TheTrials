package presentation;


import java.io.IOException;

public class ControllerManager {

    private final ViewController viewController;
    private final CompositorController compositorController;
    private final ConductorController conductorController;

    public ControllerManager(ViewController viewController, CompositorController compositorController, ConductorController conductorController) {
        this.viewController = viewController;
        this.compositorController = compositorController;
        this.conductorController = conductorController;
    }

    public void run (String option) throws IOException {
        do {
            switch (option) {
                case "I" -> {
                    viewController.showMessage("\nLoading data from CSV files...\n");
                    mainLoop();
                }
                case "II" -> {
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
        } while (true);
    }

    private void executeCompositor () throws IOException {
        compositorController.run();
    }

    private int executeConductor(int finalIndex) throws IOException {
        return conductorController.run(finalIndex);
    }
}