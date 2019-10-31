package zombies;
import java.awt.Image;

import javax.swing.ImageIcon;

//旗子僵尸
public class FlagZombie extends Zombie{
	
	private static Image move=new ImageIcon("Images/Zombies/flagZombie.gif").getImage();
	private static Image attack =new ImageIcon("Images/Zombies/flagZombieAttack.gif").getImage();
	private static Image iced=new ImageIcon("Images/Zombies/icedFlagZombie.png").getImage();

	public FlagZombie(int x) {
		super(x);
		curHP=100;
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