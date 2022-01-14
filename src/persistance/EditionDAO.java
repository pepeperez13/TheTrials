package persistance;

import business.Edition;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface EditionDAO {
    LinkedList<Edition> readAll() throws FileNotFoundException;

    boolean create(Edition edition) throws IOException;

    boolean delete(int index) throws IOException;

    Edition findByIndex(int index) throws FileNotFoundException;
}
