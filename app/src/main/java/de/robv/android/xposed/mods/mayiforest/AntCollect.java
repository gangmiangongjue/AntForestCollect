package de.robv.android.xposed.mods.mayiforest;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Method;

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


    private Class<?> h5FragmentManagerClass,h5FragmentClazz;

    private boolean finded = false;
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
                if (clazz.getName().equals("com.alipay.mobile.nebulacore.ui.H5Fragment")){
                    h5FragmentClazz = clazz;
                }
                if (clazz.getName().equals("com.alipay.mobile.nebulacore.ui.H5FragmentManager")){
                    h5FragmentManagerClass = clazz;
                }

                if (h5FragmentClazz !=null && h5FragmentManagerClass!=null && !finded){
                    finded = true;
                    Log.d(TAG, "afterHookedMethod find success");
                    XposedHelpers.findAndHookMethod(h5FragmentManagerClass, "pushFragment", h5FragmentClazz, boolean.class, Bundle.class, boolean.class, boolean.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            CollectHelper.curH5Fragment = param.args[0];
                            Log.d(TAG, "afterHookedMethod curH5Fragment: "+param.args[0].toString());
                        }
                    });
                }
                if (clazz.getName().equals(HOOK_CLASS_RPC)){
                    Log.d(TAG, "find class name: "+clazz.getName());
                    Method[] m = clazz.getDeclaredMethods();
                    for (int i=0;i<m.length;i++){
                        Log.d(TAG, "HOOK_CLASS_RPC method name: "+ m[i].getName());
                        if (m[i].getName().equals("rpcCall")){
                            XposedBridge.hookMethod(m[i], new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                    super.beforeHookedMethod(param);
                                    for (int i  = 0;i<param.args.length;i++){
                                        Log.d(TAG, "HOOK_CLASS_RPC param["+i+"]:"+param.args[i]);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
        final ClassLoader loader = lpparam.classLoader;


//        Class<?> h5PageClazz = loader.loadClass("com.alipay.mobile.h5container.api.H5Page");
//        Class<?> jsonClazz = loader.loadClass("com.alibaba.fastjson.JSONObject");
//        Class<?> rpcClazz = loader.loadClass("com.alipay.mobile.nebulabiz.rpc.H5RpcUtil");
//        if (rpcClazz !=null&&jsonClazz!=null && h5PageClazz!=null){
//            XposedHelpers.findAndHookMethod(rpcClazz, "rpcCall", String.class, String.class, String.class, boolean.class, jsonClazz, String.class, boolean.class, h5PageClazz, int.class, String.class, boolean.class, int.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                    for (int i =0;i<param.args.length;i++){
//                        Log.d(TAG, "beforeHookedMethod args["+i+"]:"+param.args[i]);
//                        Log.d(TAG, "beforeHookedMethod result: "+param.getResult());
//                        Log.d(TAG, "beforeHookedMethod rpc obj: "+param.thisObject);
//                    }
//                }
//
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    Object resp = param.getResult();
//                    if (resp != null){
//                        Method method = resp.getClass().getMethod("getResponse",new Class<?>[]{});
//                        String response = (String) method.invoke(resp,new Object[]{});
//                        Log.d(TAG, "afterHookedMethod response: "+response);
//                        if (CollectHelper.isRankList(response)){
//                            CollectHelper.autoGetCanCollectUserIdList(loader,response);
//                        }
//                        if (CollectHelper.isUserDetail(response)){
//                            CollectHelper.autoGetCanCollectBubbleIdList(loader,response);
//                        }
//                    }
//                }
//            });
//        }
    }
}