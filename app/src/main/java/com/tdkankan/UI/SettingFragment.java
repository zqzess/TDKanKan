package com.tdkankan.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hb.dialog.dialog.LoadingDialog;
import com.tdkankan.R;
import com.tdkankan.Service.DownloadService;
import com.tdkankan.Tool.ApkVersionCodeUtils;
import com.tdkankan.Tool.HttpRequest;
import com.tdkankan.ViewUitl.RightAndLeftTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

/**
 * @author ZQZESS
 * @date 12/8/2020-9:42 PM
 * @file SettingFragment.java
 * GitHub：https://github.com/zqzess
 *不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class SettingFragment extends Fragment {
    RightAndLeftTextView tv_update;
    String versionName;
    Context context;
    String downurl="";
    String verName="";
    String updateinfo="";
    AlertDialog.Builder mDialog;
    MyReceiver receiver = new MyReceiver();
    LoadingDialog loadingDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);
        initView(view);
        return view;
    }
    void initView(View view)
    {
        context=this.getActivity();
        loadingDialog=new LoadingDialog(context);
        versionName= ApkVersionCodeUtils.getVerName(context);
        tv_update=(RightAndLeftTextView) view.findViewById(R.id.setting_update);
        tv_update.setmLeftText("当前版本号:"+versionName);
        tv_update.setmRightText("检查更新 >");
        clickEvent();
        regist();
    }
    void clickEvent()
    {
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadingDialog.setMessage("");
                    loadingDialog.show();
                    HttpRequest.postJSONObject("https://api.github.com/repos/zqzess/TDKanKan/releases/latest", new Response.Listener<JSONObject >() {

                        @Override
                        public void onResponse(JSONObject  jsonObject) {
                            try {
                                     verName=jsonObject.getString("tag_name");
                                     updateinfo=jsonObject.getString("body");
                                    if (jsonObject.getJSONArray("assets") != null && jsonObject.getJSONArray("assets").length() > 0) {
                                        JSONArray Array = jsonObject.getJSONArray("assets");
                                        for (int i = 0; i < Array.length(); i++) {
                                            JSONObject object = Array.getJSONObject(i);
                                            downurl=object.getString("browser_download_url");
//                                            Log.d("downurl",verName+"->"+downurl);
                                            loadingDialog.dismiss();
                                            UpdateTipDialog(context,verName,downurl,updateinfo);
                                        }
                                    }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,"网络请求错误，获取更新失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void UpdateTipDialog(final Context context, final String verName, final String downUrl, final String updateinfo)
    {
        mDialog = new AlertDialog.Builder(context);
        mDialog.setTitle("土豆阅读又更新咯！");
        mDialog.setMessage(updateinfo);
        mDialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent serviceIntent = new Intent(context, DownloadService.class);
                //将下载地址url放入intent中
//                Log.d("downurl",downUrl);
                serviceIntent.setData(Uri.parse(downUrl));
                SettingFragment.this.getActivity().startService(serviceIntent);
            }
        });
        //设置反面按钮
        mDialog.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.setCancelable(false).create().show();
    }

    private void regist() {

        IntentFilter intentFilter = new IntentFilter(DownloadService.BROADCAST_ACTION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, intentFilter);
    }
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(DownloadService.EXTENDED_DATA_STATUS);
//            Log.i("test", data);

            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Toast.makeText(context, "编号："+id+"的下载任务已经完成！", Toast.LENGTH_SHORT).show();
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/TDkankan.apk")),
                    "application/vnd.android.package-archive");
            startActivity(intent);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }
}
