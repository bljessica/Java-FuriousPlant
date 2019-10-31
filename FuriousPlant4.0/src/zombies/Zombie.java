package zombies;
import java.awt.Image;

//僵尸类
public class Zombie {
    protected int x;
    private int type;
	private Image card;
	private int cardX;
	private int cardY;
    protected int curHP;
	
   public Zombie(int x) {
	   this.x=x;
	   curHP=100;
   }
   
   public int getX() {
	return x;
   }
   public void setX(int x) {
	this.x=x;
   }
   
   public int getCurHP() {
	   return this.curHP;
   }
   
   public Image getIced() {
	return null;
}
   
   
   public Image getMove() {
	   return null;
   }
   
   public Image getAttack() {
	   return null;
}

   public void receiveDamage(int damage) {//供植物调用，传入shoot_damage
	   curHP-=damage;
   }
   
   public boolean dead() {
	  if (curHP<=0) return true;
	  else return false;
   }

	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public Image getCard() {
		return card;
	}
	
	public void setCard(Image card) {
		this.card = card;
	}
	
	public int getCardX() {
		return cardX;
	}
	
	public void setCardX(int cardX) {
		this.cardX = cardX;
	}
	
	public int getCardY() {
		return cardY;
	}
	
	public void setCardY(int cardY) {
		this.cardY = cardY;
	}
	
}
