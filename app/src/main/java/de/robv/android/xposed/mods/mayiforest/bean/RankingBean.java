package de.robv.android.xposed.mods.mayiforest.bean;

import java.util.ArrayList;

public class RankingBean {
    private ArrayList<FriendRankInfo> friendRankBeans = new ArrayList<>();
    private String hasMore;
    private long queryTimestamp;
    private String resultCode;
    private String resultDesc;
    private boolean retriable;
    private boolean success;

    public ArrayList<FriendRankInfo> getFriendRankInfos() {
        return friendRankBeans;
    }

    public void setFriendRankInfos(ArrayList<FriendRankInfo> friendRankBeans) {
        this.friendRankBeans = friendRankBeans;
    }

    public String getHasMore() {
        return hasMore;
    }

    public void setHasMore(String hasMore) {
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
        return "hasMore:"+hasMore+" queryTimestamp:"+queryTimestamp+" resultCode:"+resultCode+" resultDesc:"+resultDesc+" retriable:"+retriable+" success:"+success+" friendRankBeans size:"+friendRankBeans.size();
    }
}
