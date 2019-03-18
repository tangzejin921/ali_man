package com.jkwy.ali.man;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.sdk.android.man.MANHitBuilders;
import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;

/**
 * 阿里 反馈
 */
public class AliManApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init(this,getString(R.string.ali_man_appkey),getString(R.string.ali_man_appSecret));
    }

    public static void init(Application application, String appKey, String appSecret) {
        // 获取MAN服务
        MANService manService = MANServiceProvider.getService();

        if (UtilAliMan.isDebug(application)){
            // 打开调试日志，线上版本建议关闭
            manService.getMANAnalytics().turnOnDebug();
            // 若需要关闭 SDK 的自动异常捕获功能可进行如下操作(如需关闭crash report，建议在init方法调用前关闭crash),详见文档5.4
            manService.getMANAnalytics().turnOffCrashReporter();
            //NOTE 通过此接口关闭页面自动打点功能，详见文档4.2
            manService.getMANAnalytics().turnOffAutoPageTrack();
        }
        // 设置渠道（用以标记该app的分发渠道名称），如果不关心可以不设置即不调用该接口，渠道设置将影响控制台【渠道分析】栏目的报表展现。如果文档3.3章节更能满足您渠道配置的需求，就不要调用此方法，按照3.3进行配置即可；1.1.6版本及之后的版本，请在init方法之前调用此方法设置channel.
        manService.getMANAnalytics().setChannel(application.getString(R.string.ali_man_channel));

        if (TextUtils.isEmpty(appKey) && TextUtils.isEmpty(appSecret)) {
            // MAN初始化方法之一，从AndroidManifest.xml中获取appKey和appSecret初始化，若您采用上述 2.3中"统一接入的方式"，则使用当前init方法即可。
            manService.getMANAnalytics().init(application, application.getApplicationContext());
        } else {
            // MAN另一初始化方法，手动指定appKey和appSecret
            // 若您采用上述2.3中"统一接入的方式"，则无需使用当前init方法。
            // String appKey = "******";
            // String appSecret = "******";
            manService.getMANAnalytics().init(application, application.getApplicationContext(), appKey, appSecret);
        }
        // 若AndroidManifest.xml 中的 android:versionName 不能满足需求，可在此指定
        // 若在上述两个地方均没有设置appversion，上报的字段默认为null
        manService.getMANAnalytics().setAppVersion(UtilAliMan.getVersionName(application));
    }

    /**
     * 埋点
     */
    public static void send(String tag) {
        // 事件名称：play_music
        MANHitBuilders.MANCustomHitBuilder hitBuilder = new MANHitBuilders.MANCustomHitBuilder(tag);
        // 可使用如下接口设置时长：3分钟
//        hitBuilder.setDurationOnEvent(3 * 60 * 1000);
        // 设置关联的页面名称：聆听
//        hitBuilder.setEventPage("Listen");
        // 设置属性：类型摇滚
//        hitBuilder.setProperty("type", "rock");
        // 设置属性：歌曲标题
//        hitBuilder.setProperty("title", "wonderful tonight");
        // 发送自定义事件打点
        MANService manService = MANServiceProvider.getService();
        manService.getMANAnalytics().getDefaultTracker().send(hitBuilder.build());
    }

}
