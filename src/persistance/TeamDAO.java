package persistance;

import business.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface TeamDAO {

    boolean create(String name, int PI) throws IOException;

    LinkedList<Player> readAll() throws FileNotFoundException;

    boolean delete(int index) throws IOException;

    boolean changeLine(int index, String name, int PI) throws IOException;

    boolean emptyFile() throws IOException;
}