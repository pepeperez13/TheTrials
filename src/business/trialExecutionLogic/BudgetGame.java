package business.trialExecutionLogic;

import business.ManagersTrials.BudgetManager;
import business.TeamManager;
import business.playerTypes.Player;
import business.typeTrials.Budget;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class BudgetGame {

    public LinkedList<Player> checkTeam (Budget budget) throws FileNotFoundException {
        TeamManager teamManager = null;
        if (teamManager.getPITeam() > (int) Math.pow(2, budget.getAmount())) {
            updatePITeam(teamManager);
        }
        return teamManager.getPlayers();
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
