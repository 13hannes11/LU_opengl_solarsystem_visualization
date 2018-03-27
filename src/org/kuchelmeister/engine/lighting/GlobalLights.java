package org.kuchelmeister.engine.lighting;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.GL3;

import shader.Shader;

public class GlobalLights {
    private static GlobalLights globalLights;

    public static GlobalLights getInstance() {
        if (globalLights == null) {
            globalLights = new GlobalLights();
        }
        return globalLights;
    }

    public static int MAX_LIGHTS = 10;
    List<Light> lights;

    public GlobalLights() {
        lights = new LinkedList<>();
    }

    public void add(final Light light) {
        if (lights.size() < MAX_LIGHTS) {
            lights.add(light);
        }
    }

    public void glSetUniforms(final GL3 gl, final Shader shader) {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            final int lightPositionLocation = gl.glGetUniformLocation(shader.getProgramId(),
                    "lights[" + i + "].position");
            final int lightEnabledLocation = gl.glGetUniformLocation(shader.getProgramId(),
                    "lights[" + i + "].enabled");

            if (lights.size() > i) {
                if (lightPositionLocation != -1) {
                    gl.glUniform3f(lightPositionLocation, lights.get(i).position.x, lights.get(i).position.y,
                            lights.get(i).position.z);
                }
                if (lightEnabledLocation != -1) {
                    gl.glUniform1i(lightEnabledLocation, 1);
                }
            } else {
                if (lightEnabledLocation != -1) {
                    gl.glUniform1i(lightEnabledLocation, 0);
                }
            }
        }

    }
}
