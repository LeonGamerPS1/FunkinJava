package com.funkinjava.engine;

import com.raylib.Colors;
import com.raylib.Raylib;


import static com.raylib.Raylib.KEY_A;




import com.funkinjava.Main;

public class Game {
    public static int frameRate = 60;
    Raylib.Texture tex;

    public static float GAME_WIDTH = 1280.0f;
    public static float GAME_HEIGHT = 720.0f;


    public Game() {
    }

    public static void resizeGame(int w, int h) {
        GAME_WIDTH = w;
        GAME_HEIGHT = h;
    }

    public void init(Main main) {


        main.renderCB.addCallback(v -> Render());
        main.updateCB.addCallback(v -> Update(v));
        tex = Raylib.LoadTexture("assets/images/notes/default.png");




    }

    public void Render() {
        if(Raylib.IsKeyPressed(KEY_A)) {
            Game.resizeGame(640,480);
        }
        float screenW = (float) Raylib.GetScreenWidth();
        float screenH = (float) Raylib.GetScreenHeight();

        float scale = Math.min(screenW / GAME_WIDTH, screenH / GAME_HEIGHT);

        float rectW = GAME_WIDTH * scale;
        float rectH = GAME_HEIGHT * scale;
        float rectX = (screenW - rectW) / 2.0f;
        float rectY = (screenH - rectH) / 2.0f;

        Raylib.ClearBackground(Colors.BLACK);

        Raylib.BeginScissorMode((int) rectX, (int) rectY, (int) rectW, (int) rectH);
        Raylib.ClearBackground(Colors.GRAY);

        Raylib.rlPushMatrix();

        Raylib.rlTranslatef(rectX, rectY, 0.0f);
        Raylib.rlScalef(scale, scale, 1.0f);

        renderGame();

        Raylib.rlPopMatrix();
        Raylib.EndScissorMode();
        Raylib.DrawFPS(0, 0);
    }

    private void renderGame() {
       
    }

    public void Update(float dT) {
    }

    public static void SetTargetFPS(int FPS) {
        frameRate = FPS;
        Raylib.SetTargetFPS(FPS);
    }
}
