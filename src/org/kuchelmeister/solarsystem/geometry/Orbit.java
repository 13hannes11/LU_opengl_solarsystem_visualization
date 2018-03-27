package org.kuchelmeister.solarsystem.geometry;

import org.joml.Vector3f;

public class Orbit {
    private final float width, height;
    private Vector3f centre;

    public Orbit(final Vector3f centre, final float width, final float height) {
        this.height = height;
        this.width = width;
        this.centre = new Vector3f(centre);

    }

    public Vector3f getPoint(final float angle) {
        return new Vector3f(
                (float) (width * Math.cos(Math.toRadians(angle)) + centre.x),
                centre.y,
                (float) (height * Math.sin(Math.toRadians(angle)) + centre.z));
    }

    public void setCentre(final Vector3f centre) {
        this.centre = centre;
    }

    public Vector3f getCentre() {
        return centre;
    }
}
