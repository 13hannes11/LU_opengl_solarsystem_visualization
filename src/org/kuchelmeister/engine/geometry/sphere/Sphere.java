package org.kuchelmeister.engine.geometry.sphere;

import java.util.LinkedList;

import org.joml.Vector3f;
import org.kuchelmeister.engine.geometry.primitive.Triangle;
import org.kuchelmeister.engine.geometry.vertex.ObjectVertex;

public class Sphere extends Icosahedron {
    public Sphere(final Vector3f centre, final float radius, final String texture, final int recursionLevel) {
        super(centre, radius, texture);

        this.faces.clear();
        for (int j = 0; j < recursionLevel; j++) {
            final LinkedList<Triangle> tmpList = new LinkedList<>();
            for (int i = this.subGeometry.size() - 1; i >= 0; i--) {
                final Triangle t = (Triangle) this.subGeometry.get(i);
                final Vector3f a = t.getA();
                final Vector3f b = t.getB();
                final Vector3f c = t.getC();

                Vector3f o = Sphere.findMiddle(a, b);
                Vector3f p = Sphere.findMiddle(b, c);
                Vector3f q = Sphere.findMiddle(c, a);

                o = Sphere.setDistanceFromPoint(o, radius, centre);
                p = Sphere.setDistanceFromPoint(p, radius, centre);
                q = Sphere.setDistanceFromPoint(q, radius, centre);

                tmpList.add(new Triangle(
                        new ObjectVertex(a, Icosahedron.projectOnTexture(a, centre), a),
                        new ObjectVertex(o, Icosahedron.projectOnTexture(o, centre), o),
                        new ObjectVertex(q, Icosahedron.projectOnTexture(q, centre), q)));
                tmpList.add(new Triangle(
                        new ObjectVertex(o, Icosahedron.projectOnTexture(o, centre), o),
                        new ObjectVertex(b, Icosahedron.projectOnTexture(b, centre), b),
                        new ObjectVertex(p, Icosahedron.projectOnTexture(p, centre), p)));
                tmpList.add(new Triangle(
                        new ObjectVertex(p, Icosahedron.projectOnTexture(p, centre), p),
                        new ObjectVertex(c, Icosahedron.projectOnTexture(c, centre), c),
                        new ObjectVertex(q, Icosahedron.projectOnTexture(q, centre), q)));
                tmpList.add(new Triangle(
                        new ObjectVertex(o, Icosahedron.projectOnTexture(o, centre), o),
                        new ObjectVertex(p, Icosahedron.projectOnTexture(p, centre), p),
                        new ObjectVertex(q, Icosahedron.projectOnTexture(q, centre), q)));
            }
            this.subGeometry.clear();
            this.subGeometry.addAll(tmpList);

            // System.out.println(this.subGeometry.size());
        }
    }

    public static Vector3f findMiddle(final Vector3f a, final Vector3f b) {
        // Total calculation a + 0.5*(b - a)
        final Vector3f negA = (new Vector3f(a)).negate();
        Vector3f tmp = new Vector3f(b);
        // b - a
        tmp = tmp.add(negA);
        // 0.5 * (b-a)
        tmp = tmp.mul(0.5f);
        // a + 0.5 * (b-a)
        tmp = tmp.add(a);
        return tmp;
    }

    public static Vector3f setDistanceFromPoint(final Vector3f a, final float distance, final Vector3f point) {
        Vector3f direction = new Vector3f(a);
        final Vector3f negPoint = (new Vector3f(point)).negate();

        direction = direction.add(negPoint);
        direction.mul(distance / direction.length());

        return direction.add(point);
    }
}
