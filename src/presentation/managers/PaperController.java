package presentation.managers;

import business.ManagersTrials.GenericTrialManager;
import business.ManagersTrials.PaperPublicationManager;
import business.ManagersTrials.TrialTypeOptions;
import business.typeTrials.PaperPublication;
import presentation.ViewController;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PaperController {
    private ViewController view;
    private PaperPublicationManager paperPublicationManager;
    private GenericTrialManager genericTrialManager;
    public PaperController(ViewController view, PaperPublicationManager paperPublicationManager, GenericTrialManager genericTrialManager) {
        this.view = view;
        this.paperPublicationManager = paperPublicationManager;
        this.genericTrialManager = genericTrialManager;
    }

    public void add() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (checkError (trialName, 1)) {
            String journalName = view.askForString("Enter the journal's name: ");
            if (checkError(journalName, 2)) {
                String quartile = view.askForString("Enter the journal's quartile: ");
                if (checkError(quartile, 3)) {
                    int accepted = view.askForInteger("Enter the acceptance probability: ");
                    if (checkError(String.valueOf(accepted), 4)) {
                        int revision = view.askForInteger("Enter the revision probability: ");
                        if (checkError(String.valueOf(revision), 5)) {
                            int rejection = view.askForInteger("Enter the rejection probability: ");
                            if ((accepted + revision + rejection) == 100 && checkError(String.valueOf(rejection), 6)) {
                                paperPublicationManager.addPaper(trialName, journalName, quartile, accepted, revision, rejection, false);
                                genericTrialManager.addTrial(trialName, TrialTypeOptions.valueOf("PAPER"));
                            }else{
                                view.showMessage("Value must be between 0 and 100 and the sum of all percenatges can't be over 100.");
                            }
                        }else{
                            view.showMessage("Value must be between 0 and 100 and the sum of all percenatges can't be over 100.");
                        }
                    }else{
                        view.showMessage("Value must be between 0 and 100 and the sum of all percenatges can't be over 100.");
                    }
                }else{
                    view.showMessage("\nQuartile must be one of the following values [Q1, Q2, Q3, Q4].");
                }
            }else{
                view.showMessage("\nMagazine name must not empty.");
            }
        }else{
            view.showMessage("\nTrial name must be unique and not empty.");
        }
    }

    /**
     * Muestra la informaci??n detallada de una prueba en concreto
     * @param numTrial Indice de la prueba concreta sobre la que se quiera obtener informaci??n
     */
    public void showPaper(int numTrial) throws FileNotFoundException {
        String name = genericTrialManager.getGenericalTrial(numTrial).getName();
        PaperPublication paper = paperPublicationManager.getPaperByName(name);
        view.showMessage("\nTrial: " + paper.getArticleName() + " (Paper publication)");
        view.showMessage("Journal: " + paper.getMagazineName() + " (" + paper.getQuartile() + ")");
        view.showMessage("Chances: " + paper.getAcceptedProbability() + "% acceptance, " +
                paper.getRevisedProbability() + "% revision, " +
                paper.getRejectedProbability() + "% rejection");
    }

    private boolean checkError (String aux, int mode) throws FileNotFoundException {
        switch (mode) {
            case 1: // Comprobamos que el nombre no este vac??o y que no exista
                if (!aux.isEmpty()) {
                    return !paperPublicationManager.checkExistence(aux);
                }else{
                    return false;
                }
            case 2: // Comprobamos que el nombre no este vac??o
                return !aux.isEmpty();
            case 3: // Comprobamos que sea uno de los valores posibles
                return aux.equals("Q1") || aux.equals("Q2") || aux.equals("Q3") || aux.equals("Q4");
            case 4, 5, 6: // Comprobamos que este entre 0 y 100
                return Integer.parseInt(aux) >= 0 && Integer.parseInt(aux) <= 100;
        }
        // Nunca se dar?? un caso diferente a los del switch
        return true;
    }
}
