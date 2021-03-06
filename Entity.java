import org.lwjgl.util.vector.*;

public class Entity{
    
    private TexturedModel model;
    private Vector3f position;
    public float rotX, rotY, rotZ;
    private float scale;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){
	this.model = model;
	this.position = position;
	this.rotX = rotX;
	this.rotY = rotY;
	this.rotZ = rotZ;
	this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz){
	this.position.x += dx;
	this.position.y += dy;
	this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz){
	this.rotX += dx;
	this.rotY += dy;
	this.rotZ += dz;
    }

    public void setModel(TexturedModel model){
	this.model = model;
    }

    public TexturedModel getModel(){
	return model;
    }

    public void setPosition(Vector3f position){
	this.position = position;
    }

    public Vector3f getPosition(){
	return position;
    }   
    
    public void setRotX(float rotX){
	this.rotX = rotX;
    }

    public float getRotX(){
	return rotX;
    }

    public void setRotY(float rotY){
	this.rotY = rotY;
    }

    public float getRotY(){
	return rotY;
    }

    public void setRotZ(float rotZ){
	this.rotZ = rotZ;
    }

    public float getRotZ(){
	return rotZ;
    }

    public void setScale(float scale){
	this.scale = scale;
    }

    public float getScale(){
	return scale;
    }

    public Vector3f getRotation(){
	return new Vector3f(rotX, rotY, rotZ);
    }




    //get and set the rest



}