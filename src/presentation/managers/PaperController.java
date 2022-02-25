package presentation.managers;

import business.ManagersTrials.GenericTrialManager;
import business.ManagersTrials.PaperPublicationManager;
import business.typeTrials.PaperPublication;
import presentation.ViewController;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PaperController {
    private ViewController view;
    private PaperPublicationManager paperPublicationManager;
    private GenericTrialManager genericTrialManager;
    public PaperController(ViewController view, PaperPublicationManager paperPublicationManager) {
        this.view = view;
        this.paperPublicationManager = paperPublicationManager;
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
                            if (checkError(String.valueOf(rejection), 5)) {
                                paperPublicationManager.addTrial(trialName, journalName, quartile, accepted, revision, rejection, false);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Muestra la información detallada de una prueba en concreto
     * @param numTrial Indice de la prueba concreta sobre la que se quiera obtener información
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

    public void deletePaper (String name) {

    }

    private boolean checkError (String aux, int mode) throws FileNotFoundException {
        switch (mode) {
            case 1: // Comprobamos que el nombre no este vacío y que no exista
                if (!aux.isEmpty()) {
                    return !paperPublicationManager.checkExistence(aux);
                }else{
                    return false;
                }
            case 2: // Comprobamos que el nombre no este vacío
                return !aux.isEmpty();
            case 3: // Comprobamos que sea uno de los valores posibles
                return aux.equals("Q1") || aux.equals("Q2") || aux.equals("Q3") || aux.equals("Q4");
            case 4, 5, 6: // Comprobamos que este entre 0 y 100
                return Integer.parseInt(aux) >= 0 && Integer.parseInt(aux) <= 100;
        }
        return true;
    }
}
