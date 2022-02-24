package presentation;

import business.TeamManager;
import business.playerTypes.Player;
import business.trialExecutionLogic.BudgetGame;
import business.typeTrials.Budget;

import java.io.IOException;

public class BudgetExecutor {

    public void playTrial (Budget budget) throws IOException {
        TeamManager teamManager = null;
        ViewController view = null;
        BudgetGame budgetGame = null;
        int i = 0;

        budgetGame.checkTeam(budget);
        /*for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                // Falta configurar una especie de gameLogic para cada tipo de prueba
                // (uno para doctoral, otro para budget y otro para master. El de paper ya es el de la primera fase)
                // O creo que ya lo tenemos hecho, solo faltaria conectar la siguiente linea con las clases
                // correspondientes que hay en la carpeta trialExecutionLogic, que vendrian a ser el gameLogic
                player = budgetGame.checkTeam(budget);// Publicamos articulo
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
            }
            i++;
        }*/
    }
}
