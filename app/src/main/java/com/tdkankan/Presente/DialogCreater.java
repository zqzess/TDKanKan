package com.tdkankan.Presente;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tdkankan.Data.GlobalConfig;
import com.tdkankan.Data.ReadConfig;
import com.tdkankan.R;
import com.tdkankan.UI.ReadingActivity;

/**
 * @author ZQZESS
 * @date 1/6/2021.
 * @file DialogCreater
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class DialogCreater {
    public static Dialog createReadSetting(final ReadingActivity readingActivity,
                                           final ReadPresenter readPresenter,
                                           View.OnClickListener settingListener,
                                           View.OnClickListener chapterListListener,
                                           View.OnClickListener lastChapterListener,
                                           View.OnClickListener nextChapterListener,
                                           SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        final Dialog dialog = new Dialog(readingActivity, R.style.jmui_default_dialog_style);
        final View view = LayoutInflater.from(readingActivity).inflate(R.layout.dialog_read_setting, null);

        dialog.setContentView(view);

        //更多设置
        LinearLayout MoreSetting = (LinearLayout) view.findViewById(R.id.layout_more_setting);
        MoreSetting.setOnClickListener(settingListener);


        /*
        点击屏幕消失
         */
        view.findViewById(R.id.rl_title_view).setOnClickListener(null);
        view.findViewById(R.id.layout_bottom_view).setOnClickListener(null);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.dismiss();
                return false;
            }
        });



        //夜间模式
        LinearLayout NightAndDayChange=(LinearLayout)view.findViewById(R.id.layout_night_and_day);
        final TextView tv_isDrak=(TextView)view.findViewById(R.id.tv_night_and_day);
        final ImageView iv_isDrak=(ImageView)view.findViewById(R.id.iv_night_and_day);

        if(ReadConfig.isDark)
        {//isDrak默认false
            iv_isDrak.setImageResource(R.mipmap.daylight);
            tv_isDrak.setText("白天");
        }
        NightAndDayChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_isDrak.getText().toString().equals("白天"))
                {
                    ReadConfig.isDark=false;
                    iv_isDrak.setImageResource(R.mipmap.darkmoon);
                    tv_isDrak.setText("夜间");
                    readPresenter.DayAndNightChange(0);
                    ReadConfig.SaveSetting(readingActivity);//保存设置
                }else
                {
                    ReadConfig.isDark=true;
                    iv_isDrak.setImageResource(R.mipmap.daylight);
                    tv_isDrak.setText("白天");
                    readPresenter.DayAndNightChange(1);
                    ReadConfig.SaveSetting(readingActivity);//保存设置
                }
            }
        });


        /*
         *目录
         */
        LinearLayout layoutChapterList = (LinearLayout) view.findViewById(R.id.layout_chapter_list);
        layoutChapterList.setOnClickListener(chapterListListener);

        //上一章
        TextView lastChapter=(TextView)view.findViewById(R.id.tv_last_chapter);
        lastChapter.setOnClickListener(lastChapterListener);
        //下一章
        TextView nextChapter=(TextView)view.findViewById(R.id.tv_next_chapter);
        nextChapter.setOnClickListener(nextChapterListener);

        //章节跳转
        SeekBar sbChapterProgress = (SeekBar) view.findViewById(R.id.sb_read_chapter_progress);
        sbChapterProgress.setProgress(GlobalConfig.chapternow*100/(GlobalConfig.list.size()-1));
        sbChapterProgress.setOnSeekBarChangeListener(onSeekBarChangeListener);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static Dialog createReadDetailSetting(final ReadingActivity readingActivity,final ReadPresenter readPresenter)
    {
        /*
         *详细设置
         */
        final Dialog dialog = new Dialog(readingActivity, R.style.jmui_default_dialog_style);
        final View view = LayoutInflater.from(readingActivity).inflate(R.layout.dialog_read_setting_detail, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        //触摸外部关闭
        view.findViewById(R.id.layout_bottom_view).setOnClickListener(null);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.dismiss();
                return false;
            }
        });
        //字体大小
        TextView tvSizeReduce = (TextView) view.findViewById(R.id.tv_reduce_text_size);
        TextView tvSizeIncrease = (TextView) view.findViewById(R.id.tv_increase_text_size);
        final TextView tvSize = (TextView) view.findViewById(R.id.tv_text_size);
        tvSize.setText(String.valueOf(setFontSizeView()));
        tvSizeReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ReadConfig.FontSize>=53)
                {//字体减小,单章页面变小
                    String tmp=tvSize.getText().toString();
                    int tmpcount= Integer.parseInt(tmp);
                    tvSize.setText(String.valueOf(tmpcount-1));
                    ReadConfig.FontSize-=3;
                    readPresenter.LoadChapterContent();
                    if(GlobalConfig.Page+1>=GlobalConfig.PageTotal)
                    {//如何字体变小前页码大于变小后总页码
                        GlobalConfig.Page=GlobalConfig.PageTotal-1;
                    }
                    readingActivity.tv_read.setImageBitmap(readPresenter.changePageContent(GlobalConfig.Page));
                    ReadConfig.SaveSetting(readingActivity);//保存设置
                }
            }
        });
        tvSizeIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ReadConfig.FontSize<=77)
                {
                    //字体增大，单章页面变多
                    String tmp=tvSize.getText().toString();
                    int tmpcount= Integer.parseInt(tmp);
                    tvSize.setText(String.valueOf(tmpcount+1));
                    ReadConfig.FontSize+=3;
                    readPresenter.LoadChapterContent();
                    readingActivity.tv_read.setImageBitmap(readPresenter.changePageContent(GlobalConfig.Page));
                    ReadConfig.SaveSetting(readingActivity);//保存设置
                }
            }
        });

        //亮度调节
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.sb_brightness_progress);
        final TextView tvBrightFollowSystem = (TextView) view.findViewById(R.id.tv_system_brightness);
        seekBar.setProgress(ReadConfig.appLight*100/255);
        tvBrightFollowSystem.setSelected(ReadConfig.isSystemLight);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GlobalConfig.setBrightness(readingActivity,progress*255/100);
                ReadConfig.appLight=progress*255/100;
                tvBrightFollowSystem.setSelected(false);
                ReadConfig.SaveSetting(readingActivity);//保存设置
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tvBrightFollowSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvBrightFollowSystem.setSelected(!tvBrightFollowSystem.isSelected());//设置未选择
                if(tvBrightFollowSystem.isSelected())
                {//跟随系统
                    WindowManager.LayoutParams lp = readingActivity.getWindow().getAttributes();
                    lp.screenBrightness =   WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
                    readingActivity.getWindow().setAttributes(lp);
                    ReadConfig.isSystemLight=true;
                    ReadConfig.SaveSetting(readingActivity);//保存设置
                }else
                {//不跟随
//                    seekBar.setProgress(GlobalConfig.appLight*100/255);
                    GlobalConfig.setBrightness(readingActivity,ReadConfig.appLight);
                    ReadConfig.isSystemLight=false;
                    ReadConfig.SaveSetting(readingActivity);//保存设置
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public static int setFontSizeView()
    {
        int[] size={50,53,56,59,62,65,68,71,74,77,80};
        int[] sizeview={20,21,22,23,24,25,26,27,28,29,30};
        int result=20;
        for(int i=0;i<size.length;i++)
        {
            if(ReadConfig.FontSize==size[i])
            {
                result=sizeview[i];
                return result;
            }
        }
        return result;
    }
}
