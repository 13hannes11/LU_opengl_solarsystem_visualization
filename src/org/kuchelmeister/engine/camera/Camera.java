package org.kuchelmeister.engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera {
    public static final float MAX_VERTICAL_ANGLE = 85.0f;
    private Vector3f position;
    private float fieldOfView;

    private float horizontalAngle;
    private float verticalAngle;

    private float nearPlane;
    private float farPlane;

    private float viewportAspectRatio;

    private Matrix4f view;
    private Matrix4f projection;

    public Camera() {
        this.setPosition(new Vector3f(0.0f, 0.0f, 0.0f));
        this.setHorizontalAngle(0.0f);
        this.setVerticalAngle(0.0f);
        this.setFieldOfView(50.0f);
        this.setNearAndFarPlanes(0.01f, 100.0f);
        this.setViewportAspectRatio(4.0f / 3.0f);
    }

    public Camera(final Camera c) {
        this.setPosition(new Vector3f(c.getPosition()));
        this.setHorizontalAngle(c.getHorizontalAngle());
        this.setVerticalAngle(c.getVerticalAngle());
        this.setFieldOfView(c.getFieldOfView());
        this.setNearAndFarPlanes(c.getNearPlane(), c.getFarPlane());
        this.setViewportAspectRatio(c.getViewportAspectRatio());
    }

    public static float[] toFloatArray(final Matrix4f m) {
        final float[] arr = new float[16];
        m.get(arr);
        return arr;
    }

    public void lookAt(final Vector3f lookAt) {
        assert (!this.position.equals(lookAt));
        final Vector3f direction = (new Vector3f(this.position)).negate().add(lookAt);
        direction.normalize();
        this.verticalAngle = (float) Math.toDegrees(Math.asin(-direction.y));
        this.horizontalAngle = (float) Math.toDegrees(-Math.atan2(-direction.x, -direction.z));
        normalizeAngles();
    }

    public void offsetPosition(final Vector3f offset) {
        position.add(offset);
    }

    public void setNearAndFarPlanes(final float nearPlane, final float farPlane) {
        assert nearPlane > 0.0f && farPlane > nearPlane;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public Matrix4f getOrientation() {
        Matrix4f orientationMatrix = new Matrix4f();
        orientationMatrix = orientationMatrix.rotate((float) Math.toRadians(verticalAngle),
                new Vector3f(1.0f, 0.0f, 0.0f));
        orientationMatrix = orientationMatrix.rotate((float) Math.toRadians(horizontalAngle),
                new Vector3f(0.0f, 1.0f, 0.0f));
        return orientationMatrix;
    }

    public void offsetOrientation(final float upAngle, final float rightAngle) {
        this.horizontalAngle += rightAngle;
        this.verticalAngle += upAngle;
    }

    public Matrix4f getProjection() {
        Matrix4f m = new Matrix4f();
        m = m.setPerspective((float) Math.toRadians(fieldOfView), viewportAspectRatio, nearPlane, farPlane);
        return m;
    }

    public Matrix4f getMatrix() {
        return getProjection().mul(getView());
    }

    public Matrix4f getView() {
        final Matrix4f m = getOrientation();
        Matrix4f p = new Matrix4f();
        p = p.translate(new Vector3f(this.getPosition()).negate());
        return m.mul(p);
    }

    public Vector3f forwardVec() {
        Vector4f vec = new Vector4f(0.0f, 0.0f, -1.0f, 1.0f);
        vec = vec.mul(getOrientation());
        return new Vector3f(vec.x, vec.y, vec.z);
    }

    public Vector3f rightVec() {
        Vector4f vec = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
        vec = vec.mul(getOrientation());
        return new Vector3f(vec.x, vec.y, vec.z);
    }

    public Vector3f upVec() {
        Vector4f vec = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
        vec = vec.mul(getOrientation());
        return new Vector3f(vec.x, vec.y, vec.z);
    }

    public float getFarPlane() {
        return farPlane;
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public float getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(final float fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    public float getHorizontalAngle() {
        return horizontalAngle;
    }

    public void setHorizontalAngle(final float horizontalAngle) {
        this.horizontalAngle = horizontalAngle;
    }

    public float getVerticalAngle() {
        return verticalAngle;
    }

    public void setVerticalAngle(final float verticalAngle) {
        this.verticalAngle = verticalAngle;
    }

    public float getViewportAspectRatio() {
        return viewportAspectRatio;
    }

    public void setViewportAspectRatio(final float viewportAspectRatio) {
        assert viewportAspectRatio > 0.0f;
        this.viewportAspectRatio = viewportAspectRatio;
    }

    public void normalizeAngles() {
        horizontalAngle = horizontalAngle % 360.0f;

        if (horizontalAngle < 0) {
            horizontalAngle += 360.0f;
        }
        if (verticalAngle > MAX_VERTICAL_ANGLE) {
            verticalAngle = MAX_VERTICAL_ANGLE;
        } else if (verticalAngle < -MAX_VERTICAL_ANGLE) {
            verticalAngle = -MAX_VERTICAL_ANGLE;
        }
    }

}
