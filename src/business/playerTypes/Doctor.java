package business.playerTypes;

public class Doctor extends Player{
    private String name;
    private int PI;

    public Doctor (String name, int pi) {
        this.name = name;
        this.PI = pi;
    }

    @Override
    public void incrementPI (int change) {
        super.setPi(super.getPI() + change*2);
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
