package org.kuchelmeister.engine.geometry.vertex;

import org.apache.commons.lang3.ArrayUtils;
import org.joml.Vector3f;

public class SkyboxVertex extends AbstractVertex {
    Vector3f texture;

    public SkyboxVertex(final Vector3f position, final Vector3f texture) {
        super(position);
        this.texture = texture;
    }

    public SkyboxVertex(final Vector3f position) {
        this(position, position);
    }

    @Override
    public float[] getFloatArray() {
        final float[] arr = new float[3];
        arr[0] = texture.x;
        arr[1] = texture.y;
        arr[2] = texture.z;
        return ArrayUtils.addAll(super.getFloatArray(), arr);
    }

}
