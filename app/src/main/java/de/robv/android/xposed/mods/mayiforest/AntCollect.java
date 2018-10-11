package de.robv.android.xposed.mods.mayiforest;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class AntCollect implements IXposedHookLoadPackage {
    private final static String TAG = AntCollect.class.getSimpleName();
    private final static String ALI_PACKAGE = "com.eg.android.AlipayGphone";
    private final static String HOOK_CLASS_LOG = "com.alipay.mobile.nebula.util.H5Log";

    private final static String HOOK_CLASS_RPC = "com.alipay.mobile.nebulabiz.rpc.H5RpcUtil";
    private final static String HOOK_CLASS_H5FRAGMENT = "com.alipay.mobile.nebulacore.ui.H5Fragment";
    private final static String HOOK_CLASS_H5FRAGMENTMANAGER = "com.alipay.mobile.nebulacore.ui.H5FragmentManager";
    public final static String HOOK_CLASS_H5PAGE = "com.alipay.mobile.h5container.api.H5Page";
    private final static String HOOK_CLASS_JSONOBJECT = "com.alibaba.fastjson.JSONObject";


    public static Class<?> h5FragmentManagerClass, h5FragmentClazz, h5pageClass, jsonObjectClass, h5RpcUtilClass;

    private boolean finded = false;
    private ArrayList<Object> responses = new ArrayList<>();


    private void handleResponse () throws Throwable{
        for (Object resp : responses){
            if (resp != null) {
                Log.d(TAG, "handleResponse name:"+resp.getClass().getSimpleName()+" content:"+resp.toString());
                Method method = resp.getClass().getMethod("getResponse", new Class<?>[]{});
                String response = (String) method.invoke(resp, new Object[]{});
                Log.d(TAG, "handleResponse afterHookedMethod response: " + response);
                if (CollectHelper.isRankList(response)) {
                    CollectHelper.autoGetCanCollectUserIdList(response);
                }
                if (CollectHelper.isUserDetail(response)) {
                    CollectHelper.autoGetCanCollectBubbleIdList(response);
                }
            }
        }
        responses.clear();
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.contains(ALI_PACKAGE))
            return;
        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                if (param.hasThrowable()) return;
                Class<?> clazz = (Class<?>) param.getResult();
                switch (clazz.getName()) {
                    case HOOK_CLASS_H5FRAGMENT:
                        h5FragmentClazz = clazz;
                        break;
                    case HOOK_CLASS_H5FRAGMENTMANAGER:
                        h5FragmentManagerClass = clazz;
                        break;
                    case HOOK_CLASS_H5PAGE:
                        h5pageClass = clazz;
                        break;
                    case HOOK_CLASS_JSONOBJECT:
                        jsonObjectClass = clazz;
                        break;
                    case HOOK_CLASS_RPC:
                        h5RpcUtilClass = clazz;
                        break;
                    default:
                        break;
                }
                //获取必要的类，来获取界面fragment的实例，以便获取最终需要rcpcall需要的H5PageImpl参数
                if (h5FragmentClazz != null && h5FragmentManagerClass != null && h5pageClass != null && jsonObjectClass != null && h5RpcUtilClass != null && !finded) {
                    Log.d(TAG, "afterHookedMethod find success");
                    finded = true;
                    XposedHelpers.findAndHookMethod(h5FragmentManagerClass, "pushFragment", h5FragmentClazz, boolean.class, Bundle.class, boolean.class, boolean.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            CollectHelper.curH5Fragment = param.args[0];
                            Log.d(TAG, "afterHookedMethod curH5Fragment: " + param.args[0].toString());
                        }
                    });
                }

                //每次call rpc都会回调该方法，使用这个获得的response来获取需要的朋友和自己的能量信息
                if (clazz.getName().equals(HOOK_CLASS_RPC)) {
                    Log.d(TAG, "find class name: " + clazz.getName());
                    Method[] m = clazz.getDeclaredMethods();
                    for (int i = 0; i < m.length; i++) {
                        Log.d(TAG, "HOOK_CLASS_RPC method name: " + m[i].getName());
                        if (m[i].getName().equals("rpcCall")) {
                            XposedBridge.hookMethod(m[i], new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                    super.beforeHookedMethod(param);
                                    for (int i = 0; i < param.args.length; i++) {
                                        Log.d(TAG, "HOOK_CLASS_RPC param[" + i + "]:" + param.args[i]);
                                    }
                                }

                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    Log.d(TAG, "afterHookedMethod response come in: ");
                                    Object res = param.getResult();
                                    responses.add(res);
                                    if(finded){//只有页面完全加载好才开始处理请求
                                        handleResponse();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
        final ClassLoader loader = lpparam.classLoader;

    }
}
