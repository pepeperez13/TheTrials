package business.typeTrials;

/**
 * Representa el tipo de prueba Budget
 * @author Abraham Cedeño
 * @author José Pérez
 */
public class Budget {
    private String nameTrial;
    private String nameEntity;
    private int amount;
    private boolean inUse;

    /**
     * Método constructor que crea un nuevo artículo, teniendo en cuenta si está en uso
     * @param nameTrial Nombre de la prueba
     * @param nameEntity Nombre del campo de estudio
     * @param amount Dificultad de la defensa de la tesis
     * @param inUse Nos permitirá saber si la prueba se usa en alguna edición
     */
    public Budget(String nameTrial, String nameEntity, int amount, boolean inUse) {
        this.nameTrial = nameTrial;
        this.nameEntity = nameEntity;
        this.amount = amount;
        this.inUse = inUse;
    }

    /**
     * Método que obtiene el nombre de la prueba
     * @return String con el nombre de la prueba
     */
    public String getNameTrial() {
        return nameTrial;
    }

    /**
     * Método que nos permite saber si la prueba esta en uso
     * @return true si está en uso, false si no lo está
     */
    public boolean isInUse () {
        return inUse;
    }

    /**
     * Método que obtiene el nombre de la entidad
     * @return
     */
    public String getNameEntity() {
        return nameEntity;
    }

    /**
     * Método que obtiene el monto de la prueba
     * @return Entero con el monto de la prueba
     */
    public int getAmount() {
        return amount;
    }
}
