package persistance;

import business.typeTrials.MasterStudies;
import business.typeTrials.PaperPublication;

import java.io.IOException;
import java.util.LinkedList;

public interface MasterDAO {
    boolean create(MasterStudies masterStudies) throws IOException;

    LinkedList<MasterStudies> readAll();

    MasterStudies findByIndex(int index);

    boolean delete(int index) throws IOException;

    boolean changeLine(int index, MasterStudies masterStudies) throws IOException;


}
