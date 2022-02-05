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

    private boolean checkError (String name, int num) {

        return false;
    }
}
