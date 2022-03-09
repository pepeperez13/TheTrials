package presentation.controllers;

import business.ManagersTrials.GenericTrialManager;
import business.ManagersTrials.MasterManager;
import business.ManagersTrials.TrialTypeOptions;
import business.typeTrials.MasterStudies;
import presentation.ViewController;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MasterController {
    private ViewController view;
    private MasterManager masterManager;
    private GenericTrialManager genericTrialManager;

    public MasterController(ViewController viewController, MasterManager masterManager, GenericTrialManager genericTrialManager) {
        this.view = viewController;
        this.masterManager = masterManager;
        this.genericTrialManager = genericTrialManager;
    }

    public void add() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (checkError(trialName, 1)) {
            String masterName = view.askForString("\nEnter the master's name: ");
            if (checkError(masterName, 2)) {
                int ECTS = view.askForInteger("\nEnter the master's ECTS number: ");
                if (checkError(String.valueOf(ECTS), 3)) {
                    int creditPass = view.askForInteger("\nEnter the credit pass probability: ");
                    if (checkError(String.valueOf(creditPass), 4)){
                        masterManager.addMaster(trialName, masterName, ECTS, creditPass, false);
                        genericTrialManager.addTrial(trialName, TrialTypeOptions.valueOf("MASTER"));
                    }else{
                        view.showMessage("Probability must be in the [0, 100] range.");
                    }
                }else{
                    view.showMessage("Credits number must be in the [60, 120] range.");
                }
            }else{
                view.showMessage("Nom of the master can't be empty");
            }
        }else{
            view.showMessage("Trial name must be unique and not empty.");
        }
    }

    public void showMaster (int numTrial) {
        String name = genericTrialManager.getGenericalTrial(numTrial).getName();
        MasterStudies master = masterManager.getMasterByName(name);
        view.showMessage("\nTrial: " + master.getName() + " (Master studies)");
        view.showMessage("Master: " + master.getNom());
        view.showMessage("ECTS:" + master.getNumberCredits()
                + ", with a " + master.getProbability()
                + "% chance to pass each one");
    }

    private boolean checkError (String aux, int mode) throws FileNotFoundException {
        switch (mode) {
            case 1: // Comprobamos que el nombre no este vacío y que no exista
                if (!aux.isEmpty()) {
                    return !genericTrialManager.checkExistance(aux);
                }else{
                    return false;
                }
            case 2: // Comprobamos que el nombre no este vacío
                return !aux.isEmpty();
            case 3: // Comprobamos que sea uno de los valores posibles
                return Integer.parseInt(aux) >= 60 && Integer.parseInt(aux) <= 120;
            case 4: // Comprobamos que este entre 0 y 100
                return Integer.parseInt(aux) >= 0 && Integer.parseInt(aux) <= 100;
        }
        // Nunca se dará un caso diferente a los del switch
        return true;
    }
}
