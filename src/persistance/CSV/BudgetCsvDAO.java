package persistance.CSV;

import business.typeTrials.Budget;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class BudgetCsvDAO implements persistance.BudgetDAO {
    private static String separator = ",";
    private static File file = new File ("files/budget.csv");

    @Override
    public boolean create(Budget budget) {
        return writeFile(budget);
    }

    private String budgetToCsv(Budget budget) {
        return budget.getNameEntity() + separator + budget.getNameTrial() + separator + budget.getAmount();
    }

    private boolean writeFile (Budget budget) {
        FileReader fr = null;
        BufferedReader bf = null;
        PrintWriter pw = null;
        FileWriter fw = null;
        LinkedList<String> lines = new LinkedList<>();
        try {
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String linea;
            while ((linea=bf.readLine())!=null) {
                lines.add(linea);
            }
            fr.close();
            fw = new FileWriter(file);
            pw = new PrintWriter(fw);
            for (String line:lines) {
                pw.println(line);
            }
            pw.println(budgetToCsv(budget));
        } catch (IOException e) {
            return false;
        }
        finally {
            try{
                if( null != fr ){
                    fr.close();
                }
                if (null != fw){
                    fw.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public LinkedList<Budget> readAll() {
        return readFile ();
    }

    private Budget budgetFromCsv (String csv) {
        String[] parts = csv.split(separator);
        return new Budget(parts[0], parts[1], Integer.parseInt(parts[2]));
    }

    private LinkedList<Budget> readFile () {
        FileReader fr = null;
        BufferedReader bf = null;
        LinkedList<Budget> budgets = new LinkedList<>();
        try {
            List<String> lines = new LinkedList<>();
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String linea;
            while ((linea=bf.readLine())!=null) {
                lines.add(linea);
            }
            for (String line : lines) {
                budgets.add(budgetFromCsv(line));
            }
        } catch (IOException e) {
            return budgets;
        }
        return budgets;
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
        return updateLine(index);
    }

    private boolean updateLine (int index) {
        // Inicializar
        FileReader fileReader = null;
        BufferedReader bufferedReader;
        PrintWriter printWriter;
        FileWriter fileWriter = null;
        LinkedList<String> lines = new LinkedList<>();

        // Leer y actualizar
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            // Mientras no se acabe el fichero, leemos
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            lines.remove(index);
            fileReader.close();
            // Volvemos a escribir toda la info en el fichero
            fileWriter = new FileWriter(file);
            printWriter = new PrintWriter(fileWriter);
            for (String linea : lines) {
                printWriter.println(linea);
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (null != fileReader) {
                    fileReader.close();
                }
                if (null != fileReader) {
                    fileWriter.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }
}

