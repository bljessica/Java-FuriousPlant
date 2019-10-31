package plants;

//大喷菇
public class FumeShroom extends Plant {

	private int atk;
	private int frontTime;
	
	public FumeShroom(int h, int l, int x, int y, int timer) {
		super(h, l, x, y, 100);
		this.atk = 25;
		this.frontTime = timer;
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
