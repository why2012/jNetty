package com.jnetty.util.url;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public class URLMatch {

    /**
     * ant style path
     * @param url request url
     * @param pattern url pattern
     * @return
     */
    public static boolean match(String url, String pattern) {
        //TODO: ant style match
        return url.startsWith(pattern);
    }
}
