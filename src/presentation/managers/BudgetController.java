package presentation.managers;

import business.ManagersTrials.BudgetManager;
import presentation.ViewController;

import java.io.IOException;

public class BudgetController {
    private ViewController view;
    private BudgetManager budgetManager;

    public BudgetController(ViewController view, BudgetManager budgetManager) {
        this.view = view;
        this.budgetManager = budgetManager;
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

    private boolean checkError (String name, int num) {
        return true;
    }
}
