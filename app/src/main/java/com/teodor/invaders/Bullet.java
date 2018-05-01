package com.teodor.invaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Bullet extends GameObject {

    public enum Direction{NOWHERE, UP, DOWN}

    private Direction direction;

    private float speed;

    private boolean isActive;

    private int screenY;

    public Bullet(int screenY){

        this.screenY = screenY;

        int height = screenY / 20;
        int length = 1;

        setSize(length, height);
        //setInitialPosition(0, 0);

        direction = Direction.NOWHERE;
        speed = 350.0f;
        isActive = false;
    }

    @Override
    public void update(long fps, long startFrameTime){

        if(isActive) {
            PointF loc = getPosition();

            if (direction == Direction.UP) {
                loc.y = loc.y - speed / fps;
            } else {
                loc.y = loc.y + speed / fps;
            }

            setPosition(loc.x, loc.y);
        }

        if(getImpactPointY() < 0 || getImpactPointY() > screenY){
            setInactive();
        }

        // collision of player bullet with invader
        if(isActive && direction == Direction.UP){
            for(int i = 0; i< InvadersView.getNumberOfInvaders(); i++){
                Invader invader = (Invader) InvadersView.getObjectManager().get("invader" + i);
                if(invader.getVisibility()){
                    if(RectF.intersects(invader.getBoundingRect(), getBoundingRect())){
                        invader.setInvisible();
                        InvadersView.getSoundManager().playSound("invaderexplode");
                        setInactive();
                        InvadersView.getScoreBoard().incrementScore();

                        if(InvadersView.checkVictory()){
                            return;
                        }

                    }
                }
            }
        }



        // collision of alien bullet with playerShip
        if(isActive && direction == Direction.DOWN){
            PlayerShip ship = (PlayerShip) InvadersView.getObjectManager().get("playerShip");
            if (RectF.intersects(ship.getBoundingRect(), getBoundingRect())) {
                InvadersView.getSoundManager().playSound("playerexplode");
                setInactive();

                InvadersView.getScoreBoard().decrementLife();

                if(InvadersView.checkVictory()){
                    return;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if(isActive){
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawRect(getBoundingRect(), paint);
        }
    }

    public boolean shoot(float startX, float startY, Direction d){

        if(!isActive){
            // get current position
            PointF loc = getPosition();

            // set new position
            loc.x = startX;
            loc.y = startY;
            setInitialPosition(loc.x, loc.y);

            direction = d;
            isActive = true;

            return true;
        }

        return false;
    }

    public void setInactive(){
        isActive = false;
    }

    public boolean getStatus(){
        return isActive;
    }

    public float getImpactPointY(){
        if(direction == Direction.DOWN){
            return getPosition().y + getHeight();
        }
        else{
            return getPosition().y;
        }
    }
}
