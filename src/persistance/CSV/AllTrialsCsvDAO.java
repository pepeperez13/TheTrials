package persistance.CSV;

import business.PlayerTypeOptions;
import business.typeTrials.GenericTrialInfo;
import persistance.AllTrialsDAO;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class AllTrialsCsvDAO implements AllTrialsDAO {

    private static String separator = ",";
    private static File file = new File ("files/doctoral.csv");

    private String genericTrialToCsv (GenericTrialInfo name) {
        return name.getName() + separator + name.getType();
    }

    private GenericTrialInfo genericFromCsv (String csv) {
        String[] parts = csv.split(separator);
        return new GenericTrialInfo(parts[0], PlayerTypeOptions.valueOf(parts[1]));
    }

    @Override
    public boolean create(GenericTrialInfo name) throws IOException {
        return false;
    }

    @Override
    public LinkedList<GenericTrialInfo> readAll() {
        return null;
    }

    @Override
    public GenericTrialInfo findByIndex(int index) {
        return null;
    }

    @Override
    public boolean delete(int index) throws IOException {
        return false;
    }
}
