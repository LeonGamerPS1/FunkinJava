package com.funkinjava.engine.loaders;

import java.util.HashMap;
import java.util.Map;

import com.raylib.Raylib;
import com.raylib.Raylib.Music;
import com.raylib.Raylib.Sound;

public class SoundLoader {
    static Map<String, Sound> sndStore = new HashMap<String, Sound>();
    static Map<String, Music> musicStore = new HashMap<String, Music>();

    static public Sound fromPath(String sndPath) {
        if (sndStore.containsKey(sndPath)) {
            return sndStore.get(sndPath);
        }

        Sound sound = Raylib.LoadSound(sndPath);
        sndStore.put(sndPath, sound);
        return sound;
    }

    static public Music fromPathMusic(String sndPath) {
        if (musicStore.containsKey(sndPath)) {
            return musicStore.get(sndPath);
        }

        Music sound = Raylib.LoadMusicStream(sndPath);
        musicStore.put(sndPath, sound);
        return sound;
    }
}
