
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import zombies.*;

public class MenuPanel extends JPanel implements MouseListener,MouseMotionListener {
	
	 int location_x, location_y;
	 
	 Image background;
	 Image mouse = null;
	 Image one = new ImageIcon("Images/UI/surface0.png").getImage();
	 Image two = new ImageIcon("Images/UI/surface1.png").getImage();
	 Image three =new ImageIcon("Images/UI/surface2.png").getImage();
	 Image four =new ImageIcon("Images/UI/surface3.png").getImage();
	 Image two2 = new ImageIcon("Images/UI/surface12.png").getImage();
	 Image three2 =new ImageIcon("Images/UI/surface22.png").getImage();
	 Image four2 =new ImageIcon("Images/UI/surface32.png").getImage();
	 Image setZ = new ImageIcon("Images/UI/zombieChoose.png").getImage();
	 Image z1 = new ImageIcon("Images/Zombies/ordi.png").getImage();
	 Image z2 = new ImageIcon("Images/Zombies/little.png").getImage();
	 Image z3 =new ImageIcon("Images/Zombies/road.png").getImage();
	 Image z4 =new ImageIcon("Images/Zombies/bucket.png").getImage();
	 Image z5 = new ImageIcon("Images/Zombies/dance.png").getImage();
	 Image z6 =new ImageIcon("Images/Zombies/duck.png").getImage();
	 Image z7 =new ImageIcon("Images/Zombies/fall.png").getImage();
	 Image z8 = new ImageIcon("Images/Zombies/pole.png").getImage();
	 
	 Zombie moveMouse = new OrdinaryZombie(0);
	 
	 private Image[] zom8 = {z1,z2,z3,z4,z5,z6,z7,z8}; //zombie1-8小图
	 int notMove=0;
	 String[] nums = new String[8];
	 private int[] gxUp = {58,132,155,230,250,327,354,430,455,530,560,635,659,735,758,832}; //对战版上排左上角
	 private int gy[] = {152,235,320,402,486,567}; //对战版y
	 
	 public static boolean fight = false;  //对战版
	 public static Zombie[][] setZombie = new Zombie[5][15];  //对战版
	 int[] numOfZombie = {0,0,0,0,0};  //每行僵尸数
	 private boolean canChoose[] = new boolean[8];  //每种僵尸是否还能选中

	 public MenuPanel() {
		 super();
		 background=one;
		 for(int i=0;i<8;i++) {
			 nums[i] = "10"; //每种僵尸最多选十种
			 canChoose[i] = true;
		 }
	}
	 
	 @Override
	 public void paint(Graphics g) {
		  super.paint(g);
		  g.drawImage(background, 5, 10, null);
		  g.drawImage(mouse,location_x-37 , location_y-50, this);
		  if(MenuPanel.fight == true) {
		      for(int i=0;i<8;i++) {
		          g.drawChars(nums[i].toCharArray(), 0, nums[i].length(), 85+i*100, 160);
		      }
		  }
		  for(int i=0;i<5;i++) {
			  int temp = 15-numOfZombie[i];
			  g.drawChars(Integer.toString(temp).toCharArray(), 0,Integer.toString(temp).length(), 857, 194+i*85);
			  for(int j=0;j<15;j++) {
			      if(setZombie[i][j]!=null) {
			          g.drawImage(setZombie[i][j].getCard(), setZombie[i][j].getCardX()-37, setZombie[i][j].getCardY()-15, this);
			      }
			  }
		  }
		  repaint();// gif在添加repaint()后才会动起来
	 }

	 @Override
	 public void mouseMoved(MouseEvent e) {
		  location_x = e.getX();
		  location_y = e.getY();
		  if (notMove ==0 && location_x>=497 && location_x<=800 && location_y>=98 && location_y<=192)
		      background=two;
		  else if (notMove == 0 && location_x>=489 && location_x<=782 && location_y>=213 && location_y<=315)
			  background=three;
		  else if (notMove ==0 && location_x>=490 && location_x<=748 && location_y>=325 && location_y<=415)
			  background=four;
		  else if(notMove==0 ) 
			  background=one;
	 }

	 @Override
	 public void mouseClicked(MouseEvent e) {
		  if (notMove==1 && fight == false && location_x>=60 && location_x<=300 && location_y>=170 && location_y<=331) {
	          new GameManager(1,false);
		  } 
		  else if (notMove==1 && location_x>=326 && location_x<=568 && location_y>=179 && location_y<=331) {
			  //对战模式
			  fight = true;
			  background = setZ;
			  mouse = null;
		  } 
		  else if (notMove==2 && fight == false && location_x>=60 && location_x<=300 && location_y>=170 && location_y<=331) {
			  new GameManager(2,false);
		  } 
		  else if (notMove==2 && location_x>=326 && location_x<=568 && location_y>=179 && location_y<=331) {
			  //对战模式
			  fight = true;
			  background = setZ;
			  mouse = null;
		  } 
		  if (notMove==3 && fight == false && location_x>=60 && location_x<=300 && location_y>=170 && location_y<=331) {
	          new GameManager(3,false);
		  } 
		  else if (notMove==3 && location_x>=326 && location_x<=568 && location_y>=179 && location_y<=331) {
			  //对战模式
			  fight = true;
			  background = setZ;
			  mouse = null;
		  } 
		  else if (fight == false && location_x>=498 && location_x<=802 && location_y>=97 && location_y<=199) {
			  background=two2;
			  notMove=1;
		  }
		  else if (fight == false && location_x>=488 && location_x<=779 && location_y>=219 && location_y<=315) {
			  background=three2;
			  notMove=2;
		  }
		  else if (fight == false && location_x>=492 && location_x<=745 && location_y>=318 && location_y<=421) {
			  background=four2;
			  notMove=3;
		  } 
		  if(MenuPanel.fight == true&&location_y>=50&&location_y<=150) {
    		 for(int i=0;i<16;i=i+2) {
    			 if(location_x>=gxUp[i]&&location_x<=gxUp[i+1] ) {
    				 if(canChoose[i/2]==true) {
    					 mouse = zom8[i/2];
    				 }
    				 else {
    					 mouse = null;
    				 }
    			 }
    		 }
	      }
		  int tx = location_x;
		  int ty = location_y;
		  if(MenuPanel.fight == true && location_y>150&&location_y<=568) {
			  moveZombie(tx,ty);
		  }
		  //排好僵尸进入游戏
		  if(fight == true&&location_x>=820 && location_x<=876 && location_y>=558 && location_y<=587) {
			  sort();
		      if(notMove == 1) {
		    	  new GameManager(1,false);
		      }
		      else if(notMove ==2) {
		    	  new GameManager(2,false);
		      }
		      else if(notMove ==3) {
		    	  new GameManager(3,false);
		      }
		  }
	 }

	 @Override
	 public void mouseEntered(MouseEvent e) {
	  
	 }

	 @Override
	 public void mouseExited(MouseEvent e) {
	  
	 }

	 @Override
	 public void mousePressed(MouseEvent e) {
		 
	 }
	 
	 @Override
	 public void mouseDragged(MouseEvent e) {
		 location_x = e.getX();
		 location_y = e.getY();
		 int tx = location_x;
		 int ty = location_y; 
		 if(MenuPanel.fight == true && location_y>150 && location_y<=568 && mouse == null){
    		 moveZombie(tx,ty);
    	 }  
	 }
	 
      //对战版布置僵尸时移动位置
	 public void moveZombie(int tx,int ty) {  
		 if(moveMouse == null) {
			 for(int i=0;i<5;i++) {
				 for(int j=0;j<numOfZombie[i];j++) {
					 if(setZombie[i][j]==null) break;
					 if(setZombie[i][j].getCardY()<ty && setZombie[i][j].getCardY()+80>ty
							 && setZombie[i][j].getCardX()>=tx-15 && setZombie[i][j].getCardX()<=tx+15) {
						 mouse = setZombie[i][j].getCard();
						 moveMouse = setZombie[i][j];
						 for(int k=j;k<numOfZombie[i]-1;k++) {
							 setZombie[i][k] = setZombie[i][k+1];
						 }
						 numOfZombie[i]--;
						 setZombie[i][numOfZombie[i]] = null;
						 break;
					 }
				 }
			 }
		 }
	 }
	 
	 //对战版安排僵尸时删除一个僵尸
	 public void deleteZombie() {  
		 moveMouse = null;
		 mouse = null;
	 }

	 //获取僵尸种类
	 public Zombie getKindOfZombie(int x) {  
		 for(int i=0;i<8;i++) {
			 if(mouse == zom8[i]) {
				 if(nums[i].equals("0")) {
					 canChoose[i] = false;
					 mouse = null;
					 return null;
				 }
				 if(canChoose[i]==true) {  //如果可以选
				     nums[i] = String.valueOf((Integer.parseInt(nums[i])-1));
					 if(mouse == zom8[0]) return new OrdinaryZombie(x*6+800);
					 if(mouse == zom8[1]) return new MinerZombie(x*6+800);
					 if(mouse == zom8[2]) return new ConeheadZombie(x*6+800);
					 if(mouse == zom8[3]) return new BucketheadZombie(x*6+800);
					 if(mouse == zom8[4]) return new DancingZombie(x*6+800,0);
					 if(mouse == zom8[5]) return new DuckyZombie(x*6+800);
					 if(mouse == zom8[6]) return new StringZombie(x*6+800);
					 if(mouse == zom8[7]) return new PoleVaultingZombie(x*6+800);
				 }
			 }
		 }
		 return null;	 
	 }
	 
	 //将僵尸加入数组
	 public void addZombie() {
		 if(MenuPanel.fight == true) {
			 for(int i=0;i<5;i++){
			     if(location_y>gy[i] && location_y<gy[i+1]) {
			    	 Zombie z = getKindOfZombie(location_x);
			    	 if(z!=null && numOfZombie[i]<15) {
				    	 setZombie[i][numOfZombie[i]] = z;
				    	 setZombie[i][numOfZombie[i]].setCardX(location_x);
			    		 setZombie[i][numOfZombie[i]].setCardY(gy[i]);
			    		 numOfZombie[i]++;
			    		 break;
			    	 }
			    	 else{
			    		 mouse = null;
			    		 break;
			    	 }
			     }
			 }
	     }
	 }
	 
	 //排序
	 public void sort() {
		 Zombie temp;
		 for(int i=0;i<5;i++) {
			 for(int j=0;j<numOfZombie[i]-1;j++) {
				 for(int k=0;k<numOfZombie[i]-1-j;k++) {
					 if(setZombie[i][k].getCardX()>setZombie[i][k+1].getCardX()) {
						 temp = setZombie[i][k+1];
						 setZombie[i][k+1] = setZombie[i][k];
						 setZombie[i][k] = temp;
					 }
				 }
			 }
		 }
	 }

	 @Override
	 public void mouseReleased(MouseEvent e) {
		 location_x = e.getX();
		 location_y = e.getY();
	     addZombie();
	     if(location_x>=10 && location_x<=94 && location_y>=564 && location_y<=589 && moveMouse!=null) {
	    	 deleteZombie();
	     }
	 }
}
