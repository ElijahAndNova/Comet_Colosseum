import java.util.TimerTask;
import java.util.Timer;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import org.lwjgl.util.vector.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.*;

public class Player extends Entity{

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;

    private static final float GRAVITY = -5;
    private static final float JUMP_POWER = 10;

    private static final float PLAYER_START_X = 0;
    private static final float PLAYER_START_Z = -35;
    
    private static final float TERRAIN_HEIGHT = 0; 

    private static float QUADRANT = 1;

    Vector3f playerFacing = new Vector3f(0,1,0);
    Vector3f enemyFacing = new Vector3f(0,-1,0);

    private static float startRotation = 0;
    private static float endRotation = 0;

    private float rotation_to_x_axis = 90;
    private float rotation_to_z_axis = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0; 

    private float DX = 0;
    private float DZ = 0;
    private float AX = 0;
    private float AZ = 0;

    private float angle = 270;

    private Vector3f position;
    private Vector3f enemyPosition;

    private Timer timer = new Timer();
    private static int moving = 0;
    private static int turning = 0;

    private static Enemy enemy;

    private static boolean onGround = true;
    
    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){
	super(model, position, rotX, rotY, rotZ, scale);
	this.position = position;
	resetRotation();
	jump();
    }

    public void setEnemy(Enemy enemy){
	this.enemy = enemy;
    }

    public void move(){
	checkInputs();
	upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
	//float theta = angle;
	//System.out.println(theta);
	//angle += 10f * DisplayManager.getFrameTimeSeconds();
	//if (angle >= 360){
	//    angle = 0;
	//}
	//	super.increaseRotation(0,theta,0);
	//float radius = calculateDistanceToEnemy();
	//float xChange = (float) (DisplayManager.getFrameTimeSeconds()*PLAYER_START_X + Math.cos(theta)*radius / 100000) / 10;
	//float zChange = (float) (DisplayManager.getFrameTimeSeconds()*PLAYER_START_Z + Math.sin(theta)*radius / 100000) / 10;
	//super.increasePosition(xChange,0,zChange);
	//super.increasePosition(xChange,upwardsSpeed * DisplayManager.getFrameTimeSeconds(),zChange);
	if (super.getPosition().y < TERRAIN_HEIGHT){
	    onGround = true;
	    upwardsSpeed = 0;
	    super.getPosition().y = TERRAIN_HEIGHT;
	}
	jump();
	//System.out.println(moving);
	moveDirection();
	resetRotation();
    }
    
    public void moveDirection(){
	if (moving == 1){
	    /*if (startRotation < endRotation){
                super.increaseRotation(0,.1f,0);
                startRotation = super.getRotY();
	    }
	    else if (startRotation > endRotation){
                super.increaseRotation(0,-.1f,0);
                startRotation = super.getRotY();
		}*/
	    //System.out.println("Moving forward");
	    super.increasePosition(-DX/50,0,-DZ/50);
	} else if (moving == -1){
	    //System.out.println("Moving backwards");
	    /*if (startRotation < endRotation){
                super.increaseRotation(0,.1f,0);
                startRotation = super.getRotY();
	    }
	    else if (startRotation > endRotation){
                super.increaseRotation(0,-.1f,0);
                startRotation = super.getRotY();
		}*/
	    super.increasePosition(DX/50,0,DZ/50);
        } else if (moving == -2){
	    //System.out.println("Moving left");
	    //super.increaseRotation(0, -30f/49f, 0);
	    //super.increasePosition(-AX/40,0, -AZ/40);
	    //super.increasePosition(-(float)Math.abs(AX-super.getPosition().x)/20, 0, -(float)Math.abs(AZ - super.getPosition().z)/40);
	} else if (moving == 2){
	    if (startRotation < endRotation){
		super.increaseRotation(0,.75f,0);
		startRotation = super.getRotY();
		//super.increasePosition(AX,0,AZ);
	    }
	    //System.out.println("Moving right");
	    //super.increaseRotation(0, 30f/48f, 0);
	    super.increasePosition(AX/48,0, AZ/48);
	    //super.increasePosition((AX)/400, 0, (AZ)/400);
	    //super.increasePosition((float)-Math.abs(AX-super.getPosition().x)/40, 0, (float)-Math.abs(AZ - super.getPosition().z)/40);
	    
	} 
	else if (moving == 3){
	    float theta = angle;
	    angle += .0001f;
	    if (angle >= 360){
		angle = 0;
	    }
	    float radius = calculateDistanceToEnemy();
	    float xChange = (float) (PLAYER_START_X + Math.cos(theta)*radius) / 10000 ; 
	    float zChange = (float) (PLAYER_START_Z + Math.sin(theta)*radius) / 10000 ;
	    super.increasePosition(xChange,0,zChange);
	}
	//else {
	    //System.out.println("Not moving");
	//}

    }

    private void jump(){
	this.upwardsSpeed = JUMP_POWER;
    }

    boolean released = false;
    float angle2;
    
    public static void setMoving(int movingVal){
	moving = movingVal;
    }

    private void resetRotation(){
	float distance = 10f;
        DX = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        DZ = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
    }

    private float calculateDistanceToEnemy(){
	float pX = super.getPosition().x;
	float pZ = super.getPosition().z;
	float eX = enemy.getPosition().x;
	float eZ = enemy.getPosition().z;
	float dX = pX - eX;
	float dZ = pZ - eZ;
	float radius = (float) Math.sqrt(dX*dX + dZ*dZ);
	return radius;
    }
    
    private float lawOfCosines(float radius){
	return (float) Math.sqrt(radius*radius + radius*radius - (2*radius*radius*Math.cos(Math.toRadians(30)))); //Hypotenuse of leg triangle                                                 
    }

    private float getAngle(){
	//float angle = (float) (Math.toDegrees(Math.atan2(enemy.getPosition().z - super.getPosition().z, enemy.getPosition().x - super.getPosition().x))-270);
	//if (angle < 0){
	//    angle += 360;
	//}
	float lookAtDiffX = enemy.getPosition().x - super.getPosition().x;
	float lookAtDiffY = enemy.getPosition().y - super.getPosition().y;
	float lookAtDiffZ = enemy.getPosition().z - super.getPosition().z;
	System.out.println(lookAtDiffX +" "+ lookAtDiffY +" "  + lookAtDiffZ);
	float angle = (float) (Math.atan2((lookAtDiffY), Math.sqrt(lookAtDiffX*lookAtDiffX + lookAtDiffZ*lookAtDiffZ)));
	
	return angle;
    }

    private void calculateAngleToEnemy(){
	float distance = calculateDistanceToEnemy();
	float px = super.getPosition().x;
	float pz = super.getPosition().z;
	float ex = enemy.getPosition().x;
	float ez = enemy.getPosition().z;
	float dx = (float) px - ex;
	float dz = (float) pz - ez;
	//float theta = (float) Math.toDegrees(Math.asin(dz / distance))-90;
	//float theta = Vector3f.angle(new Vector3f(super.getRotX(), super.getRotY(), super.getRotZ()), new Vector3f(0,180,0));
	//Vector3f normalizedPlayerVector = new Vector3f();
        //System.out.println("Y rotation = " + super.getRotY());
	
	//playerFacing.normalise(playerFacing);
        playerFacing.x = (float) ((360/super.getRotX())*.08333);
        playerFacing.y = (float) ((360/super.getRotY())*.08333);
	playerFacing.z = (float) ((360/super.getRotZ())*.08333);
	//System.out.println("playerFacing.x = " + playerFacing.x + "playerFacing.y = " + playerFacing.y + " playerFacing.z = " + playerFacing.z);
	float playerVectorLength = (float) (Math.sqrt(playerFacing.x*playerFacing.x + playerFacing.y*playerFacing.y + playerFacing.z*playerFacing.z));
	float enemyVectorLength = (float) (Math.sqrt(enemyFacing.x*enemyFacing.x + enemyFacing.y*enemyFacing.y + enemyFacing.z*enemyFacing.z));
	float theta = (float) Math.toDegrees(Math.acos(Vector3f.dot(playerFacing,enemyFacing)/(playerVectorLength*enemyVectorLength)));
	//float theta = angleVector.angle(new Vector3f(0,0,0), new Vector3f(0,0,0));
	startRotation = super.getRotY();
	endRotation = super.getRotY() + theta;
	//super.increaseRotation(0,theta,0);
	//System.out.println("Theta = " + theta);
    }
    
    private void setDestination(float radius){
	System.out.println("Calculated radius = " + radius);
	//Sets global variables AX and AY to be the amount of change required in X and Z to reach the destination. Then in the moveDirection method, it steps towards those coordinates
	//float angleBetweenPlayers = Vector3f.angle(super.getRotation(), enemy.getRotation());
	//if (moving == -2) rotation_to_x_axis = super.getRotY() + 90 + 30;
	//else if (moving == 2) rotation_to_x_axis = super.getRotY() + 90 - 30;
	if (super.getRotY() == 360 || super.getRotY() == -360){
	    super.setRotY(0);
	    endRotation = 0;
	}
	rotation_to_x_axis = 90 - super.getRotY();
	rotation_to_z_axis = 180 - super.getRotY();
	if (endRotation >= 0 && endRotation <= 90) QUADRANT = 1;
	else if (endRotation > 90 && endRotation <= 180) QUADRANT = 2;
	else if (endRotation > 180 && endRotation <= 270) QUADRANT = 3;
        else if (endRotation > 270 && endRotation <= 360) QUADRANT = 4;
	//System.out.println("Rotational angle from x axis was: " + rotation_to_x_axis + " degrees");
	//System.out.println("Rotational angle from z axis was: " + rotation_to_z_axis + " degrees");
	float startX = super.getPosition().x;
	float startZ = super.getPosition().z;
	float theta1 = 0;
	//	float distance = (float) Math.sqrt(radius*radius + radius*radius - (2*radius*radius*Math.cos(Math.toRadians(30)))); //Hypotenuse of leg triangle
	float distance = lawOfCosines(radius);
	//System.out.println("Hypotenuse is = " + distance);
	//System.out.println("Currently in quadrant " + QUADRANT);
	if (QUADRANT == 1) theta1 = 180 - 75f - rotation_to_x_axis;
	else if (QUADRANT == 2) theta1 = 180 - 75f - rotation_to_z_axis;
        else if (QUADRANT == 3) theta1 = 180 - 75f - (180-Math.abs(rotation_to_x_axis));	
	else if (QUADRANT == 4) theta1 = 180 - 75f - (180-Math.abs(rotation_to_z_axis));
	float theta2 = 180 - 90 - theta1;
	//System.out.println("Theta1 = " + theta1 + "  Theta2 = " + theta2);
	float xLeg = (float) Math.abs(distance * Math.cos((theta1))); //X coordinate destination
	float zLeg = (float) Math.abs(distance * Math.cos((theta2))); //Z coordinate destination
	if (QUADRANT == 1){ 
	    AX = xLeg;
	    AZ = -zLeg;
	}
	else if (QUADRANT == 2){
	    AX = -xLeg;
	    AZ = -zLeg;
	}
	else if(QUADRANT == 3){
            AX = -xLeg;
            AZ = zLeg;
        }
	else if(QUADRANT == 4){
            AX = xLeg;
            AZ = zLeg;
	}
	//System.out.println("Moving " + AX + " in x direction and " + AZ + " in z direction");
	//System.out.println("Starting position is (" + startX + "," + startZ + ")  |  xLeg is " + xLeg + "  |  zLeg is " + zLeg + "  |  Distances to these destinations are (" + AX + "," + AZ + ")");
	//System.out.println("AX,AZ = " + AX + "," + AZ);
	//System.out.println();
    }
    
    /*public void checkInputs(){
	if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
	    
	}
	}*/

    public void checkInputs(){
	while(Keyboard.next()) {
	    if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
		if (Keyboard.getEventKeyState() && onGround == true) {
		    setDestination(calculateDistanceToEnemy());
		    //calculateAngleToEnemy();
		    //System.out.println(getAngle());
		    onGround = false;
		    jump();
		    float distance = 10f;
                    float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
                    float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		    moving = 1;
		    timer.schedule(new TimerTask(){
			    public void run(){
				DX = dx;
				DZ = dz;
				moving = 0;
			    }			
			}, 400);
		}
	    } else if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                if (Keyboard.getEventKeyState() && onGround == true) {
		    //calculateAngleToEnemy();
		    System.out.println(getAngle());
		    onGround = false;
		    jump();
		    float distance = 10f;
                    float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
                    float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		    moving = -1;
                    timer.schedule(new TimerTask(){
                            public void run(){
                                DX = dx;
                                DZ = dz;
                                moving = 0;
                            }
                        }, 400);
		}
            } else if(Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                if (Keyboard.getEventKeyState() && onGround == true) {
		    //angle = calculateAngleToEnemy();
		    //float xoffset = (float) Math.cos(angle) * calculateDistanceToEnemy();
		    //float zoffset = (float) Math.sin(angle) * calculateDistanceToEnemy();
                    //super.increasePosition(xoffset/10,0,zoffset/10);
                    onGround = false;		    
		    jump();
		    //float distance = calculateDistanceToEnemy();
		    //float dx = (float)(distance * Math.sin(Math.toRadians(super.getRotY())));                                                                                                                        
		    //float dz = (float)(distance * Math.cos(Math.toRadians(super.getRotY())));                                                                                                                     
                    //super.increasePosition(-dx, 0, -dz);                                          
		
		    moving = -2;
		    

		    //calculateNextArcpoint();
		    //System.out.println(calculateDistanceToEnemy());
		    setDestination(calculateDistanceToEnemy());
                    //super.increasePosition(-AX,0,-AZ);
		    //super.increaseRotation(0, -30f, 0);
		    timer.schedule(new TimerTask(){
                            public void run(){
                                moving = 0;
                            }
                        }, 400);
		    super.increaseRotation(0, -30f, 0);
                    resetRotation();
		
		}
            } else if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                
		
	    if (Keyboard.getEventKeyState() && onGround == true) {
		//System.out.println(lawOfCosines(35));
                    //angle = calculateAngleToEnemy();
                    //float xoffset = (float) Math.cos(angle) * calculateDistanceToEnemy();
                    //float zoffset = (float) Math.sin(angle) * calculateDistanceToEnemy();
                    //super.increasePosition(-xoffset/10,0,zoffset/10);
		    onGround = false;
		    jump();		    
		    //float distance = calculateDistanceToEnemy();
                    //float dx = (float)(distance * Math.sin(Math.toRadians(super.getRotY())));                                                                                                          
                    //float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));                                                                                                         
                    //super.increasePosition(-dx, 0, -dz);
		    
                    //super.increasePosition(AX,0,AZ);
		    //super.increaseRotation(0, 30f, 0);
                    //System.out.println(calculateDistanceToEnemy());
		    startRotation = super.getRotY();
		    endRotation = super.getRotY()+30;
		    setDestination(calculateDistanceToEnemy());
		    moving = 3;
		    //setDestination(35);
		    timer.schedule(new TimerTask(){
                            public void run(){
                                moving = 0;
                            }
                        }, 400);
		    //super.increaseRotation(0, 30f, 0);
		    //super.increasePosition(AX,0,AZ);

		    }

		jump();
		float theta = angle;
		angle += .0001f;
		if (angle >= 360){
		    angle = 0;
		}
		float radius = calculateDistanceToEnemy();
		float xChange = (float) (PLAYER_START_X + Math.cos(theta)*radius) / 10000 ;
		float zChange = (float) (PLAYER_START_Z + Math.sin(theta)*radius) / 10000 ;
		super.increasePosition(xChange,0,zChange);
            }

	    moving = 0;


	}
    }
    
}