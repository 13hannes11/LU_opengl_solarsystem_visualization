package org.kuchelmeister.engine.geometry.primitive;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.joml.Vector3f;
import org.kuchelmeister.engine.geometry.SubGeometry;
import org.kuchelmeister.engine.geometry.vertex.AbstractVertex;

public class Triangle implements SubGeometry {
    private final AbstractVertex a, b, c;

    /**
     * Initialize new Triangle, a, b and c should be positioned
     * counterclockwise.
     */
    public Triangle(final AbstractVertex a, final AbstractVertex b, final AbstractVertex c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public List<Vector3f> getPoints() {
        final List<Vector3f> pList = new LinkedList<>();
        pList.add(getA());
        pList.add(getB());
        pList.add(getC());
        return pList;
    }

    public Vector3f getA() {
        return a.getPosition();
    }

    public Vector3f getB() {
        return b.getPosition();
    }

    public Vector3f getC() {
        return c.getPosition();
    }

    public float[] getVertices() {
        return ArrayUtils.addAll(ArrayUtils.addAll(a.getFloatArray(), b.getFloatArray()), c.getFloatArray());
    }

    @Override
    public List<Triangle> getTriangles() {
        final List<Triangle> list = new LinkedList<>();
        list.add(this);
        return list;
    }
}
