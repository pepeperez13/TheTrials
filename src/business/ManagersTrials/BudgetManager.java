package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.Budget;
import persistance.BudgetDAO;
import persistance.CSV.BudgetCsvDAO;
import persistance.JSON.BudgetJsonDAO;

import java.util.LinkedList;

public class BudgetManager {
    private BudgetDAO budgetDAO;
    public BudgetManager(DataSourceOptions options) {
        switch (options) {
            //case JSON -> budgetDAO = new BudgetJsonDAO();
            case CSV -> budgetDAO = new BudgetCsvDAO();
        }
    }

    public boolean addBudget (String nameTrial, String nameEntity, int amount) {
        Budget budget = new Budget(nameTrial, nameEntity, amount);
        return budgetDAO.create(budget);
    }

    public LinkedList<Budget> getBudget () {
        return budgetDAO.readAll();
    }

    public Budget geBudgetByIndex (int index) {
        Budget budget = budgetDAO.findByIndex(index);
        return budget;
    }

    public Budget getBudgetByNameTrial (String name) {
        int i;
        boolean found = false;
        LinkedList<Budget> budgets = budgetDAO.readAll();
        for (i = 0; i < budgets.size() && !found; i++) {
            if (budgets.get(i).getNameTrial().equals(name)) {
                found = true;
            }
        }
        return budgets.get(i - 1);
    }

    public LinkedList<String> getBudgetNamesTrials() {
        LinkedList<Budget> budgets =  budgetDAO.readAll();
        LinkedList<String> nombres = new LinkedList<>();
        for (int i = 0; i < budgets.size(); i++) {
            nombres.add(budgets.get(i).getNameTrial());
        }
        return nombres;
    }

    public boolean deleteBudget (int index) {
        return budgetDAO.delete(index);
    }
}
