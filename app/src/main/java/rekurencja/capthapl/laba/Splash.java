package rekurencja.capthapl.laba;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;

public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 6000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, rekurencja.capthapl.laba.MainActivity.class);
                startActivity(i);
                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }




}



