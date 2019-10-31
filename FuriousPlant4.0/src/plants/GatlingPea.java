package plants;
import java.util.ArrayList;

//枪管射手
public class GatlingPea extends Plant {

    private int atk;              //对僵尸的攻击力
    private int frontTime;
    private ArrayList<Pea> peas=new ArrayList<>();

	public GatlingPea(int h,int l,int x, int y,int timer) {
		super(h, l, x, y,100);
		atk=25;
		frontTime=timer;
	}

	public ArrayList<Integer> getBX() {
		ArrayList<Integer> tmp = new ArrayList<>();
		for (Pea o: peas) tmp.add(o.getX());
		return tmp;
	}
	
	public void removePea() {
		peas.remove(0);
	}
	
	public void addPea() {
		peas.add(new Pea(x+10));
	}
	
	public int getBY() {
		return y-45;
	}
	
	public int getAtk() {
		return atk;
	}

	public int getFrontTime() {
		return frontTime;
	}

	public void setFrontTime(int frontTime) {
		this.frontTime = frontTime;
	}
	 
}
