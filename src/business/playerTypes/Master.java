package business.playerTypes;

public class Master extends Player {
    private String name;
    private int PI;

    public Master (String name, int PI) {
        this.name = name;
        this.PI = PI;
    }

    @Override
    public void decrementPI (int change) {
        if (super.getPI() - change >= 0) {
            super.setPi(super.getPI() - change);
        }else{
            super.setPi(0);
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
}
