package zombies;
import java.awt.Image;

import javax.swing.ImageIcon;

//路障僵尸
public class ConeheadZombie extends Zombie{
	
	private static Image hat=new ImageIcon("Images/Zombies/coneheadZombie.gif").getImage();
	private static Image hatAttack=new ImageIcon("Images/Zombies/coneheadZombieAttack.gif").getImage();
	private static Image ordinary=new ImageIcon("Images/Zombies/ordinaryZombie.gif").getImage();
	private static Image ordinaryAttack= new ImageIcon("Images/Zombies/ordinaryZombieAttack.gif").getImage();
	private static Image iced=new ImageIcon("Images/Zombies/icedConeheadZombie.png").getImage();
	
	public ConeheadZombie(int x) {
		super(x);
		curHP=200;
		this.setType(3);
		this.setCard(new ImageIcon("Images/Zombies/road.png").getImage());
	}
	
    public Image getMove() {
		if (curHP>100) return hat;
		else  return ordinary;
	}
    
    public Image getAttack() {
		if (curHP>100) return hatAttack;
		else  return ordinaryAttack;
	}
    public Image getIced() {
		return iced;
	}
}
