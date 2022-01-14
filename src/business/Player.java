package business;

public class Player {
    private final String name;
    private int PI;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, int PI) {
        this.name = name;
        this.PI = PI;
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

}
