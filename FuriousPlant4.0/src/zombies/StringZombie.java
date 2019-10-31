package zombies;

import javax.swing.ImageIcon;

public class StringZombie extends Zombie {
	private int y=-10;
	private int frontTime = 0;
	private boolean catchflag=false;
	private int px;
	private int py;
	private boolean show;
	
	public StringZombie(int x) {
		super(x);
		this.setType(7);
		this.setCard(new ImageIcon("Images/Zombies/stringLittle.png").getImage());
		show=false;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isCatchflag() {
		return catchflag;
	}
	public void setCatchflag(boolean catchflag) {
		this.catchflag = catchflag;
	}
	public int getFrontTime() {
		return frontTime;
	}
	public void setFrontTime(int frontTime) {
		this.frontTime = frontTime;
	}

	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public int getPh() {
		return px;
	}

	public int getPl() {
		return py;
	}
	public void setPhl(int px,int py) {
		this.px=px;
		this.py = py;
	}

}
