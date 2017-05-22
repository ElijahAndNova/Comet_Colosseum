import java.util.Random;
import java.util.*;
import org.lwjgl.util.vector.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.awt.*;
import org.lwjgl.opengl.GL11;

class Colosseum{

    public Colosseum(){
    
	DisplayManager.createDisplay();

	Loader loader = new Loader();
	
	RawModel model = OBJLoader.loadObjModel("Human", loader);  
        ModelTexture texture = new ModelTexture(loader.loadTexture("Solid_Colors/White"));	    
	TexturedModel texturedModel = new TexturedModel(model, texture);             
	texture.setShineDamper(10);
	texture.setReflectivity(1f);

	RawModel model2 = OBJLoader.loadObjModel("Scuttler", loader);
        ModelTexture texture2 = new ModelTexture(loader.loadTexture("Solid_Colors/White"));
        TexturedModel texturedModel2 = new TexturedModel(model2, texture2);
        texture2.setShineDamper(10);
        texture2.setReflectivity(1f);
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	//for (float i = 0; i < 3; i++){
	//    Entity entity = new Entity(texturedModel, new Vector3f(i*10-10,-4,-20), 0, i*120, 0, 1);
	//    entities.add(entity);

	//}

	/*Random random = new Random();
	for (int i = 0; i < 100; i++){
	    float x = random.nextFloat() * 100 - 50;
	    float y = random.nextFloat() * 100 - 50;
	    float z = random.nextFloat() * -300;
	    entities.add(new Entity(texturedModel, new Vector3f(x,y,z), random.nextFloat() * 180f, random.nextFloat()*180f, 0f, 1f));
	}
	for (int i = 0; i < 100; i++){
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;
            entities.add(new Entity(texturedModel2, new Vector3f(x,y,z), random.nextFloat() * 180f, random.nextFloat()*180f, 0f, 3f));
	    }*/
	
	entities.add(new Entity(texturedModel, new Vector3f(-15f,0f,-20), 0f, 270f, 0f, 1.5f));                                                                    
	entities.add(new Entity(texturedModel2, new Vector3f(15f,1.5f,-20), 0f, 90f, 0f, 4f));                                                                    
	
	//Entity entity = new Entity(texturedModel, new Vector3f(0,-4,-20), 0, 0, 0, 1);

	Light light = new Light(new Vector3f(20,30,15), new Vector3f(1f,0f,0f));

	Camera camera = new Camera();     

	Terrain terrain = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("Solid_Colors/Gray")));
	Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("Solid_Colors/Gray")));
	Terrain terrain3 = new Terrain(-1, 0, loader, new ModelTexture(loader.loadTexture("Solid_Colors/Gray")));
        Terrain terrain4 = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("Solid_Colors/Gray")));

	float offset = 0;
	

	MasterRenderer renderer = new MasterRenderer();

	while(!Display.isCloseRequested()){

	    //update every frame
	    //for (Entity entity:entities){
		//entity.increaseRotation(0, .15f, 0f);	    
		//entity.increasePosition(0f, 0f, -.0025f);		
	    //}
	    
	    //render
	    camera.move();
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