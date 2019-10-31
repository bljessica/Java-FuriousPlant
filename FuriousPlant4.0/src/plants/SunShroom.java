package plants;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

//太阳菇
public class SunShroom extends Plant{
    private int frontTime;
	private ArrayList<Integer> sun = new ArrayList<>();
	private int growUp;
	private static Image child =new ImageIcon("Images/Plants/sunShroom0.gif").getImage();
	private static Image adult =new ImageIcon("Images/Plants/sunShroom.gif").getImage();
	
	public SunShroom(int h, int l, int x, int y, int timer) {
		super(h, l, x, y, 100);
		frontTime = timer;
		growUp=0;
	}
	
	public Image getImage() {
		if (growUp>2) return adult;
		else return child;
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
		growUp++;
	}
	
}
