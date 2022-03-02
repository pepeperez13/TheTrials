package business.playerTypes;

import business.PlayerTypeOptions;

public class Master extends Engineer{

    public Master(String name, int PI, PlayerTypeOptions playerType) {
        super(name, PI, playerType);
    }

    @Override
    public void decrementPI (int change) {
        if (super.getPI() - change >= 0) {
            super.setPi(super.getPI() - change/2);
        }else{
            super.setPi(0);
        }
    }

}
