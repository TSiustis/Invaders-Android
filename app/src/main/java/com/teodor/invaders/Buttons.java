package com.teodor.invaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;


import java.util.Vector;


public class Buttons {

    public enum Result {Pause, Shoot}

    Bitmap shoot, pause;

    private int screenX, screenY;

    private int length, height;

    Point shootBut = new Point();
    Point pauseBut = new Point();

    Rect shootRect = new Rect();
    Rect pauseRect = new Rect();

    public class ButtonItem{
        public Rect rect;
        public Result action;
    }

    private Vector<ButtonItem> buttons;

    ButtonItem pauseButton = new ButtonItem();
    ButtonItem shootButton = new ButtonItem();

    public Buttons(Context context, int screenX, int screenY){

        this.screenX = screenX;
        this.screenY = screenY;

        buttons = new Vector<>();

        length = screenY / 8;
        height = screenY / 8;

        shootBut.x = screenX - length - (screenX / 100);
        shootBut.y = (screenY / 2) - (height / 2);

        pauseBut.x = (screenX / 2) - (length / 2);
        pauseBut.y = screenY / 100 ;

        shootRect.set(shootBut.x, shootBut.y, shootBut.x + length,
                shootBut.y + height);

        pauseRect.set(pauseBut.x, pauseBut.y, pauseBut.x + length,
                pauseBut.y + height);

        shoot = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot);
        pause = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);

        shoot = Bitmap.createScaledBitmap(shoot, length, height, false);
        pause = Bitmap.createScaledBitmap(pause, length, height, false);

        // set the buttons
        pauseButton.rect = new Rect(pauseRect);
        shootButton.rect = new Rect(shootRect);
        pauseButton.action = Result.Pause;
        shootButton.action = Result.Shoot;

        buttons.addElement(pauseButton);
        buttons.addElement(shootButton);
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(shoot, shootBut.x, shootBut.y, paint);
        canvas.drawBitmap(pause, pauseBut.x, pauseBut.y, paint);
    }

    public Vector getButtons(){
        return buttons;
    }
}
