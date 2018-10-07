package de.robv.android.xposed.mods.mayiforest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class CollectHelper {
    private final static String TAG  = CollectHelper.class.getSimpleName();
    private static boolean isWebViewRefresh = false;
    private static ArrayList<String> friendRankUserIdList = new ArrayList<>();
    private static int pageCount = 0;
    public static Object curH5Fragment;
    private static Object curH5PageImpl;

    public static void autoGetCanCollectUserIdList(final ClassLoader loader,String response){
            if (isWebViewRefresh){
                return;
            }
            boolean isFinish = parseFriendRankPageDataResponse(response);
            if (isFinish){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rpcCallFriendRankList(loader);
                    }
                }).start();
            }else {
                if (friendRankUserIdList.size()>0){
                    for (String userId : friendRankUserIdList){
                        rpcCallCanCollectEnergy(loader,userId);
                    }
                }
            }
            refreshWebview();
    }

    public static void autoGetCanCollectBubbleIdList(final ClassLoader loader,String response){

    }

    private static void rpcCallFriendRankList(ClassLoader loader){
        try{
            Method rpcCallMethod = getRpcCallMethod(loader);
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

    private static void rpcCallCanCollectEnergy(ClassLoader loader,String userId){
        try{
            Method rpcCallMethod = getRpcCallMethod(loader);
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

    public static void rpcCallCollectEnergy(final ClassLoader loader,String userId,Integer bubble){
        try{
            Method rpcCallMethod = getRpcCallMethod(loader);
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

    public static boolean isRankList(String response){
        Log.d(TAG, "isRankList: "+response);
        return false;
    }

    public static boolean isUserDetail(String response){
        Log.d(TAG, "isUserDetail: "+response);
        return false;
    }

    private static boolean parseFriendRankPageDataResponse(String response){
        Log.d(TAG, "parseFriendRankPageDataResponse: "+response);
        return false;
    }

    private static boolean parseCollectEnergyResponse(String response){
        return false;
    }



    private static void refreshWebview(){

    }

    private static Method getRpcCallMethod(ClassLoader loader){
        Method result = null;
        try {
            Field aF = curH5Fragment.getClass().getDeclaredField("a");
            aF.setAccessible(true);
            Object viewHolder = aF.get(curH5Fragment);
            Field hF = viewHolder.getClass().getDeclaredField("h");
            hF.setAccessible(true);
            curH5PageImpl = hF.get(viewHolder);
            Class<?> h5PageClazz = loader.loadClass("com.alipay.mobile.h5container.api.H5Page");
            Class<?> jsonClazz = loader.loadClass("com.alibaba.fastjson.JSONObject");
            Class<?> rpcClazz = loader.loadClass("com.alipay.mobile.nebulabiz.rpc.H5RpcUtil");
            if (curH5PageImpl !=null){
                result = rpcClazz.getMethod("rpcCall",String.class,String.class,String.class,boolean.class,jsonClazz,String.class,boolean.class,h5PageClazz,int.class,String.class,boolean.class,int.class);
                Log.d(TAG, "getRpcCallMethod success ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
