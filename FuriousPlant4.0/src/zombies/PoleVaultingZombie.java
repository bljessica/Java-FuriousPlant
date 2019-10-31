package zombies;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PoleVaultingZombie extends Zombie {
	private boolean jumpflag = false; 
	private static Image PVZombieattack = new ImageIcon("Images/Zombies/PVattack.gif").getImage();
	private static Image PVZombiejump1 = new ImageIcon("Images/Zombies/PVjump1.gif").getImage();
	private static Image PVZombiejump2 = new ImageIcon("Images/Zombies/PVjump2.gif").getImage();
	private static Image PVZombierun = new ImageIcon("Images/Zombies/PVrun.gif").getImage();
	private static Image PVZombiewalk = new ImageIcon("Images/Zombies/PVwalk.gif").getImage();
	private static Image icedrun = new ImageIcon("Images/Zombies/icedPVrun.gif").getImage();
	private static Image icedwalk = new ImageIcon("Images/Zombies/icedPVwalk.gif").getImage();
	
	public PoleVaultingZombie(int x) {
		super(x);
		this.setType(8);
		this.setCard(new ImageIcon("Images/Zombies/pole.png").getImage());
	}
	public boolean isJumpflag() {
		return jumpflag;
	}
	public void setJumpflag(boolean jumpflag) {
		this.jumpflag = jumpflag;
	}
	
	public Image getMove() {
		if(jumpflag) return PVZombiewalk;
		else return PVZombierun;
	}
	
	public Image getAttack() {
		return PVZombieattack;
	}
	
	public Image getIced() {
		if(jumpflag) return icedwalk;
		else return icedrun;
	}

}
