package org.kuchelmeister.engine.geometry.primitive;

import java.util.LinkedList;
import java.util.List;

import org.kuchelmeister.engine.geometry.SubGeometry;
import org.kuchelmeister.engine.geometry.vertex.AbstractVertex;

public class Quadrilateral implements SubGeometry {
    public AbstractVertex a, b, c, d;

    public Quadrilateral(final AbstractVertex a, final AbstractVertex b, final AbstractVertex c,
            final AbstractVertex d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public List<Triangle> getTriangles() {
        final List<Triangle> list = new LinkedList<>();
        list.add(new Triangle(a, b, c));
        list.add(new Triangle(c, d, a));
        return list;
    }

}
