package persistance;

import business.typeTrials.PaperPublication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface PaperDAO {

    boolean create(PaperPublication article);

    LinkedList<PaperPublication> readAll();

    boolean delete(int index);

    boolean changeLine(int index, PaperPublication paperPublication);
}
