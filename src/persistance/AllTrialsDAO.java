package persistance;

import business.typeTrials.GenericTrialInfo;

import java.io.IOException;
import java.util.LinkedList;

public interface AllTrialsDAO {

    boolean create(GenericTrialInfo name) throws IOException;

    LinkedList<GenericTrialInfo> readAll();

    GenericTrialInfo findByIndex(int index);

    boolean delete(int index) throws IOException;

}
