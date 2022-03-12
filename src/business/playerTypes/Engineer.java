package business.playerTypes;

import business.PlayerTypeOptions;

public class Engineer extends Player{
    private final String name;
    private int PI;

    public Engineer (String name, int PI){
        this.name = name;
        this.PI = PI;
    }

    @Override
    public void incrementPI (int change) {
        this.PI = this.PI + change;
    }

    @Override
    public void decrementPI (int change) {
        if (this.PI - change >= 0) {
            this.PI = this.PI - change;
        }else{
            this.PI = 0;
        }
    }

    @Override
    public int getPI () {
        return PI;
    }

    @Override
    public void setPi (int PI) {
        this.PI = PI;
    }

    @Override
    public String getName () {
        return name;
    }

    /*public PlayerTypeOptions getPlayerType () {
        return playerType;
    }*/

    @Override
    public boolean checkUpdateStatus() {
        if ( PI >= 10) {
            return true;
        }else {
            return false;
        }
    }
}
