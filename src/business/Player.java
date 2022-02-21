package business;

public class Player {
    private final String name;
    private int PI;
    private PlayerTypeOptions playerType;

    public Player (String name, int PI){
        this.name = name;
        this.PI = PI;
        this.playerType = PlayerTypeOptions.ENGINEER;
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

    public String getName () {
        return name;
    }

    public PlayerTypeOptions getPlayerType () {
        return playerType;
    }

}
