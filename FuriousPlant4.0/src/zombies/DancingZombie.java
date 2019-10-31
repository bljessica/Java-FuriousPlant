package zombies;

import java.awt.Image;

import javax.swing.ImageIcon;

public class DancingZombie extends Zombie{
	private int frontDanceTime;
	private static Image iced =new ImageIcon("Images/Zombies/iceddancingZombieWalk.png").getImage();

	public DancingZombie(int x,int timer) {
		super(x);
		this.frontDanceTime = timer;
		this.setType(5);
		this.setCard(new ImageIcon("Images/Zombies/dance.png").getImage());
	}
	
	public void setFrontDanceTime(int frontDanceTime) {
		this.frontDanceTime = frontDanceTime;
	}

	public int getFrontDanceTime() {
		return this.frontDanceTime;
	}
	public Image getIced() {
		return iced;
	}
	
}
