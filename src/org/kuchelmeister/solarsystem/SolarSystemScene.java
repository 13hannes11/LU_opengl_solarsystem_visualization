package org.kuchelmeister.solarsystem;

import org.joml.Vector3f;
import org.kuchelmeister.engine.Scene;
import org.kuchelmeister.solarsystem.geometry.Moon;
import org.kuchelmeister.solarsystem.geometry.Orbit;
import org.kuchelmeister.solarsystem.geometry.Planet;
import org.kuchelmeister.solarsystem.geometry.Sun;
import org.kuchelmeister.solarsystem.geometry.Universe;
import org.kuchelmeister.solarsystem.input.UniverseKeyListener;

public class SolarSystemScene extends Scene {
	private static final long serialVersionUID = 3875025579198640196L;

	public SolarSystemScene(final int width, final int height, final String title) {
		super(width, height, title);
		camera.setPosition(new Vector3f(0.0f, 8.0f, 0.0f));
		camera.lookAt(new Vector3f(0.0f, 0.0f, 0.0f));
		final Universe uv = new Universe();

		final UniverseKeyListener universeKeyListener = new UniverseKeyListener(uv, camera);
		this.getCanvas().addKeyListener(universeKeyListener);
		// Mercury
		final Planet mercury = new Planet(new Orbit(new Vector3f(0.0f, 0.0f, 0.0f), 0.467f, 0.308f), uv, 0.38f,
				"spheres/mercury.jpg", 115.88f, 57.94f);
		this.addGraphicalObject(mercury);
		universeKeyListener.setKeySphericalSpaceObject(mercury, 2);

		// Venus
		final Planet venus = new Planet(new Orbit(new Vector3f(0.0f, 0.0f, 0.0f), 0.723f, 0.718f), uv, 0.38f,
				"spheres/venus.jpg", 224.701f, 243.0f);
		this.addGraphicalObject(venus);
		universeKeyListener.setKeySphericalSpaceObject(venus, 3);

		// Earth
		final Planet earth = new Planet(new Orbit(new Vector3f(0.0f, 0.0f, 0.0f), 0.98f, 1.02f), uv, 1.0f,
				"spheres/earth.jpg", 365.256f, 1.0f);
		this.addGraphicalObject(earth);
		universeKeyListener.setKeySphericalSpaceObject(earth, 4);
		// Moon
		this.addGraphicalObject(new Moon(earth,
				new Orbit(new Vector3f(0.0f, 0.0f, 0.0f), 2 * Universe.EARTH_RADIUS, 2 * Universe.EARTH_RADIUS), uv,
				0.3f, "spheres/moon.jpg", 29.530589f, 29.530589f));
		// Mars
		final Planet mars = new Planet(new Orbit(new Vector3f(0.0f, 0.0f, 0.0f), 1.5f, 1.4f), uv, 0.2657f * 2,
				"spheres/mars.jpg", 686.971f, 1.0275f);
		this.addGraphicalObject(mars);
		universeKeyListener.setKeySphericalSpaceObject(mars, 5);

		// Jupiter
		final Planet jupiter = new Planet(new Orbit(new Vector3f(0.0f, 0.0f, 0.0f), 5.2f - 3.3f, 4.9f - 3.3f), uv,
				12.0f / 12f, "spheres/jupiter.jpg", 4330.0f, 0.4132f);
		this.addGraphicalObject(jupiter);
		universeKeyListener.setKeySphericalSpaceObject(jupiter, 6);

		// Saturn
		final Planet saturn = new Planet(new Orbit(new Vector3f(0.0f, 0.0f, 0.0f), 9.5f - 7f, 9f - 7f), uv, 10.0f / 10f,
				"spheres/saturn.jpg", 10751.805f, 0.45f);
		this.addGraphicalObject(saturn);
		universeKeyListener.setKeySphericalSpaceObject(saturn, 7);

		// Uranus
		final Planet uranus = new Planet(new Orbit(new Vector3f(0.0f, 0.0f, 0.0f), 19f - 16f, 18f - 16f), uv, 4.0f / 6,
				"spheres/uranus.jpg", 30660.0f, 0.72f);
		this.addGraphicalObject(uranus);
		universeKeyListener.setKeySphericalSpaceObject(uranus, 8);

		// Neptun
		final Planet neptun = new Planet(new Orbit(new Vector3f(0.0f, 0.0f, 0.0f), 30f - 26.0f, 29f - 26.0f), uv,
				3.88f / 6, "spheres/neptune.jpg", 30660.0f, 0.673f);
		this.addGraphicalObject(neptun);
		universeKeyListener.setKeySphericalSpaceObject(neptun, 9);

		final Sun sun = new Sun(new Vector3f(0.0f, 0.0f, 0.0f), 3.0f, "spheres/sun.jpg");
		this.addGraphicalObject(sun);
		universeKeyListener.setKeySphericalSpaceObject(sun, 1);

		this.setSkybox(new String[] { "skybox/space_left_0.png", "skybox/space_right_1.png", "skybox/space_down_2.png",
				"skybox/space_up_3.png", "skybox/space_front_4.png", "skybox/space_back_5.png" });
	}
}
