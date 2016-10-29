package com.ljp.doubulebuffer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

/**
 * Created by lijianpeng on 16/10/24.
 * 图片缓存类
 */

public class CacheUtils {

    private LruCache<Integer, Bitmap> cache;

    public static CacheUtils getInstance() {
        return CacheUtilsHolder.INSTANCE;
    }

    private CacheUtils() {
        int maxSize = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxSize / 4;
        cache = new LruCache<Integer, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * 原大小图片缓存
     * @param context
     * @param resourceId
     * @return
     */
    public Bitmap decodeBitmapFromRes(Context context, int resourceId) {
        Bitmap bitmap;
        if (cache.get(resourceId) == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
            cache.put(resourceId, bitmap);
        } else {
            bitmap = cache.get(resourceId);
        }
        return bitmap;

    }

    /**
     * 根据屏幕图片进行缩放
     * @param context
     * @param resourceId
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public Bitmap decodeBitmapFromRes(Context context, int resourceId,
                                      int screenWidth,int screenHeight) {
        Bitmap bitmap;
        if (cache.get(resourceId) == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
            if (screenHeight > 0 && screenWidth > 0) {
                bitmap = scaleBitmap(bitmap, screenWidth, screenHeight);
            }
            cache.put(resourceId, bitmap);
        } else {
            bitmap = cache.get(resourceId);
        }
        return bitmap;

    }

    private Bitmap scaleBitmap(Bitmap bitmap, int screenWid, int screenHeight) {
        return BitmapUtil.getBitmap(bitmap, screenWid, screenHeight);
    }

    private static class CacheUtilsHolder {
        private static final CacheUtils INSTANCE = new CacheUtils();
    }

}
