package com.jx.xjt.twelve.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 记录崩溃日志
 */
public class CrashHandler implements UncaughtExceptionHandler {
    //TODO:具体项目请修改此文件目录
    private static final String dir = Environment.getExternalStorageDirectory().getPath() + "/" + "XJT" + "/log";
    private static final int FILE_MAX_SIZE = 1024 * 1024;

    //系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    private static boolean isDebug = true;

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) return false;
        if (!isDebug) return false;
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {

    }

    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return
     */
    private void saveCrashInfo2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        String error = sb.toString();
        if (!TextUtils.isEmpty(error)) {
            BufferedWriter mBufferedWriter = null;
            try {
                File mFile = new File(new StringBuilder(mkDir(dir).getPath() + File.separator).append("error_log").append("_").append(new SimpleDateFormat("yyyyMMdd_HH").format(new Date())).append(".txt").toString());
                if (mFile.length() > FILE_MAX_SIZE) {
                    mFile.delete();
                    mFile.createNewFile();
                }
                Date date = new Date();
                // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String time = ">>>>>>>>>>>>>>>>>> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(date) + "\n";
                mBufferedWriter = new BufferedWriter(new FileWriter(mFile, true));
                mBufferedWriter.append(time);
                mBufferedWriter.append(error);
                mBufferedWriter.append("\n\n");
                mBufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (mBufferedWriter != null) {
                    try {
                        mBufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            int flags = packageInfo.applicationInfo.flags;
            int debug = (flags & ApplicationInfo.FLAG_DEBUGGABLE);
            if (debug != 0) {
                // development mode
                isDebug = true;
            } else {
                // release mode
                isDebug = false;
            }
        } catch (NameNotFoundException e) {

            e.printStackTrace();
        }

    }

    /**
     * 创建文件目录
     *
     * @param dir
     *            如：/sdcard/log
     * @return
     */
    public File mkDir(String dir) {
        if (isSDCARDMounted()) {
            File file = null;
            try {
                file = new File(dir);
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 判断SDCARD是否有效
     */
    private boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }
}
