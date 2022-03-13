package persistance;

import business.playerTypes.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface TeamDAO {

    boolean create(Player player);

    LinkedList<Player> readAll();

    boolean delete(int index);

    boolean changeLine(int index, Player player);

    boolean emptyFile();
}