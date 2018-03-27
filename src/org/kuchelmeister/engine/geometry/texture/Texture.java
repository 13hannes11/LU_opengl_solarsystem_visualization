package org.kuchelmeister.engine.geometry.texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class Texture implements ITexture {
    private final String texturePath;
    private com.jogamp.opengl.util.texture.Texture texture;

    public Texture(final String texture) {
        this.texturePath = ITexture.TEXTURE_FOLDER + texture;
    }

    @Override
    public void bind(final GL3 gl) {
        // TODO Auto-generated method stub
        texture.enable(gl);
        texture.bind(gl);
    }

    @Override
    public boolean loadFromFile(final GL3 gl) {
        try {
            final InputStream stream = new FileInputStream(new File(texturePath));
            final TextureData data = TextureIO.newTextureData(gl.getGLProfile(), stream, false, "jpg");
            texture = TextureIO.newTexture(data);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, GL3.GL_REPEAT);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, GL3.GL_REPEAT);
            return true;
        } catch (GLException | IOException e) {
            System.out.println("Failed to load Texture: " + texturePath);
            return false;
        }
    }

}
