package business.trialExecutionLogic;

import business.TeamManager;
import business.typeTrials.Budget;

import java.io.FileNotFoundException;

public class BudgetGame {

    public boolean checkAndUpdatePI(Budget budget) throws FileNotFoundException {
        TeamManager teamManager = null;
        if (teamManager.getPITeam() > (int) Math.pow(2, budget.getAmount())) {
            updatePITeam(teamManager);
            return true;
        } else {
            return false;
        }
    }

    private void updatePITeam (TeamManager teamManager) throws FileNotFoundException {
        int extraScore = 0;
        for (int i = 0; i < teamManager.getPlayers().size(); i++) {
            extraScore = teamManager.getPlayers().get(i).getPI()/2;
            teamManager.getPlayers().get(i).incrementPI(extraScore);
        }
    }

    public void changePlayersStatus (TeamManager teamManager) throws FileNotFoundException {
        for (int i = 0; i < teamManager.getPlayers().size(); i++) {
            teamManager.getPlayers().get(i).checkUpdateStatus();
        }
    }
}
