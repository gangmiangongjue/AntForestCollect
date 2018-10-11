package de.robv.android.xposed.mods.mayiforest.bean;

import java.util.List;

public class BubblesBean {
    private String bizNo;
    private List<Bubble> bubbles;
    private String combineHandlerVOMap;//indeed it should be an object
    private boolean needGuide;
    private String nextAction;
    private long now;
    private String propertiesl;//indeed it should be an object
    private String resultCode;
    private String resultDesc;
    private boolean retriable;
    private boolean success;
    private int totalActivityCertificates;
    private String treeEnergy;//indeed it should be an object

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public List<Bubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(List<Bubble> bubbles) {
        this.bubbles = bubbles;
    }

    public String getCombineHandlerVOMap() {
        return combineHandlerVOMap;
    }

    public void setCombineHandlerVOMap(String combineHandlerVOMap) {
        this.combineHandlerVOMap = combineHandlerVOMap;
    }

    public boolean isNeedGuide() {
        return needGuide;
    }

    public void setNeedGuide(boolean needGuide) {
        this.needGuide = needGuide;
    }

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public String getPropertiesl() {
        return propertiesl;
    }

    public void setPropertiesl(String propertiesl) {
        this.propertiesl = propertiesl;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public boolean isRetriable() {
        return retriable;
    }

    public void setRetriable(boolean retriable) {
        this.retriable = retriable;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotalActivityCertificates() {
        return totalActivityCertificates;
    }

    public void setTotalActivityCertificates(int totalActivityCertificates) {
        this.totalActivityCertificates = totalActivityCertificates;
    }

    public String getTreeEnergy() {
        return treeEnergy;
    }

    public void setTreeEnergy(String treeEnergy) {
        this.treeEnergy = treeEnergy;
    }
}
