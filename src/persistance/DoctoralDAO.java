package persistance;

import business.typeTrials.DoctoralThesis;

import java.io.IOException;
import java.util.LinkedList;

public interface DoctoralDAO {
    boolean create(DoctoralThesis doctoralThesis) throws IOException;

    LinkedList<DoctoralThesis> readAll();

    DoctoralThesis findByIndex(int index);

    boolean delete(int index) throws IOException;
}
