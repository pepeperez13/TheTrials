package presentation;

import business.ManagersTrials.BudgetManager;
import business.TeamManager;
import business.playerTypes.Doctor;
import business.playerTypes.Engineer;
import business.playerTypes.Master;
import business.playerTypes.Player;
import business.typeTrials.Budget;
import business.typeTrials.GenericTrial;

import javax.print.Doc;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameExecutor {
    private GenericTrial genericTrial;
    private TeamManager teamManager;
    private BudgetManager budgetManager;
    private ViewController view;

    public GameExecutor(GenericTrial genericTrial, TeamManager teamManager, BudgetManager budgetManager, ViewController view) {
        this.genericTrial = genericTrial;
        this.teamManager = teamManager;
        this.budgetManager = budgetManager;
        this.view = view;
    }

    public void playTrial (GenericTrial genericTrial, TeamManager teamManager) throws IOException {
        switch (genericTrial.getType()) {
            case BUDGET ->  {
                playBudget(teamManager, budgetManager.getBudgetByNameTrial(genericTrial.getName()));
            }
            case DOCTORAL -> playDoctoral(teamManager);
            case PAPER -> playPaper(teamManager);
            case MASTER -> playMaster(teamManager);
        }
    }

    private void playBudget (TeamManager teamManager, Budget budget) throws IOException {
        boolean passed;
        if (teamManager.getPITeam() > (int) Math.pow(2, budget.getAmount())) {
            //increasePITeam(teamManager);
            view.showMessage("The research group got the budget!\n");
            passed = true;
        } else {
            //decreasePITeam(teamManager);
            view.showMessage("The research group didn't get the budget!\n");
            passed = false;
        }
        updatePiTeam(teamManager, passed);
        for (Player player : teamManager.getPlayers()) {
            if (player instanceof Engineer) {
                view.showMessage(player.getName()+", Engineer. PI count: " + player.getPI());
            } else if (player instanceof Master) {
                view.showMessage(player.getName()+", Master. PI count: " + player.getPI());
            } else if (player instanceof Doctor) {
                view.showMessage(player.getName()+", PhD. PI count: " + player.getPI());
            }
        }
        checkUpdateStatus(teamManager);
    }

    private void checkUpdateStatus (TeamManager teamManager) throws IOException {
        int i = 0;
        for (Player player : teamManager.getPlayers()) {
            if (player instanceof Engineer) {
                if (player.checkUpdateStatus()) {
                    Player master = new Master(player.getName(), 5);
                    teamManager.updatePlayer(i, master);
                }
            } else if (player instanceof Master) {
                if (player.checkUpdateStatus()) {
                    Player doctor = new Doctor(player.getName(), 5);
                    teamManager.updatePlayer(i, doctor);
                }
            }
            i++;
        }
    }

    private void updatePiTeam (TeamManager teamManager, boolean passed) throws IOException {
        if (passed) {
            for (int i = 0; i < teamManager.getPlayers().size(); i++) {
                //Cambios el PI por cada jugador y lo actualizamos
                teamManager.getPlayers().get(i).incrementPI(teamManager.getPlayers().get(i).getPI()/2);
                teamManager.updatePlayer(i, teamManager.getPlayers().get(i));
            }
        }
        else {
            for (int i = 0; i < teamManager.getPlayers().size(); i++) {
                //Cambios el PI por cada jugador y lo actualizamos
                teamManager.getPlayers().get(i).decrementPI(2);
                teamManager.updatePlayer(i, teamManager.getPlayers().get(i));
            }
        }
    }

    private void playDoctoral (TeamManager teamManager) {

    }

    private void playPaper (TeamManager teamManager) {

    }

    private void playMaster (TeamManager teamManager) {

    }
}
