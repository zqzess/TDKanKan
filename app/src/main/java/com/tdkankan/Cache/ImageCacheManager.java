package com.tdkankan.Cache;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.tdkankan.ViewUitl.BookItemTypeTwo;

/**
 * @author ZQZESS
 * @date 1/7/2021.
 * @file ImageCacheManager
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class ImageCacheManager {
    /**
     * 图片缓存管理类 获取ImageLoader对象
     */
    private static String TAG = ImageCacheManager.class.getSimpleName();

    // 获取图片缓存类对象
    private static ImageLoader.ImageCache mImageCache = new ImageCacheUtil();
    // 获取ImageLoader对象
    public static ImageLoader mImageLoader = new ImageLoader(VolleyRequestQueueManager.mRequestQueue, mImageCache);


    /**
     * 获取ImageListener
     *
     * @param imageView
     * @param defaultImage
     * @param errorImage
     * @return
     */
    public static ImageLoader.ImageListener getImageListener(final ImageView imageView, final Bitmap defaultImage, final Bitmap errorImage) {

        return new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // 回调失败
                if (errorImage != null) {
                    imageView.setImageBitmap(errorImage);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                // 回调成功
                if (response.getBitmap() != null) {
                    imageView.setImageBitmap(response.getBitmap());
                } else if (defaultImage != null) {
                    imageView.setImageBitmap(defaultImage);
                }
            }
        };

    }

    /**
     * 提供给外部调用方法
     *
     * @param url
     * @param imageView
     * @param defaultImage
     * @param errorImage
     */
    public static void loadImage(String url, ImageView imageView, Bitmap defaultImage, Bitmap errorImage) {
        mImageLoader.get(url, ImageCacheManager.getImageListener(imageView, defaultImage, errorImage), 0, 0);
    }

    /**
     * 提供给外部调用方法
     *
     * @param url
     * @param imageView
     * @param defaultImage
     * @param errorImage
     */
    public static void loadImage(String url, ImageView imageView, Bitmap defaultImage, Bitmap errorImage, int maxWidth, int maxHeight) {
        mImageLoader.get(url, ImageCacheManager.getImageListener(imageView, defaultImage, errorImage), maxWidth, maxHeight);
    }
}
