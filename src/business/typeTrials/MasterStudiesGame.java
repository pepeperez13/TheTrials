package business.typeTrials;

import business.Player;
import business.PlayerTypeOptions;

import java.util.Random;

public class MasterStudiesGame {

    //Metodo que dice si el jugador ha pasado los creditos requeridos de la prueba
    public void passCredits (MasterStudies masterStudies, Player player) {
        Random rand = new Random();
        int randomNumber = rand.nextInt(101);
        int pass = 0, deny = 0;
        //Comprobar cuantos creditos pasa
        for (int i = 0; i <= masterStudies.getNumberCredits() ; i++) {
            if (randomNumber <= 100) {
                //Credito conseguido
                pass++;
            }
            else {
                //Credito rechazado
                deny++;
            }
        }
        if (pass > deny) {
            if (player.getPlayerType() == PlayerTypeOptions.ENGINEER) {
                player.changePlayerType(PlayerTypeOptions.MASTERS);
            } else {
                player.incrementPI(3);
                //checkStatus --> Futuro metodo que nos dira si el jugador ya puede evolucionar
            }
        } else {
            player.decrementPI(3);
        }
    }
    //Si lo ves eres maricon
}
