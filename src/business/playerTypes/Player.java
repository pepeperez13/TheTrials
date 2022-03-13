package business.playerTypes;

public abstract class Player {
    private transient String name;
    private transient int PI;

    ///@SerializedName("type")
    //private String typeName;

    /*public Player () {
        typeName = getClass().getName();
    }*/

    public void incrementPI (int change) {
        this.PI = this.PI + change;
    }

    public void decrementPI (int change) {
        if (PI - change >= 0) {
            PI = PI - change;
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
