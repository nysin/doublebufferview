package com.ljp.doubulebuffer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.ljp.doubulebuffer.data.DataSource;
import com.ljp.doubulebuffer.utils.CacheUtils;
import com.ljp.doubulebuffer.view.DoubleBufferView;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by lijianpeng on 2016/10/24.
 */

public class DoubleBufferActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 2* 1000;
    private static final int MESSAGE_TYPE = 0x1001;
    private DoubleBufferView doubleBufferView;
    private DoubleBufferHanlder hanlder;
    private TextView tvInditor;
    private int screenW,screenH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buffer_main);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenW = dm.widthPixels;
        screenH = dm.heightPixels;
        tvInditor = (TextView) findViewById(R.id.tv_inditor);
        doubleBufferView = (DoubleBufferView) findViewById(R.id.bf_view);
        hanlder = new DoubleBufferHanlder(this);
        hanlder.sendEmptyMessageDelayed(MESSAGE_TYPE,0);

    }

    public List<Integer> getSource () {
        return DataSource.initSouce();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hanlder.removeMessages(MESSAGE_TYPE);
        hanlder = null;
        doubleBufferView = null;
    }

    public int getScreenW() {
        return screenW;
    }

    public int getScreenH() {
        return screenH;
    }


    private static class DoubleBufferHanlder extends Handler {
        private DoubleBufferView doubleBufferView;
        private int current = 0;
        private int size = 0;
        private SoftReference<DoubleBufferActivity> softReference ;

        public DoubleBufferHanlder(DoubleBufferActivity doubleBufferActivity) {
            this.softReference = new SoftReference<>(doubleBufferActivity);
            size = softReference.get().getSource().size();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (softReference.get() != null) {
                doubleBufferView = softReference.get().doubleBufferView;
            }

            int resId = softReference.get().getSource().get(current%size);
            int pos = current%size + 1;
            softReference.get().tvInditor.setText(pos+"/"+size);
            Bitmap bitmap = CacheUtils.getInstance().
                    decodeBitmapFromRes(softReference.get(),resId,softReference.get().getScreenW(),
                            softReference.get().getScreenH());
            doubleBufferView.setBitmap(bitmap);
            doubleBufferView.drawBitmap();
            current ++;
            sendEmptyMessageDelayed(MESSAGE_TYPE,DELAY_TIME);
        }
    }
}
