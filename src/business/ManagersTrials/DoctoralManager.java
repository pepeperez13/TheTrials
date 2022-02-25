package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.DoctoralThesis;
import persistance.CSV.DoctoralCsvDAO;
import persistance.DoctoralDAO;
import persistance.JSON.DoctoralJsonDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class DoctoralManager {
    private DoctoralDAO doctoralDAO;
    private GenericTrialManager genericTrialManager;


    public DoctoralManager(DataSourceOptions options) throws FileNotFoundException {
        switch (options) {
            case JSON -> doctoralDAO = new DoctoralJsonDAO();
            case CSV -> doctoralDAO = new DoctoralCsvDAO();
        }
    }

    public boolean addDoctoralThesis (String name, String field, int difficulty, boolean inUse) throws IOException {
        DoctoralThesis doctoralThesis = new DoctoralThesis(name, field, difficulty, inUse);
        genericTrialManager.addTrial(name, TrialTypeOptions.DOCTORAL);
        return doctoralDAO.create(doctoralThesis);
    }

    public LinkedList<DoctoralThesis> getDoctoralThesis () {
        return doctoralDAO.readAll();
    }

    public DoctoralThesis geDoctoralByIndex (int index) {
        return doctoralDAO.findByIndex(index);
    }

    public DoctoralThesis getDoctoralByName (String name) {
        int i;
        boolean found = false;
        LinkedList<DoctoralThesis> doctoralTheses = doctoralDAO.readAll();
        for (i = 0; i < doctoralTheses.size() && !found; i++) {
            if (doctoralTheses.get(i).getName().equals(name)) {
                found = true;
            }
        }
        return doctoralTheses.get(i - 1);
    }

    public LinkedList<String> getDoctoralNames() {
        LinkedList<DoctoralThesis> doctoralTheses =  doctoralDAO.readAll();
        LinkedList<String> nombres = new LinkedList<>();
        for (int i = 0; i < doctoralTheses.size(); i++) {
            nombres.add(doctoralTheses.get(i).getName());
        }
        return nombres;
    }

    public boolean deleteMaster (int index) throws IOException {
        return doctoralDAO.delete(index);
    }

    public boolean isInUse (String name) {
        return getDoctoralByName(name).isInUse();
    }

}