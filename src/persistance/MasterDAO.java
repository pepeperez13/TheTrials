package persistance;

import business.typeTrials.MasterStudies;

import java.util.LinkedList;

public interface MasterDAO {
    boolean create(MasterStudies masterStudies);

    LinkedList<MasterStudies> readAll();

    MasterStudies findByIndex(int index);

    boolean delete(int index);
}
