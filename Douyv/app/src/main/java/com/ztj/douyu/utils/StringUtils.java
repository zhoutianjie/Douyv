package com.ztj.douyu.utils;

/**
 * Created by zhoutianjie on 2018/7/12.
 */

public class StringUtils {

    public static boolean isNull(String str) {
        boolean b = false;
        if (str == null || str.trim().length() == 0) b = true;

        return b;
    }
}
