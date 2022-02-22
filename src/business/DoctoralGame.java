package business;

import business.playerTypes.Player;
import business.typeTrials.DoctoralThesis;

public class DoctoralGame {

    public void presentThesis (DoctoralThesis doctoralThesis, Player player) {

        double expression = calculateExpression(doctoralThesis);

        manageScore(expression, player);
    }

    private double calculateExpression (DoctoralThesis doctoral) {
        double result = 0;

        for (int i = 1; i <= doctoral.getDifficulty(); i++) {
            result = result + ((2*i) - 1);
        }
        return Math.sqrt(result);
    }

    private void manageScore (double expression, Player player) {

        if (player.getPI() > expression) {
            if (player.getPlayerType() == PlayerTypeOptions.MASTERS) {
                player.changePlayerType(PlayerTypeOptions.DOCTORS);
            }else{
                player.incrementPI(5);
                player.checkUpdateStatus();
                /*if (player.getPI() >= 10) {
                    manageStatus(player);
                }*/
            }
        }
    }

    /*private void manageStatus (Player player) {
        if (player.getPlayerType() == PlayerTypeOptions.ENGINEER) {
            player.changePlayerType(PlayerTypeOptions.MASTERS);
        }
    }*/
}
