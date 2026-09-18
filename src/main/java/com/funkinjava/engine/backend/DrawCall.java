package com.funkinjava.engine.backend;

import java.util.function.Consumer;

public class DrawCall {
    public DrawCall(Consumer onDraw) {
        this.onDraw = onDraw;
    }

    @SuppressWarnings("rawtypes")
    public Consumer onDraw;

    public void destroy() {
        onDraw = null;
    }
}
