package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.DoctoralThesis;
import persistance.CSV.DoctoralCsvDAO;
import persistance.DoctoralDAO;
import persistance.JSON.DoctoralJsonDAO;

import java.util.LinkedList;

public class DoctoralManager {
    private DoctoralDAO doctoralDAO;
    public DoctoralManager(DataSourceOptions options) {
        switch (options) {
            //case JSON -> doctoralDAO = new DoctoralJsonDAO();
            case CSV -> doctoralDAO = new DoctoralCsvDAO();
        }
    }

    public boolean addDoctoralThesis (String name, String field, int difficulty) {
        DoctoralThesis doctoralThesis = new DoctoralThesis(name, field, difficulty);
        return doctoralDAO.create(doctoralThesis);
    }

    public LinkedList<DoctoralThesis> getDoctoralThesis () {
        return doctoralDAO.readAll();
    }

    public DoctoralThesis geDoctoralByIndex (int index) {
        DoctoralThesis doctoralThesis = doctoralDAO.findByIndex(index);
        return doctoralThesis;
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

    public boolean deleteMaster (int index) {
        return doctoralDAO.delete(index);
    }
}