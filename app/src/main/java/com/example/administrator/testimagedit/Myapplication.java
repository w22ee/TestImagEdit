package com.example.administrator.testimagedit;

import org.lasque.tusdk.core.TuSdkApplication;

/**
 * Created by lixi on 2015/11/3.
 */
public class Myapplication extends TuSdkApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        this.initPreLoader(this.getApplicationContext(), "4cbb307783a791ab-00-8ab8o1");
        this.setEnableLog(true);
    }
}
