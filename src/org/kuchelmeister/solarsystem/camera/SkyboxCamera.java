package org.kuchelmeister.solarsystem.camera;

import org.joml.Vector3f;
import org.kuchelmeister.engine.camera.Camera;

public class SkyboxCamera extends Camera {
    public SkyboxCamera(final Camera camera) {
        super(camera);
    }

    @Override
    public Vector3f getPosition() {
        return new Vector3f(0.0f, 0.0f, 0.0f);
    }

}
