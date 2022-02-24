package presentation;

import business.GameLogic;
import business.TeamManager;
import business.playerTypes.Player;
import business.typeTrials.Budget;
import business.typeTrials.DoctoralThesis;

import java.io.IOException;

public class DoctoralExecutor implements ConductorControllerIface{

    @Override
    public void playTrial(Object o, TeamManager teamManager, ViewController view, GameLogic gameLogic) throws IOException {
        int i = 0;

        DoctoralThesis doctoral = DoctoralThesis.class.cast(o);


        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                // Falta configurar una especie de gameLogic para cada tipo de prueba
                // (uno para doctoral, otro para budget y otro para master. El de paper ya es el de la primera fase)
                // O creo que ya lo tenemos hecho, solo faltaria conectar la siguiente linea con las clases
                // correspondientes que hay en la carpeta trialExecutionLogic, que vendrian a ser el gameLogic
                player = gameLogic.publishArticle(doctoral, player); // Publicamos articulo
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
            }
            i++;
        }
    }
}
