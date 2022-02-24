package presentation;

import business.GameLogic;
import business.TeamManager;
import business.playerTypes.Player;
import business.typeTrials.Budget;
import business.typeTrials.PaperPublication;

import java.io.IOException;

public class BudgetExecutor implements ConductorControllerIface{

    @Override
    public void playTrial (Object o, TeamManager teamManager, ViewController view, GameLogic gameLogic) throws IOException {
        int i = 0;

        Budget budget = Budget.class.cast(o);

        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                player = gameLogic.publishArticle(budget, player); // Publicamos articulo
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
            }
            i++;
        }
    }
}
