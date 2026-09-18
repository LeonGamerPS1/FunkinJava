package com.funkinjava.engine;

public class Basic {
    static private int gbID = 0;

    public boolean canUpdate = false;
    public boolean canRender = false;

    public int ID = gbID++;
    public Camera camera;

    public Basic() {
        canRender = canUpdate = true;
        constructor();
    }

    public void constructor() {}

    public void destroy() {}

    /**
     * The function where all the graphic magic happens..
     **/
    public void render() {

    }

    public void update(float dt) {}

    /***
     * Used mainly to tell if sprites are in bounds before rendering. Off by default
     * in basics...
     ***/
    public boolean isInBounds() {
        // Stub, basics never get rendered...... theyre empty,
        return false;
    }



}
