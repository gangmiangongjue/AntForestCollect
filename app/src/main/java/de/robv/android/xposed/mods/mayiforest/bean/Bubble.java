package de.robv.android.xposed.mods.mayiforest.bean;

public class Bubble {
    private BubbleBusiness business;
    private boolean canHelpCollect;
    private int fullEnergy;
    private int id;
    private long produceTime;
    private int remainEnergy;
    private String userId;

    public BubbleBusiness getBusiness() {
        return business;
    }

    public void setBusiness(BubbleBusiness business) {
        this.business = business;
    }

    public boolean isCanHelpCollect() {
        return canHelpCollect;
    }

    public void setCanHelpCollect(boolean canHelpCollect) {
        this.canHelpCollect = canHelpCollect;
    }

    public int getFullEnergy() {
        return fullEnergy;
    }

    public void setFullEnergy(int fullEnergy) {
        this.fullEnergy = fullEnergy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(long produceTime) {
        this.produceTime = produceTime;
    }

    public int getRemainEnergy() {
        return remainEnergy;
    }

    public void setRemainEnergy(int remainEnergy) {
        this.remainEnergy = remainEnergy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
