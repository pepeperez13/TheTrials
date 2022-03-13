package business.typeTrials;

/**
 * Representa una de las pruebas que pueden incluir las ediciones
 * @author Abraham Cedeño
 * @author José Pérez
 */
public class PaperPublication {
    private String articleName;
    private String magazineName;
    private String quartile;
    private int acceptedProbability;
    private int revisedProbability;
    private int rejectedProbability;
    private boolean inUse;

    /**
     * Método constructor que crea un nuevo artículo, teniendo en cuenta si está en uso
     * @param name Nombre del artículo a publicar
     * @param magazine Nombre de la revista donde se publica
     * @param quartile Quartil de la revista
     * @param accepted Probabilidad de que el artículo sea aceptado
     * @param revised Probabilidad de que el artículo sea revisado
     * @param rejected Probabilidad de que el artículo sea rechazado
     * @param inUse Nos permitirá saber si el artículo se usa en alguna edición
     */
    public PaperPublication (String name, String magazine, String quartile, int accepted, int revised, int rejected, boolean inUse) {
        this.articleName = name;
        this.magazineName = magazine;
        this.quartile = quartile;
        this.acceptedProbability = accepted;
        this.revisedProbability = revised;
        this.rejectedProbability = rejected;
        this.inUse = inUse;
    }

    /**
     * Método constructor que crea un nuevo artículo, sin tener en cuenta si este está en uso
     * @param name Nombre del artículo a publicar
     * @param magazine Nombre de la revista donde se publica
     * @param quartile Quartil de la revista
     * @param accepted Probabilidad de que el artículo sea aceptado
     * @param revised Probabilidad de que el artículo sea revisado
     * @param rejected Probabilidad de que el artículo sea rechazado
     */
    public PaperPublication (String name, String magazine, String quartile, int accepted, int revised, int rejected) {
        this.articleName = name;
        this.magazineName = magazine;
        this.quartile = quartile;
        this.acceptedProbability = accepted;
        this.revisedProbability = revised;
        this.rejectedProbability = rejected;
        this.inUse = false;
    }

    /**
     * Método que nos permite saber el nombre de un artículo
     * @return El nombre del artículo
     */
    public String getArticleName() {
        return articleName;
    }

    /**
     * Método que nos permite saber el nombre de una revista
     * @return El nombre de la revista
     */
    public String getMagazineName() {
        return magazineName;
    }

    /**
     * Método que nos permite saber quartil de una revista
     * @return El quartil de la revista
     */
    public String getQuartile() {
        return quartile;
    }

    /**
     * Método que nos permite saber la probabilidad de un artículo de ser aceptado
     * @return Probabilidad de ser aceptado
     */
    public int getAcceptedProbability() {
        return acceptedProbability;
    }

    /**
     * Método que nos permite saber la probabilidad de un artículo de ser revisado
     * @return Probabilidad de ser revisado
     */
    public int getRevisedProbability() {
        return revisedProbability;
    }

    /**
     * Método que nos permite saber la probabilidad de un artículo de ser rechazado
     * @return Probabilidad de ser rechazado
     */
    public int getRejectedProbability() {
        return rejectedProbability;
    }

    /**
     * Método que nos permite saber si un artículo está en uso por alguna edición
     * @return true si está en uso, false si no lo está
     */
    public boolean InUse () {
        return this.inUse;
    }
}
