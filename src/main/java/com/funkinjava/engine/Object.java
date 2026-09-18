package com.funkinjava.engine;


import com.funkinjava.engine.util.Hit;



public class Object extends Basic {
    public float x = 0;
    public float y = 0;
    public int width = 0;
    public int height = 0;

    @Override
    public boolean isInBounds() {
        return Hit.overlaps(x, y, width, height, 0, 0, (int)Game.GAME_WIDTH, (int)Game.GAME_HEIGHT);
    }

    public Object(float X, float Y) {
        super();
        x = X;
        y = Y;
    }

    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    public boolean overlaps(Object otherOBJ) {
        return Hit.overlaps(x, y, width, height, otherOBJ.x, otherOBJ.y, otherOBJ.width, otherOBJ.height);
    }
}
