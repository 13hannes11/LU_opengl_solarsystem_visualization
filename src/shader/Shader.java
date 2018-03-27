package shader;

import com.jogamp.opengl.GL3;

import tools.ShaderHelper;

public class Shader {
    public static final String SHADER_FOLDER = "shaders/";

    private int fsId;
    private int vsId;
    private int programId;

    private final String fsPath;
    private final String vsPath;

    public Shader(final String vertexShaderName, final String fragmentShaderName) {
        this.vsPath = SHADER_FOLDER + vertexShaderName;
        this.fsPath = SHADER_FOLDER + fragmentShaderName;
    }

    public void compile(final GL3 gl) {
        vsId = ShaderHelper.createVertexShader(gl, vsPath);
        fsId = ShaderHelper.createFragmentShader(gl, fsPath);

        programId = ShaderHelper.createProgram(gl, vsId, fsId);
    }

    public int getProgramId() {
        return programId;
    }
}
