package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.DoctoralThesis;
import business.typeTrials.PaperPublication;
import persistance.CSV.DoctoralCsvDAO;
import persistance.DoctoralDAO;
import persistance.JSON.DoctoralJsonDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class DoctoralManager {
    private DoctoralDAO doctoralDAO;
    private GenericTrialManager genericTrialManager;


    public DoctoralManager(DataSourceOptions options) throws IOException {
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

    public int getIndexByName (String name) throws FileNotFoundException {
        int i;
        boolean found = false;
        LinkedList<DoctoralThesis> doctorals = doctoralDAO.readAll();
        for (i = 0; i < doctorals.size() && !found; i++) {
            if (doctorals.get(i).getName().equals(name)) {
                found = true;
            }
        }
        return i - 1;
    }

    public void setInUseByName(String name) throws IOException {
        int index = getIndexByName(name);
        DoctoralThesis auxDoctoral = getDoctoralByName(name);
        DoctoralThesis doctoralThesis = new DoctoralThesis(auxDoctoral.getName(), auxDoctoral.getFieldOfStudy(),
                auxDoctoral.getDifficulty(), true);
        doctoralDAO.changeLine(index, doctoralThesis);
    }
}