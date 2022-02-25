package business.typeTrials;

public class MasterStudies {
    private String name;
    private String nom;
    private int numberCredits;
    private int probability;
    private boolean inUse;

    public MasterStudies(String name, String nom, int numberCredits, int probability, boolean inUse) {
        this.name = name;
        this.nom = nom;
        this.numberCredits = numberCredits;
        this.probability = probability;
        this.inUse = inUse;
    }

    public String getName() {
        return name;
    }

    public String getNom() {
        return nom;
    }

    public boolean isInUse() {
        return inUse;
    }

    public int getNumberCredits() {
        return numberCredits;
    }

    public int getProbability() {
        return probability;
    }
}
