package plants;

//冷冻菇
public class IceShroom extends Plant {
	private int frontTime;

	public IceShroom(int h, int l, int x, int y, int timer) {
		super(h, l, x, y, 200);
		// TODO Auto-generated constructor stub
		frontTime=timer;
	}
	
	 public void setFrontTime(int time) {
			frontTime=time;
	   }
	 
	public int getFrontTime() {
		return frontTime;
	}

}
