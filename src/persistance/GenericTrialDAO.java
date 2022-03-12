package persistance;

import business.typeTrials.GenericTrial;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface GenericTrialDAO {

    boolean create(GenericTrial name) ;

    LinkedList<GenericTrial> readAll() ;

    GenericTrial findByIndex(int index);

    boolean delete(int index);

    //boolean changeLine (int index, GenericTrial genericTrial);

}
