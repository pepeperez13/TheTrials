package persistance.JSON;

import business.Edition;

import java.io.FileNotFoundException;
import java.io.FileReader;
import business.Edition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.EditionDAO;


import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

    public class EditionJsonDAO implements EditionDAO {

        private static final String filename = "files/editions.json";
        private final Gson gson;

        public EditionJsonDAO () {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }

        @Override
        public LinkedList<Edition> readAll() throws FileNotFoundException {
            FileReader reader = new FileReader(filename);

            Edition[] editions = gson.fromJson(reader, Edition[].class);

            return new LinkedList<>(Arrays.asList(editions));
        }

        @Override
        public boolean create(Edition edition) throws IOException {
            FileReader reader = new FileReader(filename);
            FileWriter writer = new FileWriter(filename);

            Edition[] editions = gson.fromJson(reader, Edition[].class);
            LinkedList<Edition> editionList = new LinkedList<>(Arrays.asList(editions));

            gson.toJson(editionList.add(edition), writer);
            writer.close();
            return false;
        }

        /**
         * Método que elimina una edición según el año
         * @param index Entero que nos indica la edición a eliminar
         * @return
         */
        @Override
        public boolean delete(int index) throws IOException {
            FileReader reader = new FileReader(filename);
            FileWriter writer = new FileWriter(filename);

            Edition[] editions = gson.fromJson(reader, Edition[].class);
            LinkedList<Edition> editionList = new LinkedList<>(Arrays.asList(editions));
            editionList.remove(index);

            gson.toJson(editionList, writer);
            writer.close();
            return false;
        }
        /**
         * Este método nos permite obtener una edicion en concreto
         * @param index Indice que nos indica la posicion del objeto deseado
         * @return Edition Retorna la edicion deseada
         */
        @Override
        public Edition findByIndex(int index) throws FileNotFoundException {
            FileReader reader = new FileReader(filename);
            Edition[] editions = gson.fromJson(reader, Edition[].class);

            return editions[index];
        }
}
