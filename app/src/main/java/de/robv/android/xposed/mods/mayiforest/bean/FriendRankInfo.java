package de.robv.android.xposed.mods.mayiforest.bean;

public class FriendRankInfo {
    private boolean canCollectEnergy;
    private long canCollectLaterTime;
    private boolean canHelpCollect;
    private int collectableBubbleCount;
    private String displayName;
    private int energySummation;
    private boolean forestUser;
    private String headPortrait;
    private int rank;
    private boolean realName;
    private String treeAmount;
    private String userId;

    public boolean isCanCollectEnergy() {
        return canCollectEnergy;
    }

    public void setCanCollectEnergy(boolean canCollectEnergy) {
        this.canCollectEnergy = canCollectEnergy;
    }

    public long getCanCollectLaterTime() {
        return canCollectLaterTime;
    }

    public void setCanCollectLaterTime(long canCollectLaterTime) {
        this.canCollectLaterTime = canCollectLaterTime;
    }

    public boolean isCanHelpCollect() {
        return canHelpCollect;
    }

    public void setCanHelpCollect(boolean canHelpCollect) {
        this.canHelpCollect = canHelpCollect;
    }

    public int getCollectableBubbleCount() {
        return collectableBubbleCount;
    }

    public void setCollectableBubbleCount(int collectableBubbleCount) {
        this.collectableBubbleCount = collectableBubbleCount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getEnergySummation() {
        return energySummation;
    }

    public void setEnergySummation(int energySummation) {
        this.energySummation = energySummation;
    }

    public boolean isForestUser() {
        return forestUser;
    }

    public void setForestUser(boolean forestUser) {
        this.forestUser = forestUser;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isRealName() {
        return realName;
    }

    public void setRealName(boolean realName) {
        this.realName = realName;
    }

    public String getTreeAmount() {
        return treeAmount;
    }

    public void setTreeAmount(String treeAmount) {
        this.treeAmount = treeAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
