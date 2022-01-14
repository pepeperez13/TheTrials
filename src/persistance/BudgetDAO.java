package persistance;

import business.typeTrials.Budget;

import java.util.LinkedList;

public interface BudgetDAO {
    boolean create(Budget budget);

    LinkedList<Budget> readAll();

    Budget findByIndex(int index);

    boolean delete(int index);
}