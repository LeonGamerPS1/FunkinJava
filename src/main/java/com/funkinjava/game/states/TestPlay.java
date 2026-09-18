package com.funkinjava.game.states;

import com.funkinjava.engine.*;

public class TestPlay extends State {
    @Override
    public void create() {
        super.create();
        Sprite sprite = new Sprite(0, 0);
        sprite.loadGraphic("assets/images/notes/default.png");
        add(sprite);
        
    }
}
