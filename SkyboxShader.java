import org.lwjgl.util.vector.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.awt.*;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL13;
import org.lwjgl.BufferUtils;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.nio.FloatBuffer;

import org.lwjgl.util.vector.Matrix4f;
 
public class SkyboxShader extends ShaderProgram{
 
    private static final String VERTEX_FILE = "skyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "skyboxFragmentShader.txt";
     
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColour;

    private static final float ROTATE_SPEED = 5f;
    //private static final float ROTATE_SPEED = -1f;
    private float rotation = 0;
     
    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }
 
    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
	matrix.m31 = 0;
	matrix.m32 = 0;
	rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
	Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0,1,0), matrix, matrix);
	super.loadMatrix(location_viewMatrix, matrix);
    }
     
    public void loadFogColour(float r, float g, float b){
	super.loadVector(location_fogColour, new Vector3f(r, g, b));
    }

    @Override
	protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
	location_fogColour = super.getUniformLocation("fogColour");
    }
 
    @Override
	protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
 
}