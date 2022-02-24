package business.playerTypes;

import business.PlayerTypeOptions;

public class Engineer {
    private final String name;
    private int PI;
    private PlayerTypeOptions playerType;

    public Engineer (String name, int PI, PlayerTypeOptions playerType){
        this.name = name;
        this.PI = PI;
        this.playerType = playerType;
    }

    public void changePlayerType (PlayerTypeOptions playerType) {
        this.playerType = playerType;
    }

    public void incrementPI (int change) {
        this.PI = this.PI + change;
    }

    public void decrementPI (int change) {
        if (this.PI - change >= 0) {
            this.PI = this.PI - change;
        }else{
            this.PI = 0;
        }
    }

    public int getPI () {
        return PI;
    }

    public void setPi (int PI) {
        this.PI = PI;
    }

    public String getName () {
        return name;
    }

    public PlayerTypeOptions getPlayerType () {
        return playerType;
    }

    public void checkUpdateStatus () {
        if (playerType == PlayerTypeOptions.ENGINEER && PI >= 10) {
            playerType = PlayerTypeOptions.MASTERS;
        }
        if (playerType == PlayerTypeOptions.MASTERS && PI >= 10) {
            playerType = PlayerTypeOptions.DOCTORS;
        }
    }
}
