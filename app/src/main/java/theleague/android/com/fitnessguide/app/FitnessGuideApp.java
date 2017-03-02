package theleague.android.com.fitnessguide.app;


import android.content.Context;

import com.activeandroid.app.Application;

/**
 * Created by Priyanka
 */

public class FitnessGuideApp extends Application {

    public static final String TAG = FitnessGuideApp.class
            .getSimpleName();

    public  static Context sContext;
    private static FitnessGuideApp singleInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        singleInstance = this;
        sContext = getApplicationContext();

    }

    public static synchronized FitnessGuideApp getInstance(){
        return singleInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
