package com.funkinjava.engine;

import java.util.ArrayList;
import java.util.List;

import com.funkinjava.engine.backend.DrawCall;
import com.funkinjava.engine.util.Vector;
import com.raylib.Raylib.Camera2D;

public class Camera {
    public float zoom;
    public float rotation;
    public Camera2D cam;

    public List<DrawCall> drawQueue;

    public Camera(float zoom) {
        cam = new Camera2D();
        setZoom(zoom);
        setRotation(0);
        cam.offset(Vector.makeVector(Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2));
        cam.target(cam.offset());

        drawQueue = new ArrayList<>();
    }

    public void update(float dt) {
        cam.offset(Vector.makeVector(Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2));
        cam.target(cam.offset());
    }

    @SuppressWarnings("unchecked")
    public void render() {
        for (DrawCall drawCall : drawQueue) {
            if (drawCall.onDraw != null)
                drawCall.onDraw.accept(null);
            drawCall.destroy();

        }
        drawQueue = new ArrayList<>();
    }

    public void setZoom(float z) {
        zoom = z;
        cam.zoom(z);
    }

    public void setRotation(float rotAngle) {
        rotation = rotAngle;
        cam.rotation(rotAngle);
    }

    public void destroy() {
        cam = null;
    }

}
