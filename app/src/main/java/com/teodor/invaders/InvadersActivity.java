package com.teodor.invaders;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class InvadersActivity extends Activity {

    InvadersView invadersView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        invadersView = new InvadersView(this, size);
        setContentView(invadersView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        invadersView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        invadersView.resume();
    }

    @Override
    protected void onStop(){
        super.onStop();
        invadersView.stop();
    }
}
