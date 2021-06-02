package com.tdkankan.ViewUitl;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdkankan.R;

/**
 * @author ZQZESS
 * @date 2021/6/2.
 * @file RightAndLeftTextView
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class RightAndLeftTextView extends LinearLayout {
    /**
     * PS：TextView有默认 TextSize，setXxxSize时会有视差，所以setXxxText前设置为左右之中最大的 TextSize
     *      -- xml中：android:textSize="xxsp"
     *      -- 代码中：setTextSize(TypedValue.COMPLEX_UNIT_SP, xx);
     */

    // 文本
    private String mLeftText;
    private String mRightText;
    private TextView tv_leftstring;
    private TextView tv_rightstring;
    private LinearLayout linearLayout;
    public RightAndLeftTextView(Context context) {
        super(context);
    }
    public RightAndLeftTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.rigthandleft_textview,this);
        linearLayout=(LinearLayout)findViewById(R.id.ll_rightandleft);
        tv_leftstring=(TextView)findViewById(R.id.tv_leftstring);
        tv_rightstring=(TextView)findViewById(R.id.tv_rightstring);
    }

    public String getmLeftText() {
        return mLeftText;
    }

    public void setmLeftText(String mLeftText) {
        this.mLeftText = mLeftText;
        tv_leftstring.setText(mLeftText);
    }

    public String getmRightText() {
        return mRightText;
    }

    public void setmRightText(String mRightText) {
        this.mRightText = mRightText;
        tv_rightstring.setText(mRightText);
    }
}
