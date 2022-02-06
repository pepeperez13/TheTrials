package presentation.managers;

import business.ManagersTrials.DoctoralManager;
import presentation.ViewController;

import java.io.IOException;

public class DoctoralController {
    private ViewController view;
    private DoctoralManager doctoralManager;

    public DoctoralController(ViewController view, DoctoralManager doctoralManager) {
        this.view = view;
        this.doctoralManager = doctoralManager;
    }

    public void add() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (checkError(trialName, 1)) {
            String thesis = view.askForString("\nEnter the thesis field of study: ");
            if (checkError(thesis, 2)) {
                int difficulty = view.askForInteger("\nEnter the defense difficulty: ");
                if (checkError(String.valueOf(difficulty), 3)) {
                    doctoralManager.addDoctoralThesis(trialName, thesis, difficulty);
                }
            }
        }
    }

    private boolean checkError (String aux, int mode) {
        switch (mode) {
            case 1: // Comprobamos que el nombre no este vacío y que no exista
                if (!aux.isEmpty()) {
                    //return !budgetManager.checkExistence(aux);
                }else{
                    return false;
                }
            case 2: // Comprobamos que el nombre no este vacío
                return !aux.isEmpty();
            case 3: // Comprobamos que sea uno de los valores posibles
                return Integer.parseInt(aux) >= 0 && Integer.parseInt(aux) <= 10;
            case 4, 5, 6: // Comprobamos que este entre 0 y 100
                return Integer.parseInt(aux) >= 0 && Integer.parseInt(aux) <= 100;
        }
        return true;

    }
}