package challenge.tomas.pt.lastfmchallenge.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import challenge.tomas.pt.lastfmchallenge.R;

/**
 * Launcher activity.
 * <p>
 * Created by Tomas on 03/09/2018.
 */
public class LauncherActivity extends AppCompatActivity {

    private static final int SECONDS_DELAYED = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent showMainActivity = new Intent(this, MainActivity.class);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(showMainActivity);
            }
        }, SECONDS_DELAYED * 1500);
    }
}
