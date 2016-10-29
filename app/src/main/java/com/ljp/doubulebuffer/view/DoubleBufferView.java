package com.ljp.doubulebuffer.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lijianpeng on 16/10/24.
 */

public class DoubleBufferView extends View {

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public DoubleBufferView(Context context) {
        super(context);
        init(context);
    }


    public DoubleBufferView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DoubleBufferView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Canvas bufferCanvas;
    private Bitmap bufferBitmap;
    private void init (Context context) {
        bufferCanvas = new Canvas();
    }

    public void drawBitmap () {
        bufferBitmap = Bitmap.createBitmap(getBitmap().getWidth(),
                getBitmap().getHeight(), Bitmap.Config.ARGB_8888);
        bufferCanvas.setBitmap(bufferBitmap);
        bufferCanvas.drawBitmap(getBitmap(), 0, 0, null);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {


        //5. 用屏幕的画布绘制缓冲位图
        canvas.drawBitmap(bufferBitmap,0,0, null);
    }

}
