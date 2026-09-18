package com.funkinjava.engine.graphics;

import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Texture;

public class Frame {
    Rectangle srcRect = new Rectangle();
    public Texture _graphic;
    public boolean destroyed;

    public float frameX = 0f;
    public float frameY = 0f;
    public float frameWidth = 0f;
    public float frameHeight = 0f;

    public Frame(float fx, float fy, float fw, float fh, Texture graphic) {
        frameX = fx;
        frameY = fy;
        frameWidth = fw;
        frameHeight = fh;
        _graphic = graphic;
    }

    public void destroy() {
        _graphic = null;
        destroyed = true;
    }

    public Rectangle getSourceRect() {

        srcRect.x(frameX).y(frameY).width(frameWidth).height(frameHeight);
        return srcRect;
    }
}
