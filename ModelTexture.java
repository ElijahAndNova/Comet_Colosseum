public class ModelTexture{

    private int textureID;

    private float shineDamper = 1;
    private float reflectivity = 0; 

    private boolean hasTransparency = false;
    private boolean useFakeLighting = false;
    
    public boolean isHasTransparency(){
	return hasTransparency;
    }

    public void setHasTransparency(boolean transparent){
	hasTransparency = transparent;
    }

    public boolean getFakeLighting(){
        return useFakeLighting;
    }

    public void setFakeLighting(boolean fakeLighting){
        useFakeLighting = fakeLighting;
    }


    public ModelTexture(int id){
	this.textureID = id;
    }
    
    public int getID(){
	return textureID;
    }
    
    public float getShineDamper(){
        return shineDamper;
    }

    public float getReflectivity(){
	return reflectivity;
    }

    public void setShineDamper(float shineDamper){
	this.shineDamper = shineDamper;
    }

    public void setReflectivity(float reflectivity){
	this.reflectivity = reflectivity;
    }

}