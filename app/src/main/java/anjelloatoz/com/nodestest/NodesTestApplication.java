package anjelloatoz.com.nodestest;

import android.app.Application;

import anjelloatoz.com.nodestest.Persistence.PersistenceManager;

/**
 * Created by Anjelloatoz on 8/11/18.
 */

public class NodesTestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PersistenceManager.initInstance(getApplicationContext());
    }
}
