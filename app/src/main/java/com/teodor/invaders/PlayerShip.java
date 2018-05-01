package com.teodor.invaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class PlayerShip extends GameObject {

    Bitmap bitmap;

    private final float initialShipSpeed;
    private float shipSpeed;

    public enum ShipState {Stopped, Left, Right}

    private ShipState shipState;

    private int screenX, screenY;

    public PlayerShip(Context context, int screenX, int screenY){

        // dimensions of object
        int length, height;

        float x,y;

        this.screenX = screenX;
        this.screenY = screenY;

        length = screenX/10;
        height = screenY/10;

        x = screenX / 2;
        y = screenY - 20;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ps38);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) length, (int) height, false);

        setSize(length, height);
        setInitialPosition(x, y);

        initialShipSpeed = 350.0f;
        shipSpeed = initialShipSpeed;
        shipState = ShipState.Stopped;
    }

    @Override
    public void update(long fps, long startFrameTime){

        PointF loc = getPosition();

        if(loc.x <= 0.0f){
            if(shipState == ShipState.Left){
                shipSpeed = 0;
            }
            else{
                shipSpeed = initialShipSpeed;
            }
        }

        if(loc. x + getLength() >= screenX){
            if(shipState == ShipState.Right){
                shipSpeed = 0;
            }
            else{
                shipSpeed = initialShipSpeed;
            }
        }

        if(shipState == ShipState.Left){
            loc.x = loc.x - shipSpeed / fps;
        }
        if(shipState == ShipState.Right){
            loc.x = loc.x + shipSpeed / fps;
        }

        setPosition(loc.x, loc.y);
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(bitmap, getPosition().x, screenY - 50, paint);
    }

    @Override
    public void reset(){
        super.reset();
        shipSpeed = initialShipSpeed;
        shipState = ShipState.Stopped;
    }

    public void setDirection(ShipState state){
        shipState = state;
    }

}
