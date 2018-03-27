package org.kuchelmeister.engine.geometry.texture;

import com.jogamp.opengl.GL3;

public interface ITexture {
    public static final String TEXTURE_FOLDER = "textures/";

    public void bind(GL3 gl);

    public boolean loadFromFile(GL3 gl);
}
