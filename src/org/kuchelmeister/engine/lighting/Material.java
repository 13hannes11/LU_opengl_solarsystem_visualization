package org.kuchelmeister.engine.lighting;

import org.joml.Vector3f;

import com.jogamp.opengl.GL3;

import shader.Shader;

public class Material {
    Vector3f ambient, diffuse, specular;
    int shininess;

    public Material(final float ambient, final float diffuse, final float specular) {
        this(new Vector3f(ambient, ambient, ambient),
                new Vector3f(diffuse, diffuse, diffuse),
                new Vector3f(specular, specular, specular),
                2);
    }

    public Material(final Vector3f ambient, final Vector3f diffuse, final Vector3f specular, final int shininess) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    public static Material getDefault() {
        final float ambient = 0.4f;
        final float diffuse = 1.0f;
        final float specular = 0.5f;
        return new Material(ambient, diffuse, specular);
    }

    public void glSetUniforms(final GL3 gl, final Shader shader) {
        final int materialAmbientPos = gl.glGetUniformLocation(shader.getProgramId(), "material.ambient");
        if (materialAmbientPos != -1) {
            gl.glUniform3f(materialAmbientPos, ambient.x, ambient.y, ambient.z);
        }
        final int materialDiffuseLocation = gl.glGetUniformLocation(shader.getProgramId(), "material.diffuse");
        if (materialDiffuseLocation != -1) {
            gl.glUniform3f(materialDiffuseLocation, diffuse.x, diffuse.y, diffuse.z);
        }
        final int materialSpecularLocation = gl.glGetUniformLocation(shader.getProgramId(), "material.specular");
        if (materialSpecularLocation != -1) {
            gl.glUniform3f(materialSpecularLocation, specular.x, specular.y, specular.z);
        }
        final int materialShininessLocation = gl.glGetUniformLocation(shader.getProgramId(), "material.shininess");
        if (materialShininessLocation != -1) {
            gl.glUniform1i(materialShininessLocation, shininess);
        }
    }
}
