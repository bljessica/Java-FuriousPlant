package plants;

import java.util.ArrayList;

//太阳花
public class SunFlower extends Plant {
	private int frontTime;
    private ArrayList<Integer> sun=new ArrayList<>();

	public SunFlower(int h, int l,int x,int y,int timer) {
		super(h, l, x, y,100);
		frontTime=timer;
	}
	
	public ArrayList<Integer> getSunX() {
		return sun;
	}
	
	public void addSun(int randNum) {
		sun.add(x-20+randNum);
	}
	
	public void decSun(int id) {
		sun.remove(id);
	}
	
	public int getFrontTime() {
		return frontTime;
	}
	
	public void setFrontTime(int time) {
		frontTime=time;	
	}
	
}

