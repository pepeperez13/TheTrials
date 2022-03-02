package business.trialExecutionLogic;

import business.TeamManager;
import business.typeTrials.Budget;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class BudgetGame {

    public boolean checkAndUpdatePI(Budget budget) throws FileNotFoundException {
        TeamManager teamManager = null;
        if (teamManager.getPITeam() > (int) Math.pow(2, budget.getAmount())) {
            increasePITeam(teamManager);
            return true;
        } else {
            decreasePITeam(teamManager);
            return false;
        }
    }

    private void increasePITeam(TeamManager teamManager) throws FileNotFoundException {
        int extraScore = 0;
        for (int i = 0; i < teamManager.getPlayers().size(); i++) {
            extraScore = teamManager.getPlayers().get(i).getPI()/2;
            teamManager.getPlayers().get(i).incrementPI(extraScore);
        }
    }

    private void decreasePITeam (TeamManager teamManager) throws FileNotFoundException {
        for (Player player: teamManager.getPlayers()) {
            player.decrementPI(2);
        }
    }

    public LinkedList<String> changePlayersStatus (TeamManager teamManager) throws FileNotFoundException {
        LinkedList<String> namesUpdatedType = new LinkedList<>();
        for (int i = 0; i < teamManager.getPlayers().size(); i++) {
            if (teamManager.getPlayers().get(i).checkUpdateStatus()) {
                // Añadimos nombre y nuevo status a la lista
                namesUpdatedType.add((teamManager.getPlayers().get(i).getName()) + " "  + (teamManager.getPlayers().get(i).getPlayerType()));
            }
        }
        return namesUpdatedType;
    }
}
