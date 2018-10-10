package de.robv.android.xposed.mods.mayiforest.bean;

import java.util.ArrayList;

public class RankingBean {
    private ArrayList<FriendRankInfo> friendRanking = new ArrayList<>();
    private boolean hasMore;
    private long queryTimestamp;
    private String resultCode;
    private String resultDesc;
    private boolean retriable;
    private boolean success;
    private FriendRankInfo myself;
    private String nextStartPoint;

    public ArrayList<FriendRankInfo> getFriendRankInfos() {
        return friendRanking;
    }

    public void setFriendRankInfos(ArrayList<FriendRankInfo> friendRankBeans) {
        this.friendRanking = friendRankBeans;
    }

    public boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public long getQueryTimestamp() {
        return queryTimestamp;
    }

    public void setQueryTimestamp(long queryTimestamp) {
        this.queryTimestamp = queryTimestamp;
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

    @Override
    public String toString() {
        return "hasMore:"+hasMore+" queryTimestamp:"+queryTimestamp+" resultCode:"+resultCode+" resultDesc:"+resultDesc+" retriable:"+retriable+" success:"+success+" friendRankBeans size:"+friendRanking.size();
    }

    public FriendRankInfo getMyself() {
        return myself;
    }

    public void setMyself(FriendRankInfo myself) {
        this.myself = myself;
    }

    public String getNextStartPoint() {
        return nextStartPoint;
    }

    public void setNextStartPoint(String nextStartPoint) {
        this.nextStartPoint = nextStartPoint;
    }
}
