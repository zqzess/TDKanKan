package com.tdkankan.Cache;

import com.tdkankan.Reptile.GetAndRead;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZQZESS
 * @date 1/6/2021.
 * @file BookContentCache
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookContentCache {
    private static ConcurrentHashMap<String, String> cacheMap = new ConcurrentHashMap<>();

    /**
     * 获取缓存的对象
     *
     * @param url
     * @return
     */
    public static String getCache(String url) {

//        url = getCacheKey(url);
        // 如果缓冲中有该链接，则返回value
        if (cacheMap.containsKey(url)) {
            return cacheMap.get(url);
        }
        // 如果缓存中没有该链接，把该帐号对象缓存到concurrentHashMap中
        initCache(url);
        return cacheMap.get(url);
    }

    /**
     * 初始化缓存
     *
     * @param url
     */
    private static void initCache(String url) {
        String content= GetAndRead.GetBookContent(url);
        if(!content.isEmpty())
        {
            cacheMap.put(url, content);
        }
//        final String url2=url;
//        Thread thread1=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String content=GetBook.GetBookContent(url2);
//                if(!content.isEmpty())
//                {
//                    cacheMap.put(url2, content);
//                }
//            }
//        });
//        thread1.start();
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * 拼接一个缓存key
     *
     * @param url
     * @return
     */
//    private static String getCacheKey(String url) {
//        return Thread.currentThread().getId() + "-" + url;
//    }

    /**
     * 移除缓存信息
     *
     * @param url
     */
    public static void removeCache(String url) {
//        cacheMap.remove(getCacheKey(url));
        cacheMap.remove(url);
    }

    /**
     * 清除所有缓存
     *
     */

    public static void removeAll()
    {
        cacheMap.clear();
    }
}
