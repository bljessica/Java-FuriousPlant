package plants;
import java.util.ArrayList;

//分裂射手
public class SplitPea extends Plant{
    private int atk;              //对僵尸的攻击力
	private int frontTime;
	private ArrayList<Pea> peas=new ArrayList<>();

	public SplitPea(int h,int l,int x, int y,int timer) {
		super(h, l, x, y,100);
		atk=25;
		frontTime=timer;
	}

	public ArrayList<Integer> getBX() {
		ArrayList<Integer> tmp = new ArrayList<>();
		for (Pea o: peas)
			if (o.xx()>x)tmp.add(o.getX());
			else tmp.add(o.getXf());
		return tmp;
	}
			
	public void removePea(int id) {
		peas.remove(id);
	}

	public void addPea() {
		peas.add(new Pea(x+20));
		peas.add(new Pea(x-50));
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
