package com.funkinjava;

import static com.raylib.Raylib.RLGL_VERSION;


import com.raylib.Colors;
import com.raylib.Raylib;
import com.funkinjava.engine.Game;
import com.funkinjava.engine.backend.Signal;
public class Main {



    private final int TARGET_FPS = 60;
    private final int WINDOW_WIDTH = 1280;
    private final int WINDOW_HEIGHT = 720;
    private final String WINDOW_TITLE = "Funkin' Shit Engine";

    public float dT = 1 / TARGET_FPS;
    public Signal<Float> updateCB;
    public Signal<Void> renderCB;

    public Game game;

    public void run() {
        init();
        System.out.println("[Engine] Runtime lifecycle loop initialized.");
        startMainLoop();

        Raylib.CloseAudioDevice();
        Raylib.CloseWindow();
    }

    private void init() {
        updateCB = new Signal<Float>();
        renderCB = new Signal<Void>();
 

        System.out.println("[Engine] Initializing context: " + WINDOW_TITLE);
        System.out.println(
                "[Engine] Target resolution: " + WINDOW_WIDTH + "x" + WINDOW_HEIGHT + " @ " + TARGET_FPS + "Hz");

        System.out.println("[Host] OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")");
        System.out.println("[Host] CPU Cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println(
                "[Host] VM Runtime: " + System.getProperty("java.vm.name") + " " + System.getProperty("java.version"));

        Raylib.SetConfigFlags(4);
        Raylib.InitWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE);
        Raylib.SetTargetFPS(TARGET_FPS);

        game = new Game();
        game.init(this);
        if (Raylib.IsWindowReady()) {
            System.out.println("[Graphics] Monitor Name: " + Raylib.GetMonitorName(Raylib.GetCurrentMonitor()));
            System.out.println("[Graphics] Raylib GL Context: Version " + RLGL_VERSION);
        } else {
            System.err.println("[Engine] CRITICAL: Failed to initialize window context.");
        }
        Raylib.InitAudioDevice();
    }

    private void startMainLoop() {



        while (!Raylib.WindowShouldClose()) {



            dT = Raylib.GetFrameTime();
            update(dT);
            Raylib.BeginDrawing();
            Raylib.ClearBackground(Colors.BLACK);
            render();

            Raylib.EndDrawing();
        }
    }

    private void update(float dt) {
        updateCB.dispatch(dt);

    }

    long peakMem;

    private void render() {
        renderCB.dispatch(null);
    }

    public static void main(String[] args) {
        new Main().run();
    }

}
