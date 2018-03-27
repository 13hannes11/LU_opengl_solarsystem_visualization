package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;

/**
 * Helper Class for loading and linking vertex and fragment shaders.
 * 
 * @author Björn Zimmer
 *
 */
public class ShaderHelper {

	public static String loadShaderFromFile(String fileName) {
		// allocate a string builder to add line per line
		StringBuilder strBuilder = new StringBuilder();

		try {
			InputStream stream = new FileInputStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			String line = reader.readLine();
			// get text from file, line per line
			while (line != null) {
				strBuilder.append(line + "\n");
				line = reader.readLine();
			}
			// close resources
			reader.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strBuilder.toString();
	}

	/**
	 * Loads and compiles a vertex shader from the given source file. Returns
	 * the shader ID.
	 * 
	 * @param gl
	 * @param fileName
	 * @return
	 */
	public static int createVertexShader(GL3 gl, String fileName) {
		return createShader(gl, fileName, GL3.GL_VERTEX_SHADER);
	}

	/**
	 * Loads and compiles a fragment shader from the given source file. Returns
	 * the shader ID.
	 * 
	 * @param gl
	 * @param fileName
	 * @return
	 */
	public static int createFragmentShader(GL3 gl, String fileName) {
		return createShader(gl, fileName, GL3.GL_FRAGMENT_SHADER);
	}

	/**
	 * 
	 * @param gl
	 * @param fileName
	 * @param shaderType
	 *            GL3.GL_VERTEX_SHADER or GL3.GL_FRAGMENT_SHADER
	 * @return
	 */
	private static int createShader(GL3 gl, String fileName, int shaderType) {
		// load the source
		String shaderSource = loadShaderFromFile(fileName);

		// create the shader id
		int shaderId = gl.glCreateShader(shaderType);
		// link the id and the source
		gl.glShaderSource(shaderId, 1, new String[] { shaderSource }, null);
		// compile the shader
		gl.glCompileShader(shaderId);

		IntBuffer shaderCompileInfo = Buffers.newDirectIntBuffer(1);
		// test if the shader was compiled correctly
		gl.glGetShaderiv(shaderId, GL3.GL_COMPILE_STATUS, shaderCompileInfo);

		System.out.println("Shader compile info:" + shaderCompileInfo);
		if (shaderCompileInfo.get() == GL3.GL_FALSE) {
			String logMessage = getProgramInfoLog(gl, shaderId);

			System.out.println("Shader compilation failed: " + logMessage);

			IntBuffer logLength = Buffers.newDirectIntBuffer(1);
			gl.glGetShaderiv(shaderId, GL3.GL_INFO_LOG_LENGTH, logLength);

			ByteBuffer log = Buffers.newDirectByteBuffer(logLength.get(0));
			gl.glGetShaderInfoLog(shaderId, log.limit(), logLength, log);
			final byte[] infoBytes = new byte[logLength.get(0)];
			log.get(infoBytes);
			System.out.println("Shader compilation failed:\n " + new String(infoBytes));
		}
		return shaderId;
	}

	/**
	 * Retrieves the info log for the program.
	 * 
	 * @param gl
	 * @param program
	 * @return
	 */
	public static String getProgramInfoLog(GL3 gl, int program) {
		// get the GL info log
		final int par[] = new int[1];
		gl.glGetProgramiv(program, GL3.GL_INFO_LOG_LENGTH, par, 0);
		final int logLen = par[0];
		// final int logLen = getProgramParameter(gl, obj,
		// GL3.GL_INFO_LOG_LENGTH);
		if (logLen <= 0)
			return "";

		// Get the log
		final int[] retLength = new int[1];
		final byte[] bytes = new byte[logLen + 1];
		gl.glGetProgramInfoLog(program, logLen, retLength, 0, bytes, 0);
		final String logMessage = new String(bytes);

		return logMessage;
	}

	/**
	 * Links a vertex- and fragmentshader to the programm.
	 * 
	 * @param gl
	 * @param vertexShaderId
	 * @param fragmentShaderId
	 * @return
	 */
	public static int createProgram(GL3 gl, int vertexShaderId, int fragmentShaderId) {
		// generate the id of the program
		int programId = gl.glCreateProgram();
		// attach the two shaders
		gl.glAttachShader(programId, vertexShaderId);
		gl.glAttachShader(programId, fragmentShaderId);
		// link them
		gl.glLinkProgram(programId);

		IntBuffer linkInfo = Buffers.newDirectIntBuffer(1);
		gl.glGetProgramiv(programId, GL3.GL_LINK_STATUS, linkInfo);
		System.out.println("Link info: " + linkInfo);
		if (linkInfo.get() == GL3.GL_FALSE) {
			String logMessage = getProgramInfoLog(gl, programId);
			System.out.println("Shader linking failed: " + logMessage);
		}

		return programId;
	}

}
