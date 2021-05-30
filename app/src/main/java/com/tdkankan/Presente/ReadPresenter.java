package com.tdkankan.Presente;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;

import androidx.core.view.GravityCompat;

import com.hb.dialog.dialog.LoadingDialog;
import com.tdkankan.Adapter.ChapterAdapter;
import com.tdkankan.Cache.BookContentCache;
import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.Data.ReadConfig;
import com.tdkankan.R;
import com.tdkankan.Reptile.GetAndRead;
import com.tdkankan.Reptile.GetBook;
import com.tdkankan.UI.ReadingActivity;

import java.math.BigDecimal;

/**
 * @author ZQZESS
 * @date 1/6/2021.
 * @file ReadPresenter
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class ReadPresenter implements BasePresente {
    private Dialog mSettingDialog;//设置视图
    private Dialog mSettingDetailDialog;//详细设置视图
    private ReadingActivity readingActivity;
    private ListView listView;
    private Canvas canvas;
    Paint textPaint = new Paint();
    GetAndRead getBook = new GetAndRead(readingActivity);
    ChapterAdapter chapterAdapter;
    LoadingDialog loadingDialog;

    public ReadPresenter(ReadingActivity readingActivity) {
        this.readingActivity = readingActivity;
    }

    @Override
    public void showSettingView() {
        if (mSettingDialog != null) {
//            mSettingDialog.show();
            mSettingDialog.cancel();
            mSettingDialog=null;
        }
//        else {
        mSettingDialog = DialogCreater.createReadSetting(readingActivity, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //详细设置
                showSettingDetailView();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //目录
                readingActivity.drawerLayout.openDrawer(GravityCompat.START);
                mSettingDialog.dismiss();
//                ChapterAdapter chapterAdapter = new ChapterAdapter(GlobalConfig.list, readingActivity);
                listView = readingActivity.findViewById(R.id.listview_chapter_list);
//                listView.setAdapter(chapterAdapter);
                new loadTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
//                listView.setSelection(GlobalConfig.chapternow - 5);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上一章
                if (GlobalConfig.chapternow != 0) {
                    GlobalConfig.chapternow -= 1;
                    GlobalConfig.Page = 0;
                    LoadChapterContent();
                    GlobalConfig.SaveReadSetting(readingActivity);//保存阅读进度
                    readingActivity.tv_read.setImageBitmap(changePageContent(GlobalConfig.Page));
                    if(!ReadConfig.isDownload)
                    {
                        getBook.ReadingBackground(GlobalConfig.chapternow);
                    }
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下一章
                if (GlobalConfig.chapternow != GlobalConfig.list.size() - 1) {
                    GlobalConfig.chapternow += 1;
                    GlobalConfig.Page = 0;
                    LoadChapterContent();
                    GlobalConfig.SaveReadSetting(readingActivity);//保存阅读进度
                    readingActivity.tv_read.setImageBitmap(changePageContent(GlobalConfig.Page));
                    if(!ReadConfig.isDownload)
                    {
                        getBook.ReadingBackground(GlobalConfig.chapternow);
                    }

                }
            }
        }, new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //章节跳转
                GlobalConfig.chapternow=(GlobalConfig.list.size()-1)*progress/100;
                GlobalConfig.Page=0;
                LoadChapterContent();
                GlobalConfig.SaveReadSetting(readingActivity);//保存阅读进度
                readingActivity.tv_read.setImageBitmap(changePageContent(GlobalConfig.Page));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        }
    }

    @Override
    public void showSettingDetailView() {
        //显示详细设置
        mSettingDialog.dismiss();
        if (mSettingDetailDialog != null) {
            mSettingDetailDialog.show();
        } else {
            mSettingDetailDialog = DialogCreater.createReadDetailSetting(readingActivity,this);
        }
    }


    @Override
    public void DayAndNightChange(int styleCode) {
        //检查系统设置修改权限
        switch (styleCode){
            case 0:
                //默认模式
                ReadConfig.fontColor=R.color.default_font_color;//字体色
                ReadConfig.bgColor=R.color.default_read_color;//背景色

                readingActivity.intChapterStyle(ReadConfig.bgColor,ReadConfig.fontColor);
                break;
            case 1:
                //夜间模式
                ReadConfig.fontColor=R.color.dark_font_color;//字体色
                ReadConfig.bgColor=R.color.dark_read_color;//背景色
                readingActivity.intChapterStyle(ReadConfig.bgColor,ReadConfig.fontColor);
                break;
        }

        readingActivity.intStyle();

    }

    private class loadTask extends AsyncTask<Void,Integer,Boolean>
    {//后台任务
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog=new LoadingDialog(readingActivity);
            loadingDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            chapterAdapter = new ChapterAdapter(GlobalConfig.list, readingActivity);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loadingDialog.dismiss();
            listView.setAdapter(chapterAdapter);
            listView.setSelection(GlobalConfig.chapternow - 5);
        }
    }
    public void LoadChapterContent()
    {//加载一个章节
        textPaint.setTextSize(ReadConfig.FontSize);
        Paint.FontMetrics fm = textPaint.getFontMetrics();// 得到系统默认字体属性
        GlobalConfig.mFontHeight = (int) (Math.ceil(fm.descent - fm.top) + 2);// 获得字体高度
        GlobalConfig.mPageLineNum = (int) (GlobalConfig.measuredHeigtt / GlobalConfig.mFontHeight);// 获得行数

        String content = "";
        GlobalConfig.contentMap.clear();
        try {
//            content = getBook.GetBookContent(GlobalConfig.list.get(GlobalConfig.chapternow).get("link"));
            content= BookContentCache.getCache(GlobalConfig.list.get(GlobalConfig.chapternow).get("link"));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
//            Toast.makeText(getApplicationContext(),"章节初始化失败",Toast.LENGTH_SHORT).show();
        }
        content = getBook.splitContentFirst(content);//分段
        content = getBook.splitcontentSecond(content, ReadConfig.FontSize, GlobalConfig.measuredWidth);//段落分行
        getBook.PageSet(content, GlobalConfig.mPageLineNum, GlobalConfig.contentMap);//章节分页并存入hashmap
        try{
            readingActivity.tv_title.setText(GlobalConfig.list.get(GlobalConfig.chapternow).get("title"));
        }catch (Exception e)
        {
            e.printStackTrace();
        }

//        /*
//         *设置阅读进度百分比
//         */
//        if(GlobalConfig.chapternow==0)
//        {
//            readingActivity.tv_foot.setText("0%");
//        }else
//        {
//            BigDecimal b1 = new BigDecimal(Double.toString(GlobalConfig.chapternow));
//            BigDecimal b2 = new BigDecimal(Double.toString(GlobalConfig.list.size()));
//            BigDecimal b3=new BigDecimal(100.00);
////            int progress=GlobalConfig.chapternow%GlobalConfig.list.size();
//            try{
//                BigDecimal progress = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
//                progress=progress.multiply(b3);
//                readingActivity.tv_foot.setText(progress+"%");
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
    }

    public Bitmap changePageContent(int page) {
        /*
         *设置阅读进度百分比
         */
        if(GlobalConfig.chapternow==0)
        {
            readingActivity.tv_foot.setText("0%");
        }else
        {
            BigDecimal b1 = new BigDecimal(Double.toString(GlobalConfig.chapternow));
            BigDecimal b2 = new BigDecimal(Double.toString(GlobalConfig.list.size()));
            BigDecimal b3=new BigDecimal(100.00);
//            int progress=GlobalConfig.chapternow%GlobalConfig.list.size();
            try{
                BigDecimal progress = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
                progress=progress.multiply(b3);
                readingActivity.tv_foot.setText(progress+"%");
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //切换页面
        textPaint.setColor(readingActivity.getResources().getColor(ReadConfig.fontColor));//字体颜色
        GlobalConfig.mutableBitmap = Bitmap.createBitmap(GlobalConfig.measuredWidth, GlobalConfig.measuredHeigtt, Bitmap.Config.RGB_565);
        GlobalConfig.mutableBitmap.eraseColor(readingActivity.getResources().getColor(ReadConfig.bgColor));//背景色
        canvas = new Canvas(GlobalConfig.mutableBitmap);

        if (GlobalConfig.Page == 0) {//新章节起始，为标题
//                ArrayList<HashMap<String, String>> list=getBook.getChapter("http://www.biquge.com/135_135874/",508);
            try {
                canvas.drawText(GlobalConfig.contentMap.get(page), 5, (GlobalConfig.measuredHeigtt - GlobalConfig.mFontHeight) / 2, textPaint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String tmpstring = GlobalConfig.contentMap.get(page);
            try {
                String[] arrtmp = tmpstring.split("\n");
                for (int i = 0; i < arrtmp.length; i++) {
                    canvas.drawText(arrtmp[i], 5, ReadConfig.FontSize + GlobalConfig.mFontHeight * i, textPaint);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

//            GlobalConfig.Page+=1;
//            if(GlobalConfig.Page==GlobalConfig.contentMap.size())
//            {
//                GlobalConfig.Page=0;
//            }
        return GlobalConfig.mutableBitmap;
    }
}
