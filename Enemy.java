import org.lwjgl.util.vector.*;
import org.lwjgl.input.Keyboard;

public class Enemy extends Entity{

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;

    public Vector3f position;

    public Enemy(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){
	super(model, position, rotX, rotY, rotZ, scale);
	this.position = position;
    }

    public Vector3f getRotation(){
	return new Vector3f(rotX, rotY, rotZ);
    }

    public void move(){
	//checkInputs();
	super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
	float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
	float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
	float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
	super.increasePosition(dx, 0, dz);
    }

    public void checkInputs(){
	if (Keyboard.isKeyDown(Keyboard.KEY_W)){
	    this.currentSpeed = -RUN_SPEED;
	} else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.currentSpeed = RUN_SPEED;
        } else {
	    this.currentSpeed = 0;
	}

	if (Keyboard.isKeyDown(Keyboard.KEY_D)){
	    this.currentTurnSpeed = -TURN_SPEED;
	} else if (Keyboard.isKeyDown(Keyboard.KEY_A)){
	    this.currentTurnSpeed = TURN_SPEED;
	} else {
	    this.currentTurnSpeed = 0;
	}
    }

}