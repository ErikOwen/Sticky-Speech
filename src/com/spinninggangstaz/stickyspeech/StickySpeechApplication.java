package com.spinninggangstaz.stickyspeech;

import android.app.Application;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: Kevin
 * Date: 2/8/14
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class StickySpeechApplication extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        StickySpeechApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return StickySpeechApplication.context;
    }
}
