package com.tdkankan.Presente;

import android.graphics.Bitmap;

/**
 * @author ZQZESS
 * @date 1/6/2021.
 * @file BasePresente
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public interface BasePresente {
    void showSettingView();
    void showSettingDetailView();
    void DayAndNightChange(int styleCode);
    void LoadChapterContent();
    Bitmap changePageContent(int page);
}
