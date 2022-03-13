package business.typeTrials;

/**
 * Representa el tipo de prueba Doctoral
 * @author Abraham Cedeño
 * @author José Pérez
 */
public class DoctoralThesis {
    private String name;
    private String fieldOfStudy;
    private int difficulty;
    private boolean inUse;

    /**
     * Método constructor que crea un nuevo DoctoralThesis, teniendo en cuenta si está en uso
     * @param name Nombre de la prueba
     * @param fieldOfStudy Nombre del campo de estudio
     * @param difficulty Dificultad de la defensa de la tesis
     * @param inUse Nos permitirá saber si la prueba se usa en alguna edición
     */
    public DoctoralThesis(String name, String fieldOfStudy, int difficulty, boolean inUse) {
        this.name = name;
        this.fieldOfStudy = fieldOfStudy;
        this.difficulty = difficulty;
        this.inUse = inUse;
    }

    /**
     * Método que nos permite saber el nombre de la prueba
     * @return El nombre de la prueba
     */
    public String getName() {
        return name;
    }

    /**
     * Método que nos permite saber el nombre del campo de estudio
     * @return String con el nombre de campo de estudio
     */
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    /**
     * Método que nos permite saber si la prueba esta en uso
     * @return true si está en uso, false si no lo está
     */
    public boolean isInUse() {
        return inUse;
    }

    /**
     * Método que nos retorna la dificultad de presentar la tesis
     * @return Entero que indica la dificultad
     */
    public int getDifficulty() {
        return difficulty;
    }
}