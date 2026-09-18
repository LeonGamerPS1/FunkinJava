package com.funkinjava.engine.util;

import com.raylib.Raylib.Vector2;

public class Vector {
    public static Vector2 makeVector(float x, float y) {
        Vector2 v = new Vector2();
        v.x(x);
        v.y(y);
        return v;
    }
}
