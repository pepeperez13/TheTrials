package business.typeTrials;

public class Budget {
    private String nameTrial;
    private String nameEntity;
    private int amount;
    private boolean inUse;

    public Budget(String nameTrial, String nameEntity, int amount, boolean inUse) {
        this.nameTrial = nameTrial;
        this.nameEntity = nameEntity;
        this.amount = amount;
        this.inUse = inUse;
    }

    public String getNameTrial() {
        return nameTrial;
    }

    public boolean isInUse () {
        return inUse;
    }

    public String getNameEntity() {
        return nameEntity;
    }

    public int getAmount() {
        return amount;
    }
}
