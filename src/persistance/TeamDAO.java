package persistance;

import business.playerTypes.Player;

import java.util.LinkedList;

/**
 * Interficie contiene los métodos de
 */
public interface TeamDAO {

    boolean create(Player player);

    LinkedList<Player> readAll();

    boolean delete(int index);

    boolean changeLine(int index, Player player);

    boolean emptyFile();
}