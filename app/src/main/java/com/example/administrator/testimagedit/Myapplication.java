package com.example.administrator.testimagedit;

import org.lasque.tusdk.core.TuSdkApplication;

/**
 * Created by lixi on 2015/11/3.
 */
public class Myapplication extends TuSdkApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        this.initPreLoader(this.getApplicationContext(), "a96c5ad01894c1ed-00-8ab8o1");
        this.setEnableLog(true);
    }
}
