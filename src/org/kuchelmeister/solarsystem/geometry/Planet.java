package org.kuchelmeister.solarsystem.geometry;

import org.apache.commons.lang3.time.StopWatch;
import org.joml.Vector3f;

public class Planet extends SphericalSpaceObject {
    /** The length of a year on this planet in hours */
    private final float yearLength;
    private final float dayLength;
    private final Universe universe;
    private float orbitDegrees;
    private float dayAxisDegrees;
    protected final Orbit orbit;

    public Planet(final Orbit orb, final Universe universe, final float radius, final String texture,
            final float yearLength, final float dayLength) {
        super(orb.getCentre(), radius, texture);
        this.universe = universe;
        this.yearLength = yearLength;
        this.orbitDegrees = 0;
        this.orbit = orb;
        dayAxisDegrees = 0f;
        this.dayLength = dayLength * Universe.EART_DAY_LENGTH;
    }

    public Vector3f getCentre() {
        return orbit.getPoint(orbitDegrees);
    }

    @Override
    public void updateLogic(final StopWatch sWatch) {
        final double elapsedDays = sWatch.getTime() / 1000.0d * universe.getTimeScale();

        final float dayOrbitRoatation = 360.0f / yearLength;
        orbitDegrees += (float) (dayOrbitRoatation * elapsedDays);

        if (orbitDegrees >= 360.0f) {
            orbitDegrees %= 360.0f;
        }
        // System.out.println("Position of Planet: " +
        // orbit.getPoint(orbitDegrees));

        // TODO: implement rotattion axis
        final float dayAxisRotation = 360f / dayLength;
        dayAxisDegrees += (float) (elapsedDays * dayAxisRotation);
        this.setRotation(0.0f, dayAxisDegrees, 0.0f);
        if (dayAxisDegrees >= 360.0f) {
            dayAxisDegrees %= 360.0f;
        }

        this.setPosition(orbit.getPoint(orbitDegrees));
    }

}
