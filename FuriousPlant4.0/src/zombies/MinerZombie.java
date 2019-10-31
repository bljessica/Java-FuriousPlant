package zombies;

import javax.swing.ImageIcon;

//挖矿僵尸
public class MinerZombie extends Zombie {

	public MinerZombie(int x) {
		super(x);
		this.setType(2);
		this.setCard(new ImageIcon("Images/Zombies/little.png").getImage());
	}

}
