package com.funkinjava.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.funkinjava.engine.backend.DrawCall;

public class TypedGroup<T extends Basic> extends Basic {
    public List<T> members;

    @Override
    public void constructor() {
        super.constructor();
        members = new ArrayList<T>();

    }

    public void forEach(Consumer<T> func) {
        for (T t : members) {
            if (t != null)
                func.accept(t);
        }
    }

    public void forEachUpdateable(Consumer<T> func) {
        forEach((T obj) -> {
            if (obj.canUpdate)
                func.accept(obj);
        });
    }

    public void forEachRenderable(Consumer<T> func) {
        forEach((T obj) -> {
            if (obj.canRender)
                func.accept(obj);
        });
    }

    @Override
    public void destroy() {
        forEach((T obj) -> {
            obj.destroy();
        });
        members = new ArrayList<>();
    }

    public void add(T obj) {
        if (obj != null && !members.contains(obj))
            members.add(obj);
    }

    public void remove(T obj) {
        if (members.contains(obj))
            members.remove(obj);
    }

    @Override
    public void render() {
        forEachRenderable(o -> {
            Camera renderCam = o.camera;
            if (renderCam == null)
                renderCam = Game.mainCamera;
            if(camera != null)
                renderCam = camera;
            if (o.isInBounds())
                renderCam.drawQueue.add(new DrawCall(p_ -> o.render()));
        });
    }

    @Override
    public void update(float dT) {
        super.update(dT);
        forEachUpdateable(m -> m.update(dT));
    }
}
