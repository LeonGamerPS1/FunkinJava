package com.funkinjava.engine.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Signal<T> {

    private final List<Consumer<T>> callbacks = new ArrayList<>();

    public void addCallback(Consumer<T> callback) {
        callbacks.add(callback);
    }

    public void removeCallback(Consumer<T> callback) {
        callbacks.remove(callback);
    }

    public void clear() {
        callbacks.clear();
    }

    public void dispatch(T args) {
        for (Consumer<T> callback : callbacks) {
            callback.accept(args);
        }
    }

    public int size() {
        return callbacks.size();
    }
}