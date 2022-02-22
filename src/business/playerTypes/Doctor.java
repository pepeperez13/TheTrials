package business.playerTypes;

import business.PlayerTypeOptions;

public class Doctor extends Master{

    public Doctor(String name, int PI, PlayerTypeOptions playerType) {
        super(name, PI, playerType);
    }

    @Override
    public void incrementPI (int change) {
        super.setPi(super.getPI() + change*2);
    }
}
