package plants;

//豌豆
public class Pea {
   private int x;
   
   public Pea(int x) {
	   this.x=x;
   }
   
   public int xx() {
	   return x;
   }
	public int getXf() {
		x-=3;
		return x;
	}
   
	public int getX() {
		x+=3;
		return x;
	}
}
