package com.tdkankan.Cache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tdkankan.MainActivity;
import com.tdkankan.MyApplication;

/**
 * @author ZQZESS
 * @date 1/7/2021.
 * @file VolleyRequestQueueManager
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class VolleyRequestQueueManager {
    /**
     * 请求队列处理类
     * 获取RequestQueue对象
     */
    // 获取请求队列类
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(MyApplication.newInstance());

    //添加任务进任务队列
    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    //取消任务
    public static void cancelRequest(Object tag){
        mRequestQueue.cancelAll(tag);
    }



}
