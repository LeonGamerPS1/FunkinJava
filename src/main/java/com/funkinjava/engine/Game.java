package com.funkinjava.engine;

import com.raylib.Colors;
import com.raylib.Raylib;

import static com.raylib.Raylib.KEY_A;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.funkinjava.Main;

public class Game {
    public static Game instance;
    public static int frameRate = 60;
    Raylib.Texture tex;

    public static float GAME_WIDTH = 1280.0f;
    public static float GAME_HEIGHT = 720.0f;

    public static List<Camera> Cameras;
    public static Camera mainCamera;

    private State _requestedState;

    public static State state;

    public Game() {
        instance = this;
        Cameras = new ArrayList<>();
    }

    public static void resizeGame(int w, int h) {
        GAME_WIDTH = w;
        GAME_HEIGHT = h;
    }

    public void init(Main main) {

    }

    public void Render() {
        if (Raylib.IsKeyPressed(KEY_A)) {
            Game.resizeGame(640, 480);
        }
        Raylib.ClearBackground(Colors.BLACK);
        renderGame();
        Raylib.DrawFPS(0, 0);
    }

       private void renderGame() {
        float screenW = (float) Raylib.GetScreenWidth();
        float screenH = (float) Raylib.GetScreenHeight();

        float scale = Math.min(screenW / GAME_WIDTH, screenH / GAME_HEIGHT);

        float rectW = GAME_WIDTH * scale;
        float rectH = GAME_HEIGHT * scale;
        float rectX = (screenW - rectW) / 2.0f;
        float rectY = (screenH - rectH) / 2.0f;

        // 1. Render the base game state first (if it doesn't use the camera array)
        if (state != null && state.canRender) {
            Raylib.BeginScissorMode((int) rectX, (int) rectY, (int) rectW, (int) rectH);
            Raylib.rlPushMatrix();
            Raylib.rlTranslatef(rectX, rectY, 0.0f);
            Raylib.rlScalef(scale, scale, 1.0f);
            
            state.render();
            
            Raylib.rlPopMatrix();
            Raylib.EndScissorMode();
        }
            
        // 2. Render each camera with the correct scale and scissor boundaries
        for (Camera camera : Cameras) {
            // Apply scissor mode so the camera doesn't bleed into the black letterbox bars
            Raylib.BeginScissorMode((int) rectX, (int) rectY, (int) rectW, (int) rectH);

            // Open the camera mode
            Raylib.BeginMode2D(camera.cam);

            // Push a custom matrix INSIDE the camera mode to handle the letterbox scale
            Raylib.rlPushMatrix();
            Raylib.rlTranslatef(rectX, rectY, 0.0f);
            Raylib.rlScalef(scale, scale, 1.0f);

            // DRAW THE CAMERA CONTENTS HERE while the scaling matrix is active!
            camera.render();

            // Clean up the matrix and camera blocks in the exact reverse order they were opened
            Raylib.rlPopMatrix();
            
            Raylib.EndMode2D();
            
            Raylib.EndScissorMode();
        }
    }


    public void Update(float dT) {
        for (Camera camera : Cameras) {
            camera.update(dT);
        }
        if (state != null && state.canUpdate)
            state.update(dT);
        if (_requestedState != null)
            resetState();
    }

    public static void SetTargetFPS(int FPS) {
        frameRate = FPS;
        Raylib.SetTargetFPS(FPS);
    }

    private void resetState() {
        if (state != null) {
            state.destroy();
            state = null;
        }
        if (Game.mainCamera != null) {
            Game.mainCamera.destroy();
            Game.mainCamera = null;
            Game.Cameras.remove(Game.mainCamera);
        }

        for (Camera camera : Cameras) {
            camera.destroy();
            camera = null;
        }
        Cameras = null;
        Cameras = new ArrayList<>();
        Game.mainCamera = new Camera(1);
        Cameras.add(mainCamera);
        state = _requestedState;
        state.create();
    }

    public static void switchState(State state) {
        Game.instance._requestedState = state;
    }
}
