package business.typeTrials;

import business.ManagersTrials.TrialTypeOptions;

/**
 * Clase que contiene el nombre y el tipo de prueba
 * @author Abraham Cedeño
 * @author José Pérez
 */
public class GenericTrial {
    private String name;
    private TrialTypeOptions type;

    /**
     * Método que crea una prueba Generica
     * @param name Nombre de la prueba
     * @param type Tipo de la prueba
     */
    public GenericTrial(String name, TrialTypeOptions type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Método que retorna el nombre de la prueba
     * @return String con el nombre de la prueba
     */
    public String getName () {
        return name;
    }

    /**
     * Método que retorna el tipo de prueba
     * @return Retorna un typo TrialTypeOptions
     */
    public TrialTypeOptions getType (){
        return type;
    }
}
