package bg.nijel.xpixelexperience;

import android.annotation.SuppressLint;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;

import static de.robv.android.xposed.XposedBridge.log;

public class xPixelExperience implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {

        if (lpparam.packageName.equals("android")) {
            try {
                Class<?> apm = XposedHelpers.
                        findClass("android.app.ApplicationPackageManager",
                                lpparam.classLoader);
                XposedHelpers.findAndHookMethod(apm, "hasSystemFeature",
                        String.class, int.class, new XC_MethodHook(XCallback.PRIORITY_HIGHEST) {
                            @SuppressLint("PrivateApi")
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) {
                                Class<?> activityThread = XposedHelpers.
                                        findClass("android.app.ActivityThread",
                                                lpparam.classLoader);
                                String packageName = (String) XposedHelpers.callStaticMethod(activityThread,
                                        "currentPackageName");
                                String name = (String) param.args[0];
                                //log("nijel8 packageName=" + packageName + ", name=" + name);
                                if (packageName != null &&
                                        packageName.contains("com.google.android.apps.photos") &&
                                        name.contains("PIXEL_2021_EXPERIENCE") ||
                                        name.contains("PIXEL_2021_MIDYEAR_EXPERIENCE") ||
                                        name.contains("PIXEL_2020_EXPERIENCE") ||
                                        name.contains("PIXEL_2020_MIDYEAR_EXPERIENCE") ||
                                        name.contains("PIXEL_2019_EXPERIENCE") ||
                                        name.contains("PIXEL_2019_PRELOAD") ||
                                        name.contains("PIXEL_2019_MIDYEAR_EXPERIENCE")) {
                                    //log("nijel8 false packageName=" + packageName + ", name=" + name);
                                    param.setResult(false);
                                }
                            }
                        });



                /*Class<?> activityThread = XposedHelpers.
                        findClass("android.app.ActivityThread",
                                lpparam.classLoader);
                XposedHelpers.findAndHookMethod(activityThread, "currentPackageName",
                        new XC_MethodHook(XCallback.PRIORITY_HIGHEST) {
                            @SuppressLint("PrivateApi")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) {
                                log("nijel8 currentPackageName=" + param.getResult());
                            }
                        });*/

            } catch (Throwable t) {
                log(t);
            }
        }
    }
}
