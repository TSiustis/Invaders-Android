package com.teodor.invaders;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import java.util.Vector;


public class MainMenu {
    private int screenX, screenY;
    public enum MenuResult { PLAY }
    MenuItem playButton;

    public class MenuItem{
        public Rect rect;
        public MenuResult action;
    }
    private Vector<MenuItem> menuItems;

    MainMenu(int screenX, int screenY){

        this.screenX = screenX;
        this.screenY = screenY;

        menuItems = new Vector<MenuItem>();

        Paint paint;
        paint = new Paint();
        paint.setTextSize(screenY / 3.8f);

        String text = "Play";
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        int height = (int) (paint.descent() - paint.ascent());

        playButton = new MenuItem();
        playButton.rect = new Rect();
        playButton.rect.left = (screenX / 2) - (bounds.width() / 2);
        playButton.rect.top = (screenY / 2) - height/2;
        playButton.rect.bottom = playButton.rect.top + height;
        playButton.rect.right = playButton.rect.left + bounds.width();
        playButton.action = MenuResult.PLAY;



        menuItems.addElement(playButton);
    }

    public Vector<MenuItem> getMenuItems(){
        return menuItems;
    }

    public void show(SurfaceHolder holder, Canvas canvas, Paint paint){
        if(holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 229, 244, 66));

            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(screenY / 3.8f);

            float height = paint.descent() - paint.ascent();
            float offset = (height / 2) - paint.descent();

            canvas.drawText("Play", playButton.rect.left, playButton.rect.bottom - offset, paint);


            holder.unlockCanvasAndPost(canvas);
        }
    }
}
