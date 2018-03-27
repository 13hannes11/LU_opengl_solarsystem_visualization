package org.kuchelmeister.solarsystem.geometry;

import org.apache.commons.lang3.time.StopWatch;

public class Moon extends Planet {
    Planet homePlanet;

    public Moon(final Planet homePlanter, final Orbit orb, final Universe universe, final float radius,
            final String texture, final float yearLength, final float dayLength) {
        super(orb, universe, radius, texture, yearLength, dayLength);
        this.homePlanet = homePlanter;
    }

    @Override
    public void updateLogic(final StopWatch sWatch) {
        super.orbit.setCentre(homePlanet.getCentre());
        ;
        super.updateLogic(sWatch);
    }

}
