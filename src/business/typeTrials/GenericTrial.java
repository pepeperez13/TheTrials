package business.typeTrials;

import business.PlayerTypeOptions;

public class GenericTrial {
    private String name;
    private PlayerTypeOptions type;

    public GenericTrial(String name, PlayerTypeOptions type) {
        this.name = name;
        this.type = type;
    }

    public String getName () {
        return name;
    }

    public PlayerTypeOptions getType (){
        return type;
    }
}
