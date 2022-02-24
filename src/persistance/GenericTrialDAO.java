package persistance;

import business.typeTrials.GenericTrial;

import java.io.IOException;
import java.util.LinkedList;

public interface GenericTrialDAO {

    boolean create(GenericTrial name) throws IOException;

    LinkedList<GenericTrial> readAll();

    GenericTrial findByIndex(int index);

    boolean delete(int index) throws IOException;

    //boolean changeLine (int index, GenericTrial genericTrial);

}
