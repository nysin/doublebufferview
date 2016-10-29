package com.ljp.doubulebuffer.data;

import com.ljp.doubulebuffer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijianpeng on 16/10/24.
 */

public class DataSource {

    public static List<Integer> initSouce() {
        List<Integer> source = new ArrayList<>();
        source.add(R.mipmap.pic1);
        source.add(R.mipmap.pic2);
        source.add(R.mipmap.pic3);
        source.add(R.mipmap.pic4);
        return source;
    }
}
