package zombies;
import java.awt.Image;

import javax.swing.ImageIcon;

//铁桶僵尸
public class BucketheadZombie extends Zombie{

	private static Image hat=new ImageIcon("Images/Zombies/bucketheadZombie.gif").getImage();
	private static Image hatAttack=new ImageIcon("Images/Zombies/bucketheadZombieAttack.gif").getImage();
	private static Image ordinary=new ImageIcon("Images/Zombies/ordinaryZombie.gif").getImage();
	private static Image ordinaryAttack= new ImageIcon("Images/Zombies/ordinaryZombieAttack.gif").getImage();
	private static Image iced=new ImageIcon("Images/Zombies/icedBucketheadZombie.png").getImage();
	
	public BucketheadZombie(int x) {
		super(x);
		curHP=250;
		this.setType(4);
		this.setCard(new ImageIcon("Images/Zombies/bucket.png").getImage());
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
