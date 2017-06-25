import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.*;

public class Camera{
    
    private Vector3f position = new Vector3f(0,100,-75);
    //    private Vector3f position = new Vector3f(0, 40, 20);
    private float pitch = 30f;
    private float roll;
    private float yaw;

    private float distanceFromPlayer = 30;
    private float angleAroundPlayer = 180;

    private float spinSpeedPitch = 0;
    private float spinSpeedAngle = 0;

    private Player player;

    public Camera(Player player){
	this.player = player;
    }

    public void move(){
	calculateZoom();
	calculatePitch();
	calculateAngleAroundPlayer() ;
	float horizontalDistance = calculateHorizontalDistance();
	float verticalDistance = calculateVerticalDistance();
	calculateCameraPosition(horizontalDistance, verticalDistance);
	if (!Mouse.isButtonDown(0)){
	    //smoothResetCamera();
	}
    }

    private void smoothResetCamera(){
	spinSpeedPitch = Math.abs((pitch - 30)/50f);
	if (spinSpeedPitch < 0.25f) spinSpeedPitch = 0.25f;
	spinSpeedAngle = Math.abs((angleAroundPlayer - 180)/50f);
	if (spinSpeedAngle < 0.25f) spinSpeedAngle = 0.25f;
	if (pitch > 30.25) pitch -= spinSpeedPitch;
	else if (pitch < 30.25 && pitch > 30) pitch = 30;
	else if (pitch < 29.75) pitch += spinSpeedPitch;
	else if (pitch < 30 && pitch > 29.75) pitch = 30;
	if (angleAroundPlayer > 180.5) angleAroundPlayer -= spinSpeedAngle;
	else if (angleAroundPlayer > 180 && angleAroundPlayer < 180.25) angleAroundPlayer = 180; 
	else if (angleAroundPlayer < 179.5) angleAroundPlayer += spinSpeedAngle;
	else if (angleAroundPlayer < 180 && angleAroundPlayer > 179.75) angleAroundPlayer = 180;
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
	float theta = player.getRotY() + angleAroundPlayer;
	float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
	float offsetZ =(float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
	position.y = player.getPosition().y + verticalDistance + 10;
	position.x = player.getPosition().x - offsetX;
	this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
        position.z = player.getPosition().z - offsetZ;
    }

    private float calculateHorizontalDistance(){
	return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
	return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    public Vector3f getPosition(){
	return position;
    }
    
    public float getPitch(){
	return pitch;
    }
    
    public float getRoll(){
	return roll;
    }

    public float getYaw(){
	return yaw;
    }

    private void calculateZoom(){
	float zoomLevel = Mouse.getDWheel() * 0.01f;
	if (distanceFromPlayer - zoomLevel > 10 && distanceFromPlayer - zoomLevel < 75)
	    distanceFromPlayer -= zoomLevel;
    }
    
    private void calculatePitch(){
	if (Mouse.isButtonDown(0)){
	    float pitchChange = Mouse.getDY() * 0.25f;
	    if (pitch - pitchChange > -5 && pitch - pitchChange < 80)
		pitch -= pitchChange;
	}
    }

    private void calculateAngleAroundPlayer(){
	if (Mouse.isButtonDown(0)){
            float angleChange = Mouse.getDX() * 0.25f;
            angleAroundPlayer -= angleChange;
        }
    }

}