package persistance.JSON;

import business.typeTrials.Budget;
import business.typeTrials.MasterStudies;
import business.typeTrials.PaperPublication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.BudgetDAO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class BudgetJsonDAO implements BudgetDAO {

    private static final String filename = "files/team.json";
    private final Gson gson;
    private final Budget[] budgets;

    public BudgetJsonDAO () throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        budgets = gson.fromJson(gson.newJsonReader(new FileReader(filename)), Budget[].class);
    }

    @Override
    public boolean create(Budget budget) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<Budget> budgetsList = new LinkedList<>();
        if (budgets != null) { // Sólo leeremos elementos si el json no está vacío
            budgetsList = new LinkedList<>(Arrays.asList(budgets));
        }

        budgetsList.add(budget);
        gson.toJson(budgetsList, writer);
        writer.close();

        return false;
    }

    @Override
    public LinkedList<Budget> readAll() {
        // Nunca va estar vacia (comprobamos antes de llamar)
        return new LinkedList<>(Arrays.asList(budgets));
    }

    @Override
    public Budget findByIndex(int index) {
        return budgets[index];
    }

    @Override
    public boolean delete(int index) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<Budget> budgetsList = new LinkedList<>(Arrays.asList(budgets));
        budgetsList.remove(index);

        gson.toJson(budgetsList, writer);
        writer.close();

        return false;
    }
}
