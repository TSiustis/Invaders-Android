package com.teodor.invaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.concurrent.ConcurrentHashMap;


public class GameObjectManager {

    private ConcurrentHashMap<String, GameObject> gameObjects = new ConcurrentHashMap<>();

    public void add(String name, GameObject gameObject){
        gameObjects.put(name, gameObject);
    }

    public void remove(String name){
        gameObjects.remove(name);
    }

    public int getObjectCount(){
        return gameObjects.size();
    }

    public GameObject get(String name){
        return gameObjects.get(name);
    }

    public void drawAll(SurfaceHolder ourHolder, Canvas canvas, Paint paint){
        if(ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 229, 244, 66));
            for (ConcurrentHashMap.Entry<String, GameObject> entry : gameObjects.entrySet()) {
                GameObject value = entry.getValue();
                value.draw(canvas, paint);
            }
            InvadersView.drawScoreBoard(canvas, paint);
            InvadersView.drawButtons(canvas, paint);
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void updateAll(long fps, long startFrameTime){
        for(ConcurrentHashMap.Entry<String, GameObject> entry : gameObjects.entrySet()){
            GameObject value = entry.getValue();
            value.update(fps, startFrameTime);
        }
    }

    public void resetAll(){
        for(ConcurrentHashMap.Entry<String, GameObject> entry : gameObjects.entrySet()){
            GameObject value = entry.getValue();
            value.reset();
        }
    }
}
