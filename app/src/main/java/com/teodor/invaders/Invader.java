package com.teodor.invaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.Random;

public class Invader extends GameObject {

    Random generator = new Random();

    public enum ShipState {LEFT, RIGHT}

    Bitmap bitmap;

    private int screenX, screenY;

    private boolean isVisible;

    private float shipSpeed;
    private final float initialShipSpeed;

    private ShipState shipState;

    private int nextBullet;
    private final int maxInvaderBullets;

    private boolean bumped;
    private boolean lost;


    public Invader(Context context, int row, int column, int screenX, int screenY){

        this.screenX = screenX;
        this.screenY = screenY;

        int length = screenX / 20;
        int height = screenY / 20;

        int padding = screenX / 25;

        float x = column * (length + padding);
        float y = row * (length + padding / 4);

        setSize(length, height);
        setInitialPosition(x, y);

        isVisible = true;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship2);

        bitmap= Bitmap.createScaledBitmap(bitmap,  length,  height, false);

        initialShipSpeed = 40.0f;
        shipSpeed = initialShipSpeed;
        shipState = ShipState.RIGHT;

        nextBullet = 0;
        maxInvaderBullets = 10;

        bumped = false;
        lost = false;
    }

    @Override
    public void update(long fps, long startFrameTime){

        // current position of object;
        PointF loc = getPosition();

        // set new location of ship
        if(shipState == ShipState.LEFT){
            loc.x = loc.x - shipSpeed / fps;
        }
        if(shipState == ShipState.RIGHT){
            loc.x = loc.x + shipSpeed / fps;
        }

        setPosition(loc.x, loc.y);

        loc = getPosition();

        PlayerShip playerShip = (PlayerShip) InvadersView.getObjectManager().get("playerShip");

        if(takeAim(playerShip.getPosition().x, playerShip.getLength())){

            Bullet bullet = (Bullet) InvadersView.getObjectManager().get("invadersBullet"
                    + nextBullet);

            if(bullet.shoot(loc.x + getLength() / 2, loc.y, Bullet.Direction.DOWN)){
                nextBullet++;

                if(nextBullet == maxInvaderBullets){
                    // this stops the firing of another bullet until one completes its journey
                    // because if bullet is still active shoot returns false.
                    nextBullet = 0;
                }
            }
        }

        if(loc.x > screenX - getLength() || loc.x < 0){
            bumped = true;
        }

        if(bumped){
            dropDownAndReverse();

            // have the invaders landed
            if(loc.y > screenY - screenY / 12){
                lost = true;
            }

            if(lost){
                InvadersView.getSoundManager().playSound("lose");
                InvadersView.getScoreBoard().setGameResult(ScoreBoard.GameResult.Lose);
                InvadersView.setGameState(InvadersView.GameState.Completed);
                return;
            }
            // make the sounds of flapping more frequent
            long interval = InvadersView.getMenaceInterval();
            interval -= 4;
            InvadersView.setMenaceInterval(interval);
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        if(isVisible){
            PointF loc = getPosition();

            // alternatively draw bitmap to give a sense of flying
            if(InvadersView.getUhOrOh()){
                canvas.drawBitmap(bitmap, loc.x, loc.y, paint);
            }
            else{
                canvas.drawBitmap(bitmap, loc.x, loc.y, paint);
            }
        }
    }

    @Override
    public void reset(){
        super.reset();
        shipSpeed = initialShipSpeed;
        isVisible = true;
        shipState = ShipState.RIGHT;
        nextBullet = 0;
        bumped = false;
        lost = false;
    }

    // helper methods
    public void dropDownAndReverse(){

        // reverse direction
        if(shipState == ShipState.LEFT){
            shipState = ShipState.RIGHT;
        }
        else{
            shipState = ShipState.LEFT;
        }

        // increase speed by 18%
        shipSpeed = shipSpeed * 1.2f;

        // update position
        PointF loc = getPosition();
        loc.y = loc.y + getHeight();
        setPosition(loc.x, loc.y);

        bumped = false;
    }

    public boolean takeAim(float playerShipX, float playerShipLength){
        int randomNumber = -1;

        PointF loc = getPosition();

        if((playerShipX + playerShipLength > loc.x && playerShipX + playerShipLength < loc.x +
                getLength()) || (playerShipX > loc.x && playerShipX < loc.x + getLength())){

            randomNumber = generator.nextInt(150);

            if(randomNumber == 0){
                return true;
            }
        }

        randomNumber = generator.nextInt(2000);
        if(randomNumber == 0){
            return true;
        }

        return false;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }


    public boolean isLost(){
        return lost;
    }
}
