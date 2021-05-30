package com.tdkankan.Data;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tdkankan.R;

/**
 * @author ZQZESS
 * @date 12/8/2020.
 * @file TabItem
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class TabItem {
    private final int SelectTextColor;
    private ImageView imageviewTab;
    /**
     * 正常状态的图片
     */
    private int imageNormal;

    /**
     * 选中状态的图片
     */
    private int imageSelected;

    private TextView textviewTab;
    /**
     * 文字
     */
    private String tabText;

    /**
     * Fragment
     */
    private Class<? extends Fragment> fragmentClass;

    private View mTabView;

    public TabItem(int imageNormal, int imageSelected, String text, Class<? extends Fragment> fragmentClass, int selectTextcolor) {
        this.imageNormal = imageNormal;
        this.imageSelected = imageSelected;
        this.tabText = text;
        this.fragmentClass = fragmentClass;
        SelectTextColor = selectTextcolor;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    /**
     * 获取 tab 上的文字
     *
     * @return tab 上的文字
     */
    public String getTabText() {
        return tabText;
    }

    /**
     * 设置选中
     *
     * @param checked 是否选中
     */
    public void setChecked(boolean checked) {
        if (checked) {
            textviewTab.setTextColor(SelectTextColor);
            imageviewTab.setImageResource(imageSelected);
        } else {
            textviewTab.setTextColor(Color.WHITE);
            imageviewTab.setImageResource(imageNormal);
        }
    }

    public View getTabView(Context context) {
        mTabView = View.inflate(context, R.layout.view_table, null);
        imageviewTab = (ImageView) mTabView.findViewById(R.id.iv_tab);
        textviewTab = (TextView) mTabView.findViewById(R.id.tv_tab);
        //mTvNewMsg = (TextView) mTabView.findViewById(R.id.tv_new_msg);
        imageviewTab.setImageResource(imageNormal);
        textviewTab.setText(tabText);
        return mTabView;
    }
}
