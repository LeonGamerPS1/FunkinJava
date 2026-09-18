package com.funkinjava.engine.sound;

import java.util.HashMap;
import java.util.Map;

import com.funkinjava.engine.Basic;
import com.funkinjava.engine.loaders.SoundLoader;
import com.raylib.Raylib;
import com.raylib.Raylib.Sound;

public class Snd extends Basic {
    public Sound _source;
    private boolean _isPaused = false;
    private float _elapsedTime = 0f;

    public Snd(String sndpath) {
        super();
        if (sndpath != null)
            _source = SoundLoader.fromPath(sndpath);
    }

    public float getLength() {
        if (_source == null) return 0f;
        return (float) _source.frameCount() / 44100f; 
    }

    public float getTime() {
        return _elapsedTime;
    }

    public void update(float elapsed) {
        if (IsPlaying()) {
            _elapsedTime += elapsed;
            float length = getLength();
            if (_elapsedTime > length) {
                _elapsedTime = length;
            }
        }
    }

    public void Play() {
        if (_source != null) {
            Raylib.PlaySound(_source);
            _isPaused = false;
            _elapsedTime = 0f;
        }
    }

    public void Pause() {
        if (_source != null) {
            Raylib.PauseSound(_source);
            _isPaused = true;
        }
    }

    public void Resume() {
        if (_source != null && _isPaused) {
            Raylib.ResumeSound(_source);
            _isPaused = false;
        }
    }

    public void Stop() {
        if (_source != null) {
            Raylib.StopSound(_source);
            _isPaused = false;
            _elapsedTime = 0f;
        }
    }

    public boolean IsPlaying() {
        if (_source == null) return false;
        return Raylib.IsSoundPlaying(_source);
    }

    public boolean IsPaused() {
        return _isPaused;
    }

    public void SetVolume(float volume) {
        if (_source != null) Raylib.SetSoundVolume(_source, volume);
    }

    public void SetPitch(float pitch) {
        if (_source != null) Raylib.SetSoundPitch(_source, pitch);
    }

    public void SetPan(float pan) {
        if (_source != null) Raylib.SetSoundPan(_source, pan);
    }
}
