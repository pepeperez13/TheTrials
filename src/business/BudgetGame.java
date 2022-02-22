package business;

import business.ManagersTrials.BudgetManager;

import java.io.FileNotFoundException;

public class BudgetGame {

    public void checkTeam (TeamManager teamManager, BudgetManager budgetManager, String nameTrialBudget) throws FileNotFoundException {
        if (teamManager.getPITeam() > (int) Math.pow(2, budgetManager.getBudgetByNameTrial(nameTrialBudget).getAmount())) {
            updatePITeam(teamManager);
        }

    }

    private void updatePITeam (TeamManager teamManager) throws FileNotFoundException {
        int extraScore = 0;
        for (int i = 0; i < teamManager.getPlayers().size(); i++) {
            extraScore = teamManager.getPlayers().get(i).getPI()/2;
            teamManager.getPlayers().get(i).incrementPI(extraScore);
            teamManager.getPlayers().get(i).checkUpdateStatus();
        }
    }
}
