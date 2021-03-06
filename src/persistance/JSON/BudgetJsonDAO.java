package persistance.JSON;

import business.typeTrials.Budget;
import business.typeTrials.PaperPublication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import persistance.BudgetDAO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class BudgetJsonDAO implements BudgetDAO {

    private String filename = "budget.json";
    //private String filePath = "C:\\Users\\Ashlyn Abraham\\Documents\\GitHub\\TheTrials\\files";
    private String filePath = "files";
    private File test = new File(filePath, filename);
    private final Gson gson;
    private final Budget[] budgets;

    public BudgetJsonDAO () throws FileNotFoundException {
        try {
            test.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
        budgets = gson.fromJson(gson.newJsonReader(new FileReader("files/"+filename)), Budget[].class);
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
        try {
            return new LinkedList<>(Arrays.asList(budgets));
        } catch (NullPointerException e) {
            return new LinkedList<>();
        }
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
    @Override
    public boolean changeLine(int index, Budget budget) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<Budget> budgetsList = new LinkedList<>(Arrays.asList(budgets));

        budgetsList.remove(index);
        budgetsList.add(index, budget);

        gson.toJson(budgetsList, writer);
        writer.close();
        return false;
    }

}
