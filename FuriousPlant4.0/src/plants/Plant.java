package plants;
import java.awt.Image;

//植物类
public class Plant {
   protected int h;
   protected int l;
   protected int x;
   protected int y;
   protected int curHp;
   
   public Plant(int h,int l,int x,int y,int curHp) {
	   this.h=h; this.l=l;
	   this.x=x; this.y=y;
	   this.curHp=curHp;
   }
   
   public int getH() {
	  return h;
   }
   public int getL() {
	  return l;
   }
   public int getX() {
	  return x;
   }
   public int getY() {
	  return y;
   }
   
	public Image getImage() {
		return null;
	}
	
   public void removePea() {
   }
   
   public void addPea() {
   }
   
   public void receiveDamage(int damage){                 //受到僵尸的攻击
	   curHp-=damage;
   }
   
   public boolean dead() {
	   if (curHp<=0) return true;
	   else return false;
   }
   
   public void setFrontTime(int time) {
	
   }
	public void setPlant(int plant) {
	}
}
