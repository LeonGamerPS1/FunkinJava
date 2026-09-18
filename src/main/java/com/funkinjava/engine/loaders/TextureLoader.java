package com.funkinjava.engine.loaders;

import java.util.HashMap;
import java.util.Map;

import com.raylib.Raylib;
import com.raylib.Raylib.Color;
import com.raylib.Raylib.Image;
import com.raylib.Raylib.Texture;;

public class TextureLoader {
    static Map<String, Texture> textureStore = new HashMap<String, Texture>();

    public static Texture makeGraphic(int w, int h, Color color) {
        String key = w + "x" + h + 'r' + color.r() + 'g' + color.g() + 'b' + color.b();
        if (textureStore.containsKey(key))
            return textureStore.get(key);
        Image myImage = Raylib.GenImageColor(w, h, color);
        Texture myTexture = fromImage(myImage);
        textureStore.put(key, myTexture);
        Raylib.UnloadImage(myImage);
        return myTexture;
    }

    public static Texture fromImage(Image image) {
        return Raylib.LoadTextureFromImage(image);
    }

    public static Texture fromPath(String path) {
        String key = path;
        if (textureStore.containsKey(key))
            return textureStore.get(key);
        Texture tex = Raylib.LoadTexture(path);
        textureStore.put(key, tex);
        return tex;
    }
}
