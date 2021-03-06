package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.MasterStudies;
import persistance.CSV.MasterCsvDAO;
import persistance.JSON.MasterJsonDAO;
import persistance.MasterDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;


public class MasterManager {
    private MasterDAO masterDAO;
    private GenericTrialManager genericTrialManager;

    public MasterManager(DataSourceOptions options, GenericTrialManager genericTrialManager) throws IOException {
        this.genericTrialManager = genericTrialManager;
        switch (options) {
            case JSON -> masterDAO = new MasterJsonDAO();
            case CSV -> masterDAO = new MasterCsvDAO();
        }
    }

    public boolean addMaster (String name, String nom, int numberCredits, int probability, boolean inUse) throws IOException {
        MasterStudies masterStudies = new MasterStudies(name, nom, numberCredits, probability, inUse);
        return masterDAO.create(masterStudies);
    }

    public LinkedList<MasterStudies> getMasters () {
        return masterDAO.readAll();
    }

    public MasterStudies getMasterByIndex (int index) {
        MasterStudies masterStudies = masterDAO.findByIndex(index);
        return masterStudies;
    }

    public MasterStudies getMasterByName (String name) {
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

    public int getIndexByName (String name) throws FileNotFoundException {
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

    public LinkedList<String> getMasterNames() {
        LinkedList<MasterStudies> masterStudies =  masterDAO.readAll();
        LinkedList<String> nombres = new LinkedList<>();
        for (int i = 0; i < masterStudies.size(); i++) {
            nombres.add(masterStudies.get(i).getName());
        }
        return nombres;
    }

    public boolean deleteMaster (int index) throws IOException {
        return masterDAO.delete(index);
    }

    public boolean isInUse (String name) {
        return getMasterByName(name).isInUse();
    }

    public void setInUseByName(String name) throws IOException {
        int index = getIndexByName(name);
        MasterStudies auxMaster = getMasterByName(name);
        MasterStudies masterStudies = new MasterStudies(auxMaster.getName(), auxMaster.getNom(), auxMaster.getNumberCredits(), auxMaster.getProbability(), true);
        masterDAO.changeLine(index, masterStudies);
    }

    public void setInNotUseByName (String name) throws IOException {
        int index = getIndexByName(name);
        MasterStudies auxMaster = getMasterByName(name);
        MasterStudies masterStudies = new MasterStudies(auxMaster.getName(), auxMaster.getNom(), auxMaster.getNumberCredits(), auxMaster.getProbability(), false);
        masterDAO.changeLine(index, masterStudies);
    }
}
