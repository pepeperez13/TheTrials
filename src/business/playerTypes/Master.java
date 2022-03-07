package business.playerTypes;

public class Master extends Player {
    private String name;
    private int PI;

    public Master(String name, int PI, String name1, int pi) {this.name = name1;
        this.PI = pi;
    }

    @Override
    public void decrementPI (int change) {
        if (super.getPI() - change >= 0) {
            super.setPi(super.getPI() - change);
        }else{
            super.setPi(0);
        }
    }

}
