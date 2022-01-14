package persistance;

import business.typeTrials.PaperPublication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface PaperDAO {

    boolean create(PaperPublication article) throws IOException;

    LinkedList<PaperPublication> readAll() throws FileNotFoundException;

    boolean delete(int index) throws IOException;

    boolean changeLine(int index, PaperPublication paperPublication) throws IOException;
}
