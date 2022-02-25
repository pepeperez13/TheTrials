package persistance.CSV;

import business.typeTrials.Budget;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class BudgetCsvDAO implements persistance.BudgetDAO {
    private static final String separator = ",";
    private static final File file = new File ("files/budget.csv");


    private String budgetToCsv(Budget budget) {
        return budget.getNameEntity() + separator + budget.getNameTrial() + separator + budget.getAmount();
    }

    private Budget budgetFromCsv (String csv) {
        String[] parts = csv.split(separator);
        return new Budget(parts[0], parts[1], Integer.parseInt(parts[2]), Boolean.valueOf(parts[3]));
    }

    @Override
    public boolean create(Budget budget) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            list.add(budgetToCsv(budget));
            Files.write(file.toPath(), list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<Budget> readAll() {
        try{
            LinkedList<Budget> budgets = new LinkedList<>();
            List<String> list = Files.readAllLines(file.toPath());
            for (String line: list) {
                budgets.add(budgetFromCsv(line));
            }
            return budgets;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Budget findByIndex(int index) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            return budgetFromCsv(list.get(index-1));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete(int index) {
        try {
            List<String> budgets = Files.readAllLines(file.toPath());
            budgets.remove(index);
            Files.write(file.toPath(), budgets);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}

