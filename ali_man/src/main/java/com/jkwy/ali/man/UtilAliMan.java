package com.jkwy.ali.man;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Copyright © 2019 健康无忧网络科技有限公司<br>
 * Author:      唐泽金 tangzejin921@qq.com<br>
 * Version:     1.0.0<br>
 * Date:        2019/3/18 13:41<br>
 * Description: 工具类
 */
public class UtilAliMan {

    public static boolean isDebug(Context ctx){
        try {
            return (boolean) getValue(ctx,"DEBUG");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BuildConfig.DEBUG;
    }


    /**
     * 获取版本号
     */
    public static int getVersionCode(Context ctx) {
        try {
            return (int)getValue(ctx,"VERSION_CODE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BuildConfig.VERSION_CODE;
    }

    public static String getVersionName(Context ctx) {
        try {
            return getValue(ctx,"VERSION_NAME").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BuildConfig.VERSION_NAME;
    }


    public static Object getValue(Context ctx,String name) throws Exception {
        String clspath = ctx.getPackageName()+".BuildConfig";
        Class<?> c = Class.forName(clspath);
        Object o = c.newInstance();
        Field field = getField(c, name);
        return field.get(o);
    }

    /**
     * 获取字段
     */
    public static Field getField(Class cls, String name) throws NoSuchFieldException {
        Field field = null;
        try {
            field = cls.getField(name);//类及其父类的public 字段.
        } catch (NoSuchFieldException e) {
            field  = getDeclaredField(cls,name);
        }
        field.setAccessible(true);
        return field;
    }
    /**
     * 类本身以及父类的所有字段.
     */
    private static Field getDeclaredField(Class cls, String name) throws NoSuchFieldException {
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            try {
                Field result = c.getDeclaredField(name);
                if (result !=null) {
                    return result;
                }
            } catch (NoSuchFieldException e) {
            }
        }
        throw new NoSuchFieldException();
    }

}
