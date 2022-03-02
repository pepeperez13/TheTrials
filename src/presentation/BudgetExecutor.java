package presentation;

import business.TeamManager;
import business.playerTypes.Player;
import business.trialExecutionLogic.BudgetGame;
import business.typeTrials.Budget;

import java.io.IOException;
import java.util.LinkedList;

public class BudgetExecutor {
    /**
     * En las clases executor, hace falta ir llamando individualmente a pequeños metodos
     * publicos de las clases game. No podemos actualizar toda la info del jugador a la vez
     * ya que como se deben in mostrando cosas (y el Game no debería), es necesario ir
     * actualizando los datos poco a poco para poder ir mostrando desde el executor
     */
    public void playTrial (Budget budget) throws IOException {
        TeamManager teamManager = null;
        ViewController view = null;
        BudgetGame budgetGame = null;
        LinkedList<String> namesUpdatedType = new LinkedList<>();

        int i = 0;

        boolean accepted = budgetGame.checkAndUpdatePI(budget);
        if (accepted){
            view.showMessage("The research group got the budget!");
        }else{
            view.showMessage("Research group did not get the budget! Sorry!");
        }
        for (Player player: teamManager.getPlayers()) {
            switch (player.getPlayerType()) {
                case ENGINEER -> view.showMessage(player.getName() + ". PI count: " + player.getPI());
                case MASTERS -> view.showMessage("Master " + player.getName() + ". PI count: " + player.getPI());
                case DOCTORS -> view.showMessage(player.getName() + ",PhD. PI count: " + player.getPI());
            }
        }
        namesUpdatedType = budgetGame.changePlayersStatus(teamManager);

        // Mostramos todos los jugadores que hayan cambiado de tipo
        for (String name : namesUpdatedType){
            String[] parts = name.split(" ");
            view.showMessage(parts[0] + " is now a " + parts[1] + "(with 5 PI)");
        }
    }
}
