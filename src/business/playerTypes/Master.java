package business.playerTypes;

public class Master extends Player {
    private String name;
    private int PI;

    public Master (String name, int PI) {
        this.name = name;
        this.PI = PI;
    }

    @Override
    public void incrementPI (int change) {
        PI = PI + change;
    }

    @Override
    public void decrementPI (int change) {
        if (PI - change/2 >= 0) {
            PI = PI - change/2;
        }else{
            PI = 0;
        }
    }

    @Override
    public boolean checkUpdateStatus() {
        if ( PI >= 10) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int getPI () {
        return PI;
    }

    @Override
    public String getName () {
        return name;
    }
}
