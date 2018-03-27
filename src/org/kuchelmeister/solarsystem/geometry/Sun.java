package org.kuchelmeister.solarsystem.geometry;

import org.joml.Vector3f;
import org.kuchelmeister.engine.lighting.Light;
import org.kuchelmeister.engine.lighting.Material;

public class Sun extends SphericalSpaceObject {
    public Sun(final Vector3f centre, final float radius, final String texture) {
        super(centre, radius, texture);
        this.setMaterial(new Material(1.0f, 1.0f, 0.0f));
        this.getGlobalLights().add(new Light(centre));
    }
}
