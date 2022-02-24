package business.typeTrials;

import business.ManagersTrials.TrialTypeOptions;
import business.PlayerTypeOptions;

public class GenericTrial {
    private String name;
    private TrialTypeOptions type;

    public GenericTrial(String name, TrialTypeOptions type) {
        this.name = name;
        this.type = type;
    }

    public String getName () {
        return name;
    }

    public TrialTypeOptions getType (){
        return type;
    }
}
