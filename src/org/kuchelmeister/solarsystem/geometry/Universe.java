package org.kuchelmeister.solarsystem.geometry;

public class Universe {
    /**
     * radius of the earth in AU
     */
    public static float EARTH_RADIUS = 0.09f;
    public static float EART_DAY_LENGTH = 1.0f;

    /**
     * The timescale multiplyer 1 means one second is one day
     */
    private float timeScale;
    private boolean paused;

    public Universe() {
        timeScale = 80.0f;
        paused = false;
    }

    public float getTimeScale() {
        if (paused) {
            return 0.0f;
        }
        return timeScale;
    }

    public void tooglePause() {
        paused = !paused;
    }

    public void doubleTimeScale() {
        timeScale *= 2;
    }

    public void halfTimeScale() {
        timeScale /= 2;
    }

    public boolean isPaused() {
        return paused;
    }

    public void reverseTime() {
        timeScale = -timeScale;
    }

}
