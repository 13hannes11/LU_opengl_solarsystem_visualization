package org.kuchelmeister.engine.geometry.vertex;

import org.apache.commons.lang3.ArrayUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class ObjectVertex extends AbstractVertex {
    private final Vector2f texture;
    private final Vector3f normal;

    public ObjectVertex(final Vector3f position, final Vector2f texture, final Vector3f normal) {
        super(position);
        this.texture = texture;
        this.normal = normal;
    }

    @Override
    public float[] getFloatArray() {
        final float[] arr = new float[5];
        arr[0] = texture.x;
        arr[1] = texture.y;
        arr[2] = normal.x;
        arr[3] = normal.y;
        arr[4] = normal.z;
        return ArrayUtils.addAll(super.getFloatArray(), arr);
    }

}
