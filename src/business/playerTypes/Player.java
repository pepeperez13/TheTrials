package business.playerTypes;

import business.PlayerTypeOptions;

public abstract class Player {
    private String name;
    private int PI;

    /*public void changePlayerType (PlayerTypeOptions playerType) {
        this.playerType = playerType;
    }*/

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

    /*public PlayerTypeOptions getPlayerType () {
        return playerType;
    }*/

    public boolean checkUpdateStatus () {
        return false;
    }

}
