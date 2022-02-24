package business.ManagersTrials;

import business.DataSourceOptions;
import business.PlayerTypeOptions;
import business.typeTrials.GenericTrialInfo;
import persistance.AllTrialsDAO;
import persistance.CSV.AllTrialsCsvDAO;
import persistance.JSON.AllTrialsJsonDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class AllTrialsNamesManager {
    private AllTrialsDAO trialsDAO;

    public AllTrialsNamesManager (DataSourceOptions options) throws FileNotFoundException {
        switch (options) {
            case JSON -> trialsDAO = new AllTrialsJsonDAO();
            case CSV -> trialsDAO= new AllTrialsCsvDAO();
        }
    }

    public void addTrial (String name, PlayerTypeOptions type) throws IOException {
        GenericTrialInfo genericTrialInfo = new GenericTrialInfo(name, type);
        trialsDAO.create(genericTrialInfo);
    }

    public PlayerTypeOptions getTrialTypeByIndex (int index) {
        LinkedList<GenericTrialInfo> trialsNames = trialsDAO.readAll();
        return trialsNames.get(index-1).getType();
    }
}
