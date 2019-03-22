package com.jx.xjt.twelve.config;

import android.util.Log;

import com.jx.xjt.twelve.utils.CrashHandler;
import com.jx.xjt.twelve.utils.PreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ZbrApplication extends LitePalApplication {

    //首页当前显示页面
    public static int index = 0;

    //    修改默认地址：第一次登录时
    public static String BASE_URL = "http://192.168.1.7:8080/JXSW/";
    //    修改默认地址：第一次登录时

    public static String YEAR_MONTH = "";
    //public static User sUser;
    public static String USERID ; //抄表员编号

    //public static List<Arrearage> sCbInfoData = new ArrayList<>();
    public static int sCbWay = 0;  // 抄表方式。0：顺序抄表； 1：特殊抄表
    public static String sSectionId = "";

    public static String nfcSectionId = "";
    public static String CurrentNfcCardId = ""; // 当前抄表卡号
    public static String NFC_BIND_EMP_POSITION = ";系统管理员;NFC绑定;NFC标签绑定;nfc绑定;nfc标签绑定;";
    public static String downloadType = "downloadType"; // intabmanid,sectionid,alluser
    public static String DOWNLOADTYPE_INTABMANID = "intabmanid";
    public static String DOWNLOADTYPE_SECTIONID = "sectionid";

    public static boolean nfcCardIdState = false; // 是否有nfcCardId 字段。
    public static boolean showNfcBindView = false; // 是否显示NFC绑定卡号功能
    public static boolean fromNFCCbOrder = false; // 是否从NFC抄表页面进入抄表页面
    public static String NFC_CBSTATE_POSITION = ";系统管理员;顺序抄表;特殊抄表;";
    public static boolean cbState = true; // 是否可以顺序抄表或特殊抄表
    public static String NFC_ASCCBSTATE_POSITION = "顺序抄表";
    public static boolean ascCbState = true; // 是否可以顺序抄表
    public static String NFC_SPECIALBSTATE_POSITION = "特殊抄表";
    public static boolean specialCbState = true; // 是否可以特殊抄表

    // nfc卡号变更
    public static String NEWNFCCARDIDSUFFIX = "NEWNFCCARDIDSUFFIX"; // userid + "NEWNFCCARDIDSUFFIX"
    public static String NFCUPDATETYPESUFFIX = "NFCUPDATETYPESUFFIX"; // userid + "NFCUPDATETYPESUFFIX"
    public static Boolean isNewUpdate = true; // 是否为新添加变更
    public static String[] UPDATETYPEVALUES = {"变更", "解绑"};

    private static final String TAG = "XJTApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        String ip = PreferenceUtils.getPrefString(this, "ip", "192.168.1.7");
        String port = PreferenceUtils.getPrefString(this, "port", "8080");
        String dir = PreferenceUtils.getPrefString(this, "dir", "JXSW");

        BASE_URL = "http://" + ip + ":" + port + "/" + dir + "/";
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
        CrashHandler.getInstance().init(getApplicationContext());

    }


    /**
     * 读取本地用户登录数据，查询用户是否有绑定NFC标签功能。并初始化相应功能
     *
     * @param haveNFCCard 是否有NFCCardID字段
     * @param position    登录用户角色信息
     * @return 是否显示NFC绑定功能
     */
    public static boolean showNFCBindView(Boolean haveNFCCard, String position) {
        // 是否有卡号字段
        if (haveNFCCard) {
            ZbrApplication.nfcCardIdState = true;
            ZbrApplication.cbState = false;
            ZbrApplication.ascCbState = false;
            ZbrApplication.specialCbState = false;
        } else {
            ZbrApplication.nfcCardIdState = false;
            ZbrApplication.cbState = true;
            ZbrApplication.ascCbState = true;
            ZbrApplication.specialCbState = true;
        }

        ZbrApplication.showNfcBindView = false;
        // 是否有权限
        if (position.equals("")) {
            ZbrApplication.showNfcBindView = false;
        } else {
            String[] str = position.split(";");
            Log.e(TAG, "showNFCBindView: "+position );
            for (int i = 0; i < str.length && ZbrApplication.nfcCardIdState; i++) {
                if (ZbrApplication.NFC_BIND_EMP_POSITION.contains(";" + str[i] + ";")) {

                    ZbrApplication.showNfcBindView = true;
                }
                if (ZbrApplication.NFC_CBSTATE_POSITION.contains(";" + str[i] + ";")) {
                    ZbrApplication.cbState = true;
                    if (!ZbrApplication.NFC_ASCCBSTATE_POSITION.equals(str[i])
                            && !ZbrApplication.NFC_SPECIALBSTATE_POSITION.equals(str[i])) {
                        ZbrApplication.ascCbState = true;
                        ZbrApplication.specialCbState = true;
                    } else {
                        if (ZbrApplication.NFC_ASCCBSTATE_POSITION.equals(str[i])) {
                            ZbrApplication.ascCbState = true;
                        }
                        if (ZbrApplication.NFC_SPECIALBSTATE_POSITION.equals(str[i])) {
                            ZbrApplication.specialCbState = true;
                        }
                    }
                }
            }
        }
        return ZbrApplication.showNfcBindView;
    }
}
