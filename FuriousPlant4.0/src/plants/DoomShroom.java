package plants;

//毁灭菇
public class DoomShroom extends Plant{
	private int frontTime;
	private int plantedTime;

	public DoomShroom(int h, int l,int x,int y,int timer) {
		super(h, l, x, y,100);
		frontTime=timer;
		plantedTime=timer;
	}
	
	public int getFrontTime() {
		return frontTime;
	}
	
	public void setFrontTime(int time) {
		frontTime=time;	
	}

	public int getPlantedTime() {
		return plantedTime;
	}
}
