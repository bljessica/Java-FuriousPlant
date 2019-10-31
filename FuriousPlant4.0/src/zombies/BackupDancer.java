package zombies;
import java.awt.Image;

import javax.swing.ImageIcon;

//伴舞僵尸
public class BackupDancer extends Zombie{
	
	private static Image move=new ImageIcon("Images/Zombies/backupDancer.gif").getImage();
	private static Image attack=new ImageIcon("Images/Zombies/backupDancerAttack.gif").getImage();
	private static Image iced=new ImageIcon("Images/Zombies/icedBackupDancer.png").getImage();

	public BackupDancer(int x) {
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