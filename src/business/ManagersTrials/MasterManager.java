package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.MasterStudies;
import persistance.CSV.MasterCsvDAO;
import persistance.JSON.MasterJsonDAO;
import persistance.MasterDAO;

import java.util.LinkedList;


public class MasterManager {
    private MasterDAO masterDAO;
    public MasterManager(DataSourceOptions options) {
        switch (options) {
            //case JSON -> masterDAO = new MasterJsonDAO();
            case CSV -> masterDAO = new MasterCsvDAO();
        }
    }

    public boolean addMasterManager (String name, String nom, int numberCredits, int probability) {
        MasterStudies masterStudies = new MasterStudies(name, nom, numberCredits, probability);
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

    public LinkedList<String> getMasterNames() {
        LinkedList<MasterStudies> masterStudies =  masterDAO.readAll();
        LinkedList<String> nombres = new LinkedList<>();
        for (int i = 0; i < masterStudies.size(); i++) {
            nombres.add(masterStudies.get(i).getName());
        }
        return nombres;
    }

    public boolean deleteMaster (int index) {
        return masterDAO.delete(index);
    }


}
