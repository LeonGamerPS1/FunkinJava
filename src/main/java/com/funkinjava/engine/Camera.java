package com.funkinjava.engine;

import com.raylib.Raylib.Camera2D;

public class Camera {
    public float zoom;
    public float rotation;
    private Camera2D cam;

    public Camera(float zoom) {
        cam = new Camera2D();
        setZoom(zoom);
        setRotation(0);
    }

    public void update(float dt) {

    }

    public void render() {}

    public void setZoom(float z) {
        zoom = z;
        cam.zoom(z);
    }

    public void setRotation(float rotAngle) {
        rotation = rotAngle;
        cam.rotation(rotAngle);
    }

   
}
