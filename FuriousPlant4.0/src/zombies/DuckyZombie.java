package zombies;

import javax.swing.ImageIcon;

//鸭子僵尸
public class DuckyZombie extends Zombie {

	public DuckyZombie(int x) {
		super(x);
		this.setType(6);
		this.setCard(new ImageIcon("Images/Zombies/duck.png").getImage());
	}

}
