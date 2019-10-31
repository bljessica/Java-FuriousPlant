package zombies;
import java.awt.Image;

import javax.swing.ImageIcon;

//普通僵尸
public class OrdinaryZombie extends Zombie{
	
	private static Image move=new ImageIcon("Images/Zombies/ordinaryZombie.gif").getImage();
	private static Image attack =new ImageIcon("Images/Zombies/ordinaryZombieAttack.gif").getImage();
	private static Image iced=new ImageIcon("Images/Zombies/icedZombie.png").getImage();

	public OrdinaryZombie(int x) {
		super(x);
		curHP=100;
		this.setType(1);
		this.setCard(new ImageIcon("Images/Zombies/ordi.png").getImage());
	}
	
    public Image getMove() {
        return move;
	}
    
    public Image getAttack() {
		return attack;
	}
    public Image getIced() {
		return iced;
	}
}