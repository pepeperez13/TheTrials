package business.trialExecutionLogic;

import business.PlayerTypeOptions;
import business.playerTypes.Player;
import business.typeTrials.DoctoralThesis;

public class DoctoralGame {

    public boolean checkPassed (DoctoralThesis doctoral, Player player) {
        double result = 0;

        for (int i = 1; i <= doctoral.getDifficulty(); i++) {
            result = result + ((2*i) - 1);
        }
        return player.getPI() > result;
    }


    public Player updatePI (Player player, boolean passed) {
        if (passed) {
            if (player.getPlayerType() == PlayerTypeOptions.MASTERS) {
                player.setPi(10);
            }else{
                player.incrementPI(5);
            }
        }else{
            player.decrementPI(5);
        }
        return player;
    }

    public Player checkUpdateStatus (Player player, boolean passed) {
        if (passed) {
            if (player.getPlayerType() == PlayerTypeOptions.MASTERS) {
                player.changePlayerType(PlayerTypeOptions.DOCTORS);
            }else{
                player.checkUpdateStatus();
            }
        }
        return player;
    }

}
