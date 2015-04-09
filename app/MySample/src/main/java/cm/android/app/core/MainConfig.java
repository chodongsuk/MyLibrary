package cm.android.app.core;

import android.content.Context;

import ch.qos.logback.classic.Level;
import cm.android.framework.core.global.WorkDir;
import cm.android.framework.ext.MyAppConfig;
import cm.android.log.LogConfig;

public class MainConfig extends MyAppConfig {

    public static final String DOWNLOAD = "download/";

    public static final String LOG_DIR = "log/";


    @Override
    protected void initDatabase() {
        // 初始化数据库版本
        // DatabaseConfig.initVersion(1);

        // 初始化数据库表
        // DatabaseConfig.initTable(ApkBean.class);
    }

    @Override
    public void initWorkDir(Context context) {
        WorkDir.initWorkDir(context);
    }

    @Override
    public void initLog(Context context) {
        LogConfig.configLogback(Level.ALL, WorkDir.getDir(LOG_DIR));
    }

    private static final class StoreRoot {

        /**
         * 测试
         */
        private static final String TEST = "http://192.168.1.72:8080";

        /**
         * 正式环境
         */
        private static final String OFFICIAL = "http://";
    }

    /**
     * IP
     */
    public static String SERVER_ROOT = StoreRoot.TEST;

}
