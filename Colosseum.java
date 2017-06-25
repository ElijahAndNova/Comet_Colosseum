import java.util.Random;
import java.util.*;
import org.lwjgl.util.vector.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.awt.*;
import org.lwjgl.opengl.GL11;

class Colosseum{

    public Vector3f playerPosition = new Vector3f(0,0,-35);
    public Vector3f enemyPosition = new Vector3f(0,0,-70);
    public Vector3f cubeSatPosition = new Vector3f(0,20,-50);
    public Vector3f playerPosition2 = new Vector3f(0,0,-35);

    public Colosseum(){
    
	DisplayManager.createDisplay();

	Loader loader = new Loader();
	
	RawModel model = OBJLoader.loadObjModel("Human", loader);  
	//ModelData data = OBJFileLoader.loadOBJ("Human");
	//RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());	
	ModelTexture texture = new ModelTexture(loader.loadTexture("Solid_Colors/White"));	    
	TexturedModel texturedModel = new TexturedModel(model, texture);             
	texture.setShineDamper(10);
	texture.setReflectivity(1f);

	RawModel model2 = OBJLoader.loadObjModel("CubeSat", loader);
	//ModelData model2 = OBJFileLoader.loadOBJ("Scuttler");	
	ModelTexture texture2 = new ModelTexture(loader.loadTexture("Solid_Colors/Blue"));
        TexturedModel texturedModel2 = new TexturedModel(model2, texture2);
        texture2.setShineDamper(10);
        texture2.setReflectivity(1f);

        //Player player = new Player(texturedModel, playerPosition, 0, 0, 0, 1);
        Player player = new Player(texturedModel, playerPosition2, 0, 0, 0, 2.5f);	
	//Enemy enemy = new Enemy(texturedModel2, enemyPosition, 0, 180f, 0, 3);
	Enemy enemy = new Enemy(texturedModel2, cubeSatPosition, 0, 300f, 0, 1.5f);
	player.setEnemy(enemy);
	
	RawModel model3 = OBJLoader.loadObjModel("fern", loader);
	//ModelData model3 = OBJFileLoader.loadOBJ("fern");
	ModelTexture texture3 = new ModelTexture(loader.loadTexture("fern"));
        TexturedModel texturedModel3 = new TexturedModel(model3, texture3);
        texture3.setShineDamper(10);
        texture3.setReflectivity(1f);
	texture3.setHasTransparency(true);
	texture3.setFakeLighting(true);
	Entity fern = new Entity(texturedModel3, new Vector3f(0,0,0), 0f, 0f, 0f, 1f);

	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	//for (float i = 0; i < 3; i++){
	//    Entity entity = new Entity(texturedModel, new Vector3f(i*10-10,-4,-20), 0, i*120, 0, 1);
	//    entities.add(entity);

	//}

	Random random = new Random();
	for (int i = 0; i < 50; i++){
	    float x = random.nextFloat() * 100 - 50;
	    float y = random.nextFloat() * 100 - 50;
	    float z = random.nextFloat() * -300;
	    entities.add(new Entity(texturedModel2, new Vector3f(x,y,z), random.nextFloat() * 180f, random.nextFloat()*180f, 0f, 1.5f));
	}
	for (int i = 0; i < 50; i++){
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;
            entities.add(new Entity(texturedModel2, new Vector3f(x,y,z), random.nextFloat() * 180f, random.nextFloat()*180f, 0f, 1.5f));
	    }
	
	//entities.add(new Entity(texturedModel, new Vector3f(-15f,0f,-20), 0f, 270f, 0f, 1.5f));                                                                    
	//entities.add(new Entity(texturedModel2, new Vector3f(15f,1.5f,-20), 0f, 90f, 0f, 4f));                                                                    
	//entities.add(new Entity(texturedModel3, new Vector3f(0,0,0), 0f, 0f, 0f, 1f));
	

	//Entity entity = new Entity(texturedModel, new Vector3f(0,-4,-20), 0, 0, 0, 1);

	Light light = new Light(new Vector3f(50,100,-40), new Vector3f(1f,1f,1f));

	Camera camera = new Camera(player);     

	Terrain terrain = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("crater2")));
	Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("crater2")));
	Terrain terrain3 = new Terrain(-1, 0, loader, new ModelTexture(loader.loadTexture("crater2")));
        Terrain terrain4 = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("crater2")));

	float offset = 0;
	

	MasterRenderer renderer = new MasterRenderer();
	camera.move();

	boolean up = true;

	while(!Display.isCloseRequested()){
	    
	    enemy.increaseRotation(0,DisplayManager.getFrameTimeSeconds() * 10f,0);
	    if (enemy.getPosition().y > 22) up = false;
	    else if (enemy.getPosition().y < 18) up = true;

	    if (up) enemy.increasePosition(0,1f * DisplayManager.getFrameTimeSeconds(), 0);
	    else if (!up) enemy.increasePosition(0,-1f * DisplayManager.getFrameTimeSeconds(),0);
	    
	    //update every frame
	    for (Entity entity:entities){
		entity.increaseRotation(0, .15f, 0f);	    
		//entity.increasePosition(0f, 0f, -.0025f);		
	    }
	  	    
  
	    //render
	    camera.move();
	    player.move();
	    renderer.processEntity(player);
	    renderer.processEntity(enemy);
	    renderer.processEntity(fern);
	    
	    for (Entity entity:entities){
		//if (entity.getPosition().z <= Renderer.getFar() && entity.getPosition().z >= Renderer.getNear()){
		renderer.processEntity(entity);
	    }
	    renderer.processTerrain(terrain);
	    renderer.processTerrain(terrain2); 
	    renderer.processTerrain(terrain3);
            renderer.processTerrain(terrain4);


	    renderer.render(light, camera);
	    DisplayManager.updateDisplay();
	}
	
	renderer.cleanUp();
	loader.cleanUp();
	DisplayManager.closeDisplay();
    }

}