package persistance;

import business.typeTrials.Budget;

import java.io.IOException;
import java.util.LinkedList;

public interface BudgetDAO {
    boolean create(Budget budget);

    LinkedList<Budget> readAll();

    Budget findByIndex(int index);

    boolean delete(int index) throws IOException;

    boolean changeLine(int index, Budget budget) throws IOException;
}
