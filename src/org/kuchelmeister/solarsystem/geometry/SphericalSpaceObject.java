package org.kuchelmeister.solarsystem.geometry;

import org.joml.Vector3f;
import org.kuchelmeister.engine.geometry.sphere.Sphere;

public abstract class SphericalSpaceObject extends Sphere {
    public SphericalSpaceObject(final Vector3f centre, final float radius, final String texture) {
        super(centre, radius * Universe.EARTH_RADIUS, texture, 3);

    }
}
