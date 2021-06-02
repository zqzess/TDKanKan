package com.tdkankan.Tool;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.tdkankan.Cache.VolleyRequestQueueManager;
import com.tdkankan.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author ZQZESS
 * @date 2021/6/2.
 * @file HttpRequest
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class HttpRequest {
    public static void postJSONArray(String links, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, links, null, listener, errorListener);
        VolleyRequestQueueManager.mRequestQueue.add(request);
    }

    public static void postJSONObject(String links, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, links, null, listener, errorListener);
        VolleyRequestQueueManager.mRequestQueue.add(request);
    }

    public static void getString(String str, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(str, listener, errorListener);
        VolleyRequestQueueManager.mRequestQueue.add(request);
    }
}
