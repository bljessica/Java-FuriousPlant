package plants;

//辣椒
public class Jalapeno extends Plant{

	private int atk;              //对僵尸的攻击力
	private int frontTime;
	private boolean fired;
	
	public Jalapeno(int h, int l, int x, int y, int timer) {
		super(h, l, x, y, 2000);
		atk = 1000;
		frontTime = timer;
	}
	public int getH(){
		return h;
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
	public boolean isFired() {
		return fired;
	}
	public void setFired(boolean fired) {
		this.fired = fired;
	}

}
