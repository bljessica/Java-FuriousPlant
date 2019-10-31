package plants;

import java.awt.Image;

import javax.swing.ImageIcon;

//土豆地雷
public class PotatoMine extends Plant {
	private int atk;              //对僵尸的攻击力
	private int frontTime;
	private boolean growUp;
	private boolean explode;
	private static Image adult=new ImageIcon("Images/Plants/potatoMine.gif").getImage();
	private static Image child=new ImageIcon("Images/Plants/potatoMineNotReady.gif").getImage();
	private static Image bomb=new ImageIcon("Images/Plants/potatoMineExplode.png").getImage();
	
	
	public PotatoMine(int h, int l, int x, int y, int timer) {
		super(h, l, x, y, 100);
		atk = 1000;
		frontTime = timer;
		growUp=false;
		setExplode(false);
	}

	public Image getImage() {
		if (explode) return bomb;
		if (growUp) return adult;
		else return child;
	}
	
	public boolean ifGrowUp(int timer) {
		if (timer-frontTime>150)growUp=true;
		else growUp=false;
		return growUp;
	}
	
	@Override
	public int getL() {
		return super.getL();
	}

	public int getAtk() {
		return atk;
	}

	public int getFrontTime() {
		return frontTime;
	}
	public void setFrontTime(int timer) {
		frontTime=timer;
	}

	public boolean isExplode() {
		return explode;
	}

	public void setExplode(boolean explode) {
		this.explode = explode;
	}


}