package com.jnetty.util.url;

/**
 * Created by wanghaiyang on 16/1/22.
 */
public class URLMatch {
    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * ant style path
     * @param url request url
     * @param pattern url pattern
     * @return
     */
    public static boolean match(String url, String pattern) {
        return antPathMatcher.match(pattern, url);
    }
}
