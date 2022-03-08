package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.GenericTrial;
import business.typeTrials.MasterStudies;
import business.typeTrials.PaperPublication;
import persistance.GenericTrialDAO;
import presentation.TypeTrials;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class GenericTrialManager {
    private GenericTrialDAO trialsDAO;

    public void runClass (DataSourceOptions options) throws IOException {
    }
    public abstract boolean addTrial(String name, String nom, int numberCredits, int probability, boolean inUse) throws IOException;

    public abstract LinkedList<MasterStudies> getTrials();

    public abstract MasterStudies getTrialByIndex(int index);

    public abstract MasterStudies getTrialsByName(String name);

    public abstract void addTrial(String name, String magazine, String quartile, int accepted, int revised, int rejected, boolean inUse) throws IOException;

    public abstract int getTrialIndexByName(String name) throws FileNotFoundException;

    public abstract LinkedList<String> getMasterNames();

    public abstract boolean deleteTrial(int index) throws IOException;

    public abstract PaperPublication getTrialByName(String name) throws FileNotFoundException;

    public abstract boolean isInUse(String name);

    public abstract void setInNotUseByName(String name) throws IOException;
}
