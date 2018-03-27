package org.kuchelmeister.engine.geometry.vertex;

import org.joml.Vector3f;

public abstract class AbstractVertex implements VertexInterface {
    private final Vector3f position;

    public AbstractVertex(final Vector3f position) {
        this.position = position;
    }

    @Override
    public float[] getFloatArray() {
        final float[] fl = new float[3];
        fl[0] = position.x;
        fl[1] = position.y;
        fl[2] = position.z;
        return fl;
    }

    public Vector3f getPosition() {
        return position;
    }

}
