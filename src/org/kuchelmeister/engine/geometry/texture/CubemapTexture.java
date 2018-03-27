package org.kuchelmeister.engine.geometry.texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class CubemapTexture implements ITexture {
    private IntBuffer textureID;
    private final String[] textures;

    /**
     * 
     * @param textures
     *            path to the textures (Order: right, left, top, bottom, back,
     *            front)
     */
    public CubemapTexture(final String[] textures) {
        assert (textures != null && textures.length == 6);
        this.textures = textures;
    }

    public void bind(final GL3 gl) {
        gl.glBindTexture(GL3.GL_TEXTURE_CUBE_MAP, textureID.get(0));
    }

    public boolean loadFromFile(final GL3 gl) {

        textureID = IntBuffer.allocate(1);
        gl.glGenTextures(1, textureID);
        gl.glBindTexture(GL3.GL_TEXTURE_CUBE_MAP, textureID.get(0));

        for (int i = 0; i < textures.length; i++) {
            try {
                final InputStream stream = new FileInputStream(new File(ITexture.TEXTURE_FOLDER + textures[i]));
                final TextureData data = TextureIO.newTextureData(gl.getGLProfile(), stream, false, "jpg");
                gl.glTexImage2D(GL3.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL3.GL_RGB, data.getWidth(),
                        data.getHeight(), 0, GL3.GL_RGB, GL3.GL_UNSIGNED_BYTE, data.getBuffer());

            } catch (GLException | IOException e) {
                System.out.println("Failed to load Texture: " + textures[i]);
            }
        }

        gl.glTexParameteri(GL3.GL_TEXTURE_CUBE_MAP, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_CUBE_MAP, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_CUBE_MAP, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL3.GL_TEXTURE_CUBE_MAP, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL3.GL_TEXTURE_CUBE_MAP, GL3.GL_TEXTURE_WRAP_R, GL3.GL_CLAMP_TO_EDGE);

        return true;
    }
}
