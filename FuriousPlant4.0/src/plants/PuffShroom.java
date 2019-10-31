package plants;
import java.util.ArrayList;

//小喷菇
public class PuffShroom extends Plant{
	
	private int atk;
	private int frontTime;
	private ArrayList<Pea> peas = new ArrayList<>();

	public PuffShroom(int h, int l, int x, int y, int timer) {
		super(h, l, x, y, 80);
		this.atk = 18;
		this.frontTime = timer;
	}
	
	public ArrayList<Integer> getBX(){
		ArrayList<Integer> tmp = new ArrayList<>();
		for(Pea o:peas) {
			tmp.add(o.getX());
		}
		return tmp;
	}
	
	public void removePea() {
		peas.remove(0);
	}
	
	public void addPea() {
		peas.add(new Pea(x+10));
	}
	
	public int getBY() {
		return this.y-45;
	}
	
	public int getAtk() {
		return this.atk;
	}

	public int getFrontTime() {
		return this.frontTime;
	}

	public void setFrontTime(int frontTime) {
		this.frontTime = frontTime;
	}

}
