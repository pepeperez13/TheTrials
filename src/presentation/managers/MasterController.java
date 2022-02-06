package presentation.managers;

import business.ManagersTrials.MasterManager;
import presentation.ViewController;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MasterController {
    private ViewController viewController;
    private MasterManager masterManager;

    public MasterController(ViewController viewController, MasterManager masterManager) {
        this.viewController = viewController;
        this.masterManager = masterManager;
    }

    public void add() throws IOException {
        String trialName = viewController.askForString("\nEnter the trial's name: ");
        if (checkError(trialName, 1)) {
            String masterName = viewController.askForString("\nEnter the master's name: ");
            if (checkError(masterName, 2)) {
                int ECTS = viewController.askForInteger("\nEnter the master's ECTS number: ");
                if (checkError(String.valueOf(ECTS), 3)) {
                    int creditPass = viewController.askForInteger("\nEnter the credit pass probability: ");
                    if (checkError(String.valueOf(creditPass), 4)){
                        masterManager.addMasterManager(trialName, masterName, ECTS, creditPass);
                    }
                }
            }
        }
    }

    private boolean checkError (String aux, int mode) throws FileNotFoundException {
        switch (mode) {
            case 1: // Comprobamos que el nombre no este vacío y que no exista
                /*if (!aux.isEmpty()) {
                    return !masterManager.checkExistence(aux);
                }else{
                    return false;
                }*/
            case 2: // Comprobamos que el nombre no este vacío
                return !aux.isEmpty();
            case 3: // Comprobamos que sea uno de los valores posibles
                return Integer.parseInt(aux) >= 60 && Integer.parseInt(aux) <= 120;
            case 4: // Comprobamos que este entre 0 y 100
                return Integer.parseInt(aux) >= 0 && Integer.parseInt(aux) <= 100;
        }
        return true;
    }
}
