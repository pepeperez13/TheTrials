package presentation.managers;

import business.ManagersTrials.BudgetManager;
import presentation.ViewController;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BudgetController {

    private BudgetManager budgetManager;
    private ViewController view;

    public BudgetController (BudgetManager budgetManager, ViewController view) {
        this.budgetManager = budgetManager;
        this.view = view;
    }

    public void add() throws IOException {
        String trialName = view.askForString("\nEnter the trial's name: ");
        if (checkError(trialName, 1)) {
            String entityName = view.askForString("\nEnter the entity's name: ");
            if (checkError(entityName, 2)) {
                int budget = view.askForInteger("\nEnter the budget amount: ");
                if (checkError(String.valueOf(budget), 3)) {
                    budgetManager.addBudget(trialName, entityName, budget);
                }
            }
        }
    }

    private boolean checkError (String aux, int mode)  {
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
                return Integer.parseInt(aux) >= 1000 && Integer.parseInt(aux) <= 2000000000;
        }
        return true;
    }
}
