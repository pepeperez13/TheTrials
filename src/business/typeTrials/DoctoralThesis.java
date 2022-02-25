package business.typeTrials;

public class DoctoralThesis {
    private String name;
    private String fieldOfStudy;
    private int difficulty;
    private boolean inUse;

    public DoctoralThesis(String name, String fieldOfStudy, int difficulty, boolean inUse) {
        this.name = name;
        this.fieldOfStudy = fieldOfStudy;
        this.difficulty = difficulty;
        this.inUse = inUse;
    }

    public String getName() {
        return name;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public boolean isInUse() {
        return inUse;
    }

    public int getDifficulty() {
        return difficulty;
    }
}