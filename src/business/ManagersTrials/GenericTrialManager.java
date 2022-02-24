package business.ManagersTrials;

import business.DataSourceOptions;
import business.PlayerTypeOptions;
import business.typeTrials.GenericTrial;
import business.typeTrials.PaperPublication;
import persistance.GenericTrialDAO;
import persistance.CSV.GenericTrialCsvDAO;
import persistance.JSON.GenericTrialJsonDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class GenericTrialManager {
    private GenericTrialDAO trialsDAO;

    public GenericTrialManager(DataSourceOptions options) throws FileNotFoundException {
        switch (options) {
            case JSON -> trialsDAO = new GenericTrialJsonDAO();
            case CSV -> trialsDAO= new GenericTrialCsvDAO();
        }
    }

    public void addTrial (String name, TrialTypeOptions type) throws IOException {
        GenericTrial genericTrial = new GenericTrial(name, type);
        trialsDAO.create(genericTrial);
    }

    public LinkedList<String> getTrialsNames() throws FileNotFoundException {
        LinkedList<GenericTrial> trials =  trialsDAO.readAll();
        LinkedList<String> nombres = new LinkedList<>();
        for (int i = 0; i < trials.size(); i++) {
            nombres.add(trials.get(i).getName());
        }
        return nombres;
    }

    public TrialTypeOptions getTrialTypeByIndex (int index) {
        LinkedList<GenericTrial> trialsNames = trialsDAO.readAll();
        return trialsNames.get(index-1).getType();
    }

    public GenericTrial getGenericalTrial (int index) {
        return trialsDAO.findByIndex(index);
    }
}
