package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.MasterStudies;
import persistance.CSV.MasterCsvDAO;
import persistance.JSON.MasterJsonDAO;
import persistance.MasterDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;


public class MasterManager extends GenericTrialManager{
    private MasterDAO masterDAO;

    @Override
    public void runClass (DataSourceOptions options) throws IOException {
        switch (options) {
            case JSON -> masterDAO = new MasterJsonDAO();
            case CSV -> masterDAO = new MasterCsvDAO();
        }
    }

    @Override
    public boolean addTrial (String name, String nom, int numberCredits, int probability, boolean inUse) throws IOException {
        MasterStudies masterStudies = new MasterStudies(name, nom, numberCredits, probability, inUse);
        return masterDAO.create(masterStudies);
    }

    @Override
    public LinkedList<MasterStudies> getTrials () {
        return masterDAO.readAll();
    }

    @Override
    public MasterStudies getTrialByIndex (int index) {
        MasterStudies masterStudies = masterDAO.findByIndex(index);
        return masterStudies;
    }

    @Override
    public MasterStudies getTrialsByName (String name) {
        int i;
        boolean found = false;
        LinkedList<MasterStudies> masterStudies = masterDAO.readAll();
        for (i = 0; i < masterStudies.size() && !found; i++) {
            if (masterStudies.get(i).getName().equals(name)) {
                found = true;
            }
        }
        return masterStudies.get(i - 1);
    }

    @Override
    public int getTrialIndexByName (String name) throws FileNotFoundException {
        int i;
        boolean found = false;
        LinkedList<MasterStudies> masters = masterDAO.readAll();
        for (i = 0; i < masters.size() && !found; i++) {
            if (masters.get(i).getName().equals(name)) {
                found = true;
            }
        }
        return i - 1;
    }

    @Override
    public LinkedList<String> getMasterNames() {
        LinkedList<MasterStudies> masterStudies =  masterDAO.readAll();
        LinkedList<String> nombres = new LinkedList<>();
        for (int i = 0; i < masterStudies.size(); i++) {
            nombres.add(masterStudies.get(i).getName());
        }
        return nombres;
    }

    @Override
    public boolean deleteTrial (int index) throws IOException {
        return masterDAO.delete(index);
    }

    @Override
    public boolean isInUse (String name) {
        return getTrialsByName(name).isInUse();
    }

    public void setInUseByName(String name) throws IOException {
        int index = getTrialIndexByName(name);
        MasterStudies auxMaster = getTrialsByName(name);
        MasterStudies masterStudies = new MasterStudies(auxMaster.getName(), auxMaster.getNom(), auxMaster.getNumberCredits(), auxMaster.getProbability(), true);
        masterDAO.changeLine(index, masterStudies);
    }

    @Override
    public void setInNotUseByName (String name) throws IOException {
        int index = getTrialIndexByName(name);
        MasterStudies auxMaster = getTrialsByName(name);
        MasterStudies masterStudies = new MasterStudies(auxMaster.getName(), auxMaster.getNom(), auxMaster.getNumberCredits(), auxMaster.getProbability(), false);
        masterDAO.changeLine(index, masterStudies);
    }
}
