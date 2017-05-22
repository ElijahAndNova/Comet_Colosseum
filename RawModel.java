import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.awt.*;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.GL11;

class RawModel{

    private int vaoID;
    private int vertexCount;

    public RawModel(int vaoID, int vertexCount){
	this.vaoID = vaoID;
	this.vertexCount = vertexCount; 
    }

    public int getVertexCount(){
	return vertexCount;
    }

    public int getVaoID(){
	return vaoID;
    }

}