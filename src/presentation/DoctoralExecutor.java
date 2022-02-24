package presentation;

import business.TeamManager;
import business.playerTypes.Player;
import business.trialExecutionLogic.DoctoralGame;
import business.typeTrials.DoctoralThesis;

import java.io.IOException;

public class DoctoralExecutor {

    public void playTrial(DoctoralThesis doctoralThesis) throws IOException {
        ViewController view = new ViewController();
        DoctoralGame doctoralGame = null;
        TeamManager teamManager = null;

        int i = 0;
        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                // Falta configurar una especie de gameLogic para cada tipo de prueba
                // (uno para doctoral, otro para budget y otro para master. El de paper ya es el de la primera fase)
                // O creo que ya lo tenemos hecho, solo faltaria conectar la siguiente linea con las clases
                // correspondientes que hay en la carpeta trialExecutionLogic, que vendrian a ser el gameLogic
                //player = gameExecutor.publish(doctoral, player); // Publicamos articulo
                doctoralGame.presentThesis(doctoralThesis, player);
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
            }
            i++;
        }
    }
}
