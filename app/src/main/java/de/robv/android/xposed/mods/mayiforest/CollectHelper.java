package de.robv.android.xposed.mods.mayiforest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.robv.android.xposed.mods.mayiforest.bean.FriendRankInfo;
import de.robv.android.xposed.mods.mayiforest.bean.RankingBean;

public class CollectHelper {
    private final static String TAG  = CollectHelper.class.getSimpleName();

    private static boolean isWebViewRefresh = false;
    private static ArrayList<String> friendRankUserIdList = new ArrayList<>();
    private final static String FIELD_FRIEND_RANK = "friendRanking";
    private static int pageCount = 0;
    static Object curH5Fragment;
    private static Object curH5PageImpl;
    private static Gson gson = new Gson();

    public static void autoGetCanCollectUserIdList(String response){
        Log.d(TAG, "autoGetCanCollectUserIdList: ");
            if (isWebViewRefresh){
                return;
            }
            boolean isFinish = parseFriendRankPageDataResponse(response);
            if (!isFinish){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rpcCallFriendRankList();
                    }
                }).start();
            }else {
                if (friendRankUserIdList.size()>0){
                    for (String userId : friendRankUserIdList){
                        rpcCallCanCollectEnergy(userId);
                    }
                    friendRankUserIdList.clear();
                }
            }
            refreshWebview();
    }

    public static void autoGetCanCollectBubbleIdList(String response){

    }

    /**
     * 发送朋友排名请求
     * */
    private static void rpcCallFriendRankList(){
        Log.d(TAG, "rpcCallFriendRankList: ");
        try{
            Method rpcCallMethod = getRpcCallMethod();
            if (rpcCallMethod ==null) return;
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("av","5");
            jsonObject.put("ct","android");
            jsonObject.put("pageSize",pageCount*20);
            jsonObject.put("startPoint",""+(pageCount*20+1));
            jsonArray.put(jsonObject);
            rpcCallMethod.invoke(null,"alipay.antmember.forest.h5.queryEnergyRanking",jsonArray.toString(),"",true,null,null,false,curH5PageImpl,0,"",false,-1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据指定userid获取该用户的能量信息
     * */
    private static void rpcCallCanCollectEnergy(String userId){
        Log.d(TAG, "rpcCallCanCollectEnergy useid: "+userId+" size:"+friendRankUserIdList.size()+"curH5PageImpl:"+curH5PageImpl);
        try{
            Method rpcCallMethod = getRpcCallMethod();
            if (rpcCallMethod ==null) return;
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("av","5");
            jsonObject.put("ct","android");
            jsonObject.put("pageSize",3);
            jsonObject.put("startIndex",0);
            jsonObject.put("userId",userId);
            jsonArray.put(jsonObject);
            rpcCallMethod.invoke(null,"alipay.antmember.forest.h5.queryNextAction",jsonArray.toString(),"",true,null,null,false,curH5PageImpl,0,"",false,-1);
            rpcCallMethod.invoke(null,"alipay.antmember.forest.h5.pageQueryDynamics",jsonArray.toString(),"",true,null,null,false,curH5PageImpl,0,"",false,-1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * */
    public static void rpcCallCollectEnergy(String userId,Integer bubble){
        try{
            Method rpcCallMethod = getRpcCallMethod();
            if (rpcCallMethod ==null) return;
            JSONArray jsonArray = new JSONArray();
            JSONArray bubbleArray = new JSONArray();
            bubbleArray.put(bubble);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("av","5");
            jsonObject.put("ct","android");
            jsonObject.put("bubbleIds",bubbleArray);
            jsonObject.put("userId",userId);
            jsonArray.put(jsonObject);
            Object response = rpcCallMethod.invoke(null,"alipay.antmember.forest.h5.collectEnergy",jsonArray.toString(),"",true,null,null,false,curH5PageImpl,0,"",false,-1);
            Method method = response.getClass().getMethod("getResponse",new Class<?>[]{});
            String responseStr = (String) method.invoke(response,new Object[]{});
            boolean isSuccess = parseCollectEnergyResponse(responseStr);
            Log.d(TAG, "rpcCallCollectEnergy: ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 如果当前response是排名的（包括刚进入和后来刷新的），解析并把所有的userid保存起来
     * */
    public static boolean isRankList(String response){
        Log.d(TAG, "isRankList: "+response);
        return response.contains(FIELD_FRIEND_RANK);
    }
    /**
     * 用户的详细能量情况
     * */
    public static boolean isUserDetail(String response){
        Log.d(TAG, "isUserDetail: "+response);
        return false;
    }

    private static boolean parseFriendRankPageDataResponse(String response){
        RankingBean rankingBean = gson.fromJson(response,RankingBean.class);
        for (FriendRankInfo rankInfo :rankingBean.getFriendRankInfos()){
            if (!friendRankUserIdList.contains(rankInfo.getUserId()))
            friendRankUserIdList.add(rankInfo.getUserId());
        }
        Log.d(TAG, "parseFriendRankPageDataResponse: "+rankingBean);
//        return !rankingBean.getHasMore();
        return true;//请求会有问题
    }

    private static boolean parseCollectEnergyResponse(String response){
        return false;
    }



    private static void refreshWebview(){

    }

    private static Method getRpcCallMethod(){
        Method result = null;
        try {
            Log.d(TAG, "getRpcCallMethod curH5Fragment: " + CollectHelper.curH5Fragment);
            Field aF = curH5Fragment.getClass().getDeclaredField("a");
            aF.setAccessible(true);
            Object viewHolder = aF.get(curH5Fragment);
            Field hF = viewHolder.getClass().getDeclaredField("h");
            hF.setAccessible(true);
            curH5PageImpl = hF.get(viewHolder);
            Class<?> h5PageClazz = AntCollect.h5pageClass;
            Class<?> jsonClazz = AntCollect.jsonObjectClass;
            Class<?> rpcClazz = AntCollect.h5RpcUtilClass;
            if (curH5PageImpl !=null){
                result = rpcClazz.getMethod("rpcCall",String.class,String.class,String.class,boolean.class,jsonClazz,String.class,boolean.class,h5PageClazz,int.class,String.class,boolean.class,int.class);
                Log.d(TAG, "getRpcCallMethod success curH5PageImpl is :" + curH5PageImpl);
            }else {
                Log.d(TAG, "getRpcCallMethod: rpc call method is null ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
