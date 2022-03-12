package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.GenericTrial;
import persistance.CSV.GenericTrialCsvDAO;
import persistance.GenericTrialDAO;
import persistance.JSON.GenericTrialJsonDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class GenericTrialManager {
    private GenericTrialDAO trialsDAO;

    public GenericTrialManager(DataSourceOptions options) throws IOException {
        switch (options) {
            //case JSON -> trialsDAO = new GenericTrialJsonDAO();
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

    public String[] getTrialsNamesByIndexes (ArrayList<Integer> indexes) throws FileNotFoundException {
        LinkedList<String> allNames = getTrialsNames(); // Obtenemos los nombres de todas las pruebas disponibles
        LinkedList<String> names = new LinkedList<>();  // Array de strings donde se guardaran los nombres que necesitemos

        for (int i = 0; i < indexes.size(); i++) {
            names.add(allNames.get(indexes.get(i))) ;
        }
        String[] stringNames = new String[names.size()];
        for (int i = 0; i < names.size(); i++) {
            stringNames[i] = names.get(i);
        }
        return stringNames;
    }

    public TrialTypeOptions getTrialTypeByIndex (int index) {
        LinkedList<GenericTrial> trialsNames = trialsDAO.readAll();
        return trialsNames.get(index-1).getType();
    }

    public TrialTypeOptions getTrialTypeByName (String name) {
        boolean found = false;
        int i;
        for (i = 0; i < getTrials().size() && !found; i++) {
            if (getTrials().get(i).getName().equals(name)) {
                found = true;
            }
        }
        return getTrials().get(i-1).getType();
    }

    public GenericTrial getGenericalTrial (int index) {
        return trialsDAO.findByIndex(index);
    }

    public LinkedList<GenericTrial> getTrials () {
        return trialsDAO.readAll();
    }

    public boolean checkExistance (String name) throws FileNotFoundException {
        return getTrialsNames().contains(name);
    }

    public int getIndexByName (String name) {
        int i;
        boolean found = false;
        LinkedList<GenericTrial> genericTrials = trialsDAO.readAll();
        for (i = 0; i < genericTrials.size() && !found; i++) {
            if (genericTrials.get(i).getName().equals(name)) {
                found = true;
            }
        }
        return i - 1;
    }

    public void deleteByname (String name) throws IOException {
        trialsDAO.delete(getIndexByName(name));
    }
}
