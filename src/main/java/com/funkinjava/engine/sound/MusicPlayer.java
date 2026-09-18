package com.funkinjava.engine.sound;

import com.funkinjava.engine.Basic;
import com.funkinjava.engine.loaders.SoundLoader;
import com.raylib.Raylib;
import com.raylib.Raylib.Music;

public class MusicPlayer extends Basic {
    public Music _source;
    public float pitch = 1f;
    public float volume = 1.0f;
    public float time = 0f;
    private boolean _explicitlyPaused = false; // Tracks if the user chose to pause

    public MusicPlayer(String path) {
        super();
        load(path);
    }

    public MusicPlayer load(String path) {

        _source = SoundLoader.fromPathMusic(path);
        this.time = 0f;
        this._explicitlyPaused = false;
        return this;
    }

    public MusicPlayer play(Float startTime) {
        if (_source == null)
            return this;

        _explicitlyPaused = false;
        if (!isPlaying()) {
            if (startTime != null) {
                seek(startTime);
            }
            Raylib.PlayMusicStream(_source);
        } else if (startTime != null) {
            seek(startTime);
        }
        return this;
    }

    public MusicPlayer pause() {
        if (_source != null) {
            _explicitlyPaused = true;
            Raylib.PauseMusicStream(_source);
        }
        return this;
    }

    public MusicPlayer resume() {
        if (_source != null) {
            _explicitlyPaused = false;
            Raylib.ResumeMusicStream(_source);
        }
        return this;
    }

    public boolean isPlaying() {
        return _source != null && Raylib.IsMusicStreamPlaying(_source);
    }

    public boolean isPaused() {
        return _source != null && _explicitlyPaused;
    }

    public Float getTimePosition() {
        if (_source == null)
            return 0f;
        time = Raylib.GetMusicTimePlayed(_source);
        return time;
    }

    public MusicPlayer seek(float time) {
        if (_source == null)
            return this;
        this.time = time;
        Raylib.SeekMusicStream(_source, time);
        return this;
    }

    public MusicPlayer setPitch(float pitch) {
        if (_source == null)
            return this;
        this.pitch = pitch;
        Raylib.SetMusicPitch(_source, pitch);
        return this;
    }

    public MusicPlayer setVolume(float vol) {
        if (_source == null)
            return this;
        this.volume = vol;
        Raylib.SetMusicVolume(_source, vol);
        return this;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        // Fix: Update stream even when finishing a loop, unless explicitly paused.
        if (_source != null && !_explicitlyPaused) {
            Raylib.UpdateMusicStream(_source);
        }
    }
}
