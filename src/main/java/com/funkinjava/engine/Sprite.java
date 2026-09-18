package com.funkinjava.engine;

import com.funkinjava.engine.graphics.Frame;
import com.funkinjava.engine.loaders.TextureLoader;
import com.raylib.Colors;
import com.raylib.Raylib;
import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Texture;
import com.raylib.Raylib.Vector2;

public class Sprite extends Object {
    public Color color = Colors.WHITE;

    public float scaleX = 1;
    public float scaleY = 1;

    public Vector2 origin;
    public Vector2 offset;

    public Texture graphic;
    public float angle = 0;

    private final Rectangle srcRect = new Rectangle();
    private final Rectangle dstRect = new Rectangle();
    private final Vector2 finalOrigin = new Vector2();

    public boolean isAnimated = false;

    public Frame frame;

    public boolean flipX = false;
    public boolean flipY = false;

    public Sprite(float X, float Y) {
        super(X, Y);

        offset = new Vector2();
        origin = new Vector2();

        origin.x(0).y(0);
        offset.x(0).y(0);
    }

    public void makeGraphic(int width, int height, Color clr) {
        if (clr == null)
            clr = Colors.WHITE;
        graphic = TextureLoader.makeGraphic(width, height, clr);
        onGraphic();
    }

    public void loadGraphic(String tex) {
        graphic = TextureLoader.fromPath(tex);
        onGraphic();
        if (frame != null) {
            frame.destroy();
            frame = null;
        }
    }

    public void onGraphic() {
        if (graphic != null) {
            this.width = graphic.width();
            this.height = graphic.height();
        }
    }

    @Override
    public void render() {
        if (graphic == null) {
            loadGraphic("assets/flixel.png");
            if (graphic == null) return;
        }

        Texture Targetgraphic = graphic;
        if(frame != null)
            Targetgraphic = frame._graphic;
        float baseWidth = (frame != null) ? frame.frameWidth : graphic.width();
        float baseHeight = (frame != null) ? frame.frameHeight : graphic.height();
        float frameX = (frame != null) ? frame.frameX : 0.0f;
        float frameY = (frame != null) ? frame.frameY : 0.0f;

        srcRect.x(frameX);
        srcRect.y(frameY);
        srcRect.width(baseWidth);
        srcRect.height(baseHeight);

        dstRect.x(x - offset.x() + origin.x() * scaleX);
        dstRect.y(y - offset.y() + origin.y() * scaleY);
        dstRect.width(Math.abs(baseWidth * scaleX));
        dstRect.height(Math.abs(baseHeight * scaleY));

        finalOrigin.x(origin.x() * Math.abs(scaleX));
        finalOrigin.y(origin.y() * Math.abs(scaleY));

        Raylib.DrawTexturePro(Targetgraphic, srcRect, dstRect, finalOrigin, angle, color);
    }
}
