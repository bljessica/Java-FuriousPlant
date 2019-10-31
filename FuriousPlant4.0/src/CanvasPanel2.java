import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import plants.*;
import zombies.*;

//阳光泳池
public class CanvasPanel2 extends JPanel implements Runnable,MouseListener,MouseMotionListener{
	private Image sun;
	private Image sunFlower;
	private Image peaShooter;
	private Image wallNut;
	private Image bullet;
	private Image spikeRock;
	private Image jalapeno;
	private Image lilyPad;
	private Image splitPea;
	private Image SeaShroom;
	private Image lawnCleaner;
	private Image mouse;
	private Image shovel;
	private Image back;
	private Image flagZombie;
	private Image flagZombieAttack;
	private Image ordinaryZombie;
	private Image ordinaryZombieAttack;
	private Image duckyZombie1;
	private Image duckyZombie2;
	private Image duckyZombieAttack;
	private Image seashroombullet;
	private Image PVZombieattack;
	private Image PVZombiejump1;
	private Image PVZombiejump2;
	private Image PVZombierun;
	private Image PVZombiewalk;
	private Image minerdown;
	private Image minermove1;
	private Image minermove2;
	private Image minerattack;
	private Image stringzombie;
	private Image fail;
	private Image won;
	
	private Thread thread;
	
	private PlaySound BGM = new PlaySound("Audios/UraniwaNi.mp3",true);
	
	private static Random random = new Random();
	private boolean win = false;
	private boolean lose = false;
	private boolean play = true;
	private int kindOfMouse;
	private int[][] f= new int[5][9];  //判断格子内是否有植物，如有则为true，否则为false
	private int[] gx = {92,177,258,339,417,497,581,660,740};
	private int[] gy = {99,189,285,397,477};
	
	private int[] gx2 = {0,92,177,258,339,417,497,581,660};
	
	private Zombie[][] zombies=new Zombie[5][15];
	private ArrayList<Plant> plants=new ArrayList<Plant>();
	private int[] numOfZombies=new int[5];
	private int[] numOfZombiesForA = new int[5];
	private int[] carX=new int[5];
	private int timer=0;
	private String money="100";
	private int myMoney=100;
	private int[] cost= {0,50,100,150,25,0,50,250,50}; 
	       //null,sunflower,peashooter,splitPea,lilypad,seashroom,jalapeno,spikerock,wallnut;
	
	int location_x, location_y;
	int startX = 750;
	
	private JFrame jf;
	
	public CanvasPanel2(JFrame jf) {
	      super();
	      this.jf = jf;
	      win = false;
	      lose = false;
	      mouse = null;
	      play = true;
	      for (int i=0;i<5;i++) {
	    	  carX[i]=-1;  //初始化小车
	    	  for (int j=0;j<9;j++) f[i][j]=-1; //初始化没有植物
	      }
	      loadImage();
	      loadZombie();
	      
	      if (thread == null || !thread.isAlive())
	      thread = new Thread(this);
	      thread.start();
	      BGM.play();
	   }
	
	 //加载图片
	private void loadImage() {
		sun = new ImageIcon("Images/Plants/sun.png").getImage();
		sunFlower = new ImageIcon("Images/Plants/sunFlower.gif").getImage();
		peaShooter = new ImageIcon("Images/Plants/peaShooter.gif").getImage();
		bullet = new ImageIcon("Images/Plants/bullet.png").getImage();
		spikeRock = new ImageIcon("Images/Plants/spikerock.gif").getImage();
		lilyPad = new ImageIcon("Images/Plants/LilyPad.gif").getImage();
		splitPea = new ImageIcon("Images/Plants/splitPea.gif").getImage();
		wallNut = new ImageIcon("Images/plants/wallNut.gif").getImage();
	    SeaShroom = new ImageIcon("Images/Plants/SeaShroom.gif").getImage();
	    seashroombullet = new ImageIcon("Images/Plants/seashroombullet.png").getImage();
	    jalapeno = new ImageIcon("Images/Plants/jalapeno.gif").getImage();
	    
	    shovel = new ImageIcon("Images/UI/shovel.png").getImage();
		lawnCleaner = new ImageIcon("Images/UI/lawnCleaner.png").getImage();
		back = new ImageIcon("Images/UI/background7w.gif").getImage();
		won = new ImageIcon("Images/UI/trophy.png").getImage();
		fail = new ImageIcon("Images/UI/zombiesWon.png").getImage();
		
		flagZombie = new ImageIcon("Images/Zombies/flagZombie.gif").getImage();
		flagZombieAttack=new ImageIcon("Images/Zombies/flagZombieAttack.gif").getImage();
		ordinaryZombie = new ImageIcon("Images/Zombies/ordinaryZombie.gif").getImage();
		ordinaryZombieAttack=new ImageIcon("Images/Zombies/ordinaryZombieAttack.gif").getImage();
		duckyZombie1 = new ImageIcon("Images/Zombies/duckyZombie1.gif").getImage();
		duckyZombie2 = new ImageIcon("Images/Zombies/duckyZombie2.gif").getImage();
		duckyZombieAttack = new ImageIcon("Images/Zombies/duckyZombieAttack.gif").getImage();
		PVZombieattack = new ImageIcon("Images/Zombies/PVattack.gif").getImage();
		PVZombiejump1 = new ImageIcon("Images/Zombies/PVjump1.gif").getImage();
		PVZombiejump2 = new ImageIcon("Images/Zombies/PVjump2.gif").getImage();
		PVZombierun = new ImageIcon("Images/Zombies/PVrun.gif").getImage();
		PVZombiewalk = new ImageIcon("Images/Zombies/PVwalk.gif").getImage();
		minerdown = new ImageIcon("Images/Zombies/minerdown.gif").getImage();
		minermove1 = new ImageIcon("Images/Zombies/minermove1.gif").getImage();
		minerattack = new ImageIcon("Images/Zombies/minerattack.gif").getImage();
		minermove2 = new ImageIcon("Images/Zombies/minermove2.gif").getImage();
		stringzombie = new ImageIcon("Images/Zombies/stringzombie.png").getImage();
	}
	
	//加载僵尸
	private void loadZombie() {
		if(MenuPanel.fight == false) {
		   int randNum,front;
		   randNum=random.nextInt(5);
		   if(randNum==2) {
			   zombies[1][0]=new FlagZombie(startX+random.nextInt(400));//第一波
			   numOfZombies[1]++;
		   }
		   else {
			   zombies[randNum][0]=new MinerZombie(startX+random.nextInt(400));
			   numOfZombies[randNum]++;
			   zombies[randNum][1]=new StringZombie(gx[2]);
			   numOfZombies[randNum]++;
		   }
		   for (int i=0;i<5;i++) {
			   front=1200+random.nextInt(400);
			   if(i==2) {
				   zombies[i][numOfZombies[i]]=new DuckyZombie(front);
			       numOfZombies[i]++;
			   }
			   else {
			       zombies[i][numOfZombies[i]]=new OrdinaryZombie(front);
			       numOfZombies[i]++;
			   }
		       randNum=random.nextInt(4);
		       for (int j=0;j<randNum;j++) {
				   if(i==2) {
			    	   front=1650+random.nextInt(200)+numOfZombies[i]*250;
			    	   zombies[i][numOfZombies[i]]= new DuckyZombie(front);
				       numOfZombies[i]++;
				   }
				   else {
					   front=1650+random.nextInt(200)+numOfZombies[i]*250;
			    	   zombies[i][numOfZombies[i]]= new PoleVaultingZombie(front);
				       numOfZombies[i]++;
				   }
		       }
		       front=3500;
		       randNum=random.nextInt(5)+2;
		       for (int j=0;j<randNum;j++) {
		    	   if(i!=2) {
		    	   front+=random.nextInt(100);
		    	   zombies[i][numOfZombies[i]]= new FlagZombie(front);//第二波
			       numOfZombies[i]++;
		    	   }
		    	   else {
		    		   front+=random.nextInt(100);
			    	   zombies[i][numOfZombies[i]]= new DuckyZombie(front);
				       numOfZombies[i]++;
		    	   }
		       }
		   }
		}
		else {
		   int sum = 0;
		   for(int i=0;i<5;i++) 
			   for(int j=0;j<15;j++) {
				   if(MenuPanel.setZombie[i][j]!=null) {
				       zombies[i][j] = MenuPanel.setZombie[i][j];
				       sum += MenuPanel.setZombie[i][j].getCardX();
				       numOfZombies[i]++;
				   }
				   else break;
			   }
		   myMoney = (sum/400)*10;
		   if(myMoney < 50) {
			   myMoney = 50;
		   }
		}
	}
	
	//使一个僵尸死亡
	private void destroyAZombie(int h) {
		for (int i=1; i<numOfZombies[h];i++)
			zombies[h][i-1]=zombies[h][i];
	    numOfZombies[h]--;
	    numOfZombiesForA[h]--;
	    zombies[h][numOfZombies[h]]=null;
	}
	
	protected void paintComponent(Graphics g) {//继承了JPanel 的类重写paintComponent;
	      super.paintComponent(g);
	      g.drawImage(back, 5, 10, this);
	      int leftOfZombie;
	      timer++;
	      
	      if(lose == false && win == false) {
	      for (Plant plant:plants) {
			  if (plant instanceof SunFlower) {  //太阳花
				  SunFlower s=(SunFlower) plant;
				  g.drawImage(sunFlower, s.getX()+5, s.getY()+10, this); 
				  
				  if (timer-s.getFrontTime()>200) {
					  ((SunFlower) plant).addSun(random.nextInt(60));
					  plant.setFrontTime(timer);
				  }
				  
				  for (Integer o:s.getSunX())
				  g.drawImage(sun, o, s.getY()+30, this);
			  }
			  else if(plant instanceof Jalapeno) { //火爆辣椒
				  Jalapeno j=(Jalapeno) plant;
				  g.drawImage(jalapeno, j.getX()+5, j.getY()+10, this);
			  }
			  else if(plant instanceof WallNut) {  //坚果
				  WallNut p=(WallNut) plant;
				g.drawImage(wallNut, p.getX()+5, p.getY()+10, this);
			  }
			  else if(plant instanceof SplitPea) {  //分裂射手
			  	SplitPea s=(SplitPea) plant;
			  	g.drawImage(splitPea, s.getX()+5, s.getY()+10, this);
		      }
			  else if (plant instanceof SeaShroom) {  //水蘑菇
				    SeaShroom p = (SeaShroom)plant;
					g.drawImage(SeaShroom, p.getX()+15, p.getY()-15, this);
	                int h = p.getH();
	                if(numOfZombiesForA[h]>0) {
	                	leftOfZombie = zombies[h][0].getX();
	                	if(timer - p.getFrontTime()>50) {
	                		plant.addPea();
	                		plant.setFrontTime(timer);
	                	}
	                	  for(int o:p.getBX()) {
	                		if(o-p.getX()>130) {
	                			p.removePea();
	                		}
	                		if(o>leftOfZombie) {
	                			new PlaySound("Audios/shovel.mp3",false).play();
	                			zombies[h][0].receiveDamage(p.getAtk());
	                			if(zombies[h][0].dead()) {
	                				destroyAZombie(h);
	                			}
	                			plant.removePea();
	                		}
	                		g.drawImage(seashroombullet, o+45, p.getBY()+80, this);
	                	  }
	                }
			  }
			  else if (plant instanceof LilyPad) {  //睡莲
				  LilyPad l=(LilyPad) plant;
				  g.drawImage(lilyPad, l.getX()+5, l.getY()+28, this);
			  }
			  else if (plant instanceof SpikeRock) {  //地刺
				  SpikeRock s=(SpikeRock) plant;
				  g.drawImage(spikeRock, s.getX()+5, s.getY()+10, this);
			  }
			  else if (plant instanceof PeaShooter) {   //豌豆射手
				  PeaShooter p= (PeaShooter) plant;
				  g.drawImage(peaShooter, p.getX()+5, p.getY()+10, this);
				  
				  ArrayList<Integer> bx=p.getBX();
				  int h=p.getH();
				  
				  if(numOfZombiesForA[h]>0 ) {   //发射豌豆
				     leftOfZombie=zombies[h][0].getX();
				     if( timer-p.getFrontTime()>50) {
				    	   plant.addPea();
				    	   plant.setFrontTime(timer);
				     }
				     
				       for (int o: bx) {
					        if (o>leftOfZombie) {
					        	new PlaySound("Audios/shovel.mp3",false).play();
						        zombies[h][0].receiveDamage(p.getAtk());
						        if (zombies[h][0].dead()) 
						        	destroyAZombie(h);
							
						        plant.removePea();
					        }
				        	g.drawImage(bullet,o+45,p.getBY()+50,this);
				     }
				  }
			   }
	      }
	      
	      Image move;
		  Image attack;
		  
		  boolean jumpflag=false;//撑杆跳僵尸跳跃标志
	      int y = 0;
	      boolean catchflag=false;
		  int x,endOfZombie;
		  int zombieSum = 0;
		  
	      for (int i=0; i<5; i++) {
	    	  numOfZombiesForA[i]=0;
	    	  if (carX[i]>=700) carX[i]=-100;
		      else if (carX[i]>=0) carX[i]+=7;
	    	  
	    	  g.drawImage(lawnCleaner, carX[i]+20, 120+i*90, this); //画小车
	 
	          for (int j=0;j<numOfZombies[i];j++) {
	        	  x=zombies[i][j].getX();
	        	  attack=null;
	        	  move=null;
	        	  if(x<=carX[i]-95) {
		        		if(carX[i]<0) carX[i]=0;  
		        		destroyAZombie(i);
		        		j--; 
		        		continue;	        		
		        	} 
		          if(x>=startX) {
		        	    zombies[i][j].setX(--x);
		        		continue;
		        	}
		          
	        	  numOfZombiesForA[i]++;
	              int right=findRight(i,zombies[i][j]);
	    	      if (right<0) 
	    	    	  endOfZombie=-100; //一直走到左侧尽头
	    	      else 
	    	    	  endOfZombie=gx[right];
	    	      if(zombies[i][j] instanceof MinerZombie) {
	    	    	  if(right<0) {
	    	    		  endOfZombie=740;
	    	    	  }
	    	    	  else{
	    	    		  endOfZombie = gx2[right];
	    	    	  }
	    	      }
	    	      if (zombies[i][j] instanceof FlagZombie) {
	        		  move=flagZombie;
		    	      attack=flagZombieAttack;
	        	   }
	    	      else if (zombies[i][j] instanceof OrdinaryZombie) {
	    	    	  move=ordinaryZombie;
		    	      attack=ordinaryZombieAttack;
	               }
	    	      else if (zombies[i][j] instanceof DuckyZombie) {
		    	      attack=duckyZombieAttack;
	               }
	    	      else if(zombies[i][j] instanceof PoleVaultingZombie) {
		    	      attack=PVZombieattack;
		    	      jumpflag = ((PoleVaultingZombie) zombies[i][j]).isJumpflag();
	               }
	    	      else if(zombies[i][j] instanceof MinerZombie) {
	    	    	  attack=minerattack;
	    	      }
	    	      else if(zombies[i][j] instanceof StringZombie) {
	    	    	  attack=stringzombie;
	    	    	  y=((StringZombie) zombies[i][j]).getY();
	    	    	  catchflag=((StringZombie) zombies[i][j]).isCatchflag();
	    	      }
	    	      if(move!=null) {
	    	    	  if (x<=endOfZombie) {  //僵尸到达end
	                  		g.drawImage(attack, x, 60+i*90, this); 
	                  		if(right>=0) {
	                  			int id=find(i, right);
	                  			if(id >= 0) {
	                  			    plants.get(id).receiveDamage(1);
	                  			}
	                  			if(plants.get(id).dead()) { 
	                  				f[plants.get(id).getH()][plants.get(id).getL()]=-1; 
	                  				plants.remove(id); 
	                  			}
	                  		}
	    	    	  }
	                  else {
	                  		g.drawImage(move, --x, 60+i*90, this);
	                  		zombies[i][j].setX(x);
	                  	}
	    	      }
	    	      else if(attack==duckyZombieAttack){
	    	    	  if(x<=endOfZombie) {
	                	  g.drawImage(duckyZombieAttack, x, 60+i*90, this); 
                	      if(right>=0) {
                	         int id=find(i, right);
                	         if(id>0)
                	             plants.get(id).receiveDamage(1);
                	         if(plants.get(id).dead()&&(plants.get(id) instanceof SeaShroom)) { 
                	        	f[plants.get(id).getH()][plants.get(id).getL()]=-1;  //吃了水蘑菇，置为-1没毛病
                	            plants.remove(id); 
                	            }
                	         else if(plants.get(id).dead()&&(plants.get(id) instanceof LilyPad)) { 
	                	        	f[plants.get(id).getH()][plants.get(id).getL()]=-1;  //吃了睡莲，置为-1没毛病
	                	            plants.remove(id); 
	                	        }
                	         else if(plants.get(id).dead()){
                	        	 f[plants.get(id).getH()][plants.get(id).getL()]=0;//吃了植物，置为0代表睡莲
                	        	 plants.remove(id);
                	         }
                	      }
            	      }
	    	    	  else if(x>730){
                    	  g.drawImage(duckyZombie1, x--, 60+i*90, this);
                    	  zombies[i][j].setX(x);
	    	    	  }
	    	    	  else if(x==730){
                    	  g.drawImage(duckyZombie2, x--, 60+i*90, this);
                    	  zombies[i][j].setX(x);
	    	    	  }
	    	    	  else {
                    	  g.drawImage(duckyZombie2, x--, 60+i*90, this);
                    	  zombies[i][j].setX(x);
                      }
	    	      }
	    	      else if(attack==PVZombieattack) {
	    	    	  if(jumpflag==true) { //跳过第一个植物，准备攻击第二个植物
	    	    		  if(x<=endOfZombie) {
	    	    			  g.drawImage(PVZombieattack, x-100, i*90, this);
	    	    			  if(right>=0) {
	    	    				  int id=find(i, right);
	    	    				  if(id>0)
	    	    				      plants.get(id).receiveDamage(1);
	    	    				  if(plants.get(id).dead()) { 
	    	    					  f[plants.get(id).getH()][plants.get(id).getL()]=-1; 
	    	    					  plants.remove(id); 
	    	    				  }
	    	    			  }
	    	    		  }
	    	    		  else {
	    	    			  g.drawImage(PVZombiewalk, x-150, i*90, this);
	    	    			  x--;
	    	    			  zombies[i][j].setX(x);
	    	    		  }
	    	    	  }
	    	    	  if(jumpflag==false) {  //到达最右边一个植物，开始跳跃
	    	    		  if(x<=endOfZombie) {
	    	    			  g.drawImage(PVZombiejump1, x, i*90, this);
	    	    			  x=x-78;
	    	    			  g.drawImage(PVZombiejump2, x, i*90, this);
	    	    			  zombies[i][j].setX(x);
	    	    			  ((PoleVaultingZombie) zombies[i][j]).setJumpflag(true);
	    	    		  }
	    	    		  else {
	    	    			  g.drawImage(PVZombierun, x--, i*90, this);
	    	    			  zombies[i][j].setX(x);
	    	    		  }	  
            	      }
	    	    	}
	    	      else if(attack==minerattack) {
	    	    	  if(x>730) {
	    	    		  g.drawImage(minermove1, x--, 85+i*90, this);
                    	  zombies[i][j].setX(x);
	    	    	  }
	    	    	  else if(x>=725&&x<=730) {  //没有显示出下降的效果
	    	    		  g.drawImage(minerdown, x, 85+i*90, this);
	    	    		  x=0;
                    	  zombies[i][j].setX(x);
	    	    	  }
	    	    	  else if(x>=endOfZombie) {
	    	    		  g.drawImage(attack, x, 85+i*90, this); 
	                  	  if(right>=0) {
	                  			int id=find(i, right);
	                  			if(id>0)
	                  			    plants.get(id).receiveDamage(1);
	                  			if(plants.get(id).dead()) { 
	                  				f[plants.get(id).getH()][plants.get(id).getL()]=-1; 
	                  				plants.remove(id); 
	                  			}
	                  		}
	    	    	  }
	    	    	  else {
	    	    		  g.drawImage(minermove2, x++, 85+i*90, this);
                    	  zombies[i][j].setX(x);
	    	    	  }
	    	      }
	    	      else if(attack==stringzombie) { //先默认生成在x=gx[2]的地方
	    	    	  g.drawImage(stringzombie,zombies[i][j].getX(),y,this);
	    	    	  if(y<=gy[1]) {
	    	    		  if(catchflag==false) {
	    	    			  y=y+7;
	    	    			  for(int k =0;k<5;k++) {
	    	    				  if(f[k][2]==1||f[k][2]==0) {
	    	    					  if(y>=(gy[k]-400)) {
	    	    						  int id = find(k,2);
	    	    						  if(plants.get(id) instanceof SeaShroom) { 
	    	    						  f[plants.get(id).getH()][plants.get(id).getL()]=-1;  //吃了水蘑菇，置为-1没毛病
	    	                	          plants.remove(id);
	    	                	          ((StringZombie) zombies[i][j]).setCatchflag(true);
	    	                	      	  }
	    	    						  else if(plants.get(id) instanceof LilyPad) { 
	    	                	    	  f[plants.get(id).getH()][plants.get(id).getL()]=-1;  //吃了睡莲，置为-1没毛病
	    	                	    	  plants.remove(id);
	    	                	    	  ((StringZombie) zombies[i][j]).setCatchflag(true);
	    		                	      }
	    	    						  else if(k==2){
	    	                	    	  f[plants.get(id).getH()][plants.get(id).getL()]=0;//吃了植物，置为0代表睡莲
	    	                	    	  plants.remove(id);
	    	                	    	  ((StringZombie) zombies[i][j]).setCatchflag(true);
	    	                	      	  }
	    	    						  else {
	    	                	    	  f[plants.get(id).getH()][plants.get(id).getL()]=-1;
	    	                	    	  plants.remove(id);
	    	                	    	  ((StringZombie) zombies[i][j]).setCatchflag(true);
	    	    						  } 
	    	    					  }
	    	    				  }
	    	    			  }
	    	    		  }
	    	    		  else {
	    	    			   y=y-10;
	    	    		  }
	    	    	  }
	    	    	  else{
	    	    		  ((StringZombie) zombies[i][j]).setCatchflag(true);
	    	    		  y=y-10;
	    	    	  }
	    	    	  ((StringZombie) zombies[i][j]).setY(y); 
	    	      }
	    	      zombies[i][j].setX(x); 
	    	      if(carX[i] == -100 && endOfZombie == zombies[i][0].getX() && endOfZombie==-50) {
		       	     lose = true;
		         }
	          }
	          zombieSum+=numOfZombies[i];
	      }	 
	      if(zombieSum == 0) {
			  win = true;
		  }
	      g.drawImage(mouse, location_x - 45, location_y - 55, 90, 100, null);
	      money=String.valueOf(myMoney);
	      g.drawChars(money.toCharArray(), 0, money.length(), 106, 95);
	      }
	      if(lose == true){
	    	  g.drawImage(fail, 200, 100, this);
	      }
	      if(win == true) {
	    	  g.drawImage(won, 350,250 , this);
	      }
	   }
	
	//关卡失败
	public void endGame() {
	     play = false;
	     BGM.stop();
	     thread.stop();
	     jf.dispose();
		 MenuFrame f = new MenuFrame();
		 f.setVisible(true);
	}

	//关卡通过
	 public void nextGame() {
		 play = false;
		 BGM.stop();
		 thread.stop();
		 jf.dispose();
		 MenuPanel.fight = false;
		 GameManager g = new GameManager(3,true);
		 g.setVisible(true);
	 }
	
	//是否在某区域
	private boolean inTheArea(int l,int r,int down,int up) {
		   if (location_x>=l && location_x<=r && location_y>=down && location_y<=up) 
			   return true;
		   else 
			   return false;
	}
	
	//点击生产的阳光 
	private boolean clickSun() {  
		for (Plant p:plants) 
			if(p instanceof SunFlower) {
				ArrayList<Integer> suns=((SunFlower) p).getSunX();
				int id=0;
				for (Integer x:suns) { 
					if(inTheArea(x+5, x+60, p.getY()+40, p.getY()+100)) {
						((SunFlower) p).decSun(id);
						myMoney+=50;
						return true;
					 }
					 id++;
				 }
			 }
		return false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	    location_x = e.getX();
	    location_y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	    location_x = e.getX();
	    location_y = e.getY();
	}
	
	//退出游戏
	private void exitGame() {
		   play = false;
		   thread.stop();
		   BGM.stop();
		   System.exit(0);
	   }

	@Override
	public void mouseClicked(MouseEvent e) {
		   if(lose==true) {
		       endGame();
	       }
		   if(win == true) {
			   nextGame();
		   }
		   if(inTheArea(818, 890, 23, 52)) {
			   exitGame();
		   }
		new PlaySound("Audios/buttonclick.mp3",false).play();
		if (inTheArea(148, 210, 15, 98)) {
			mouse = sunFlower; 
			kindOfMouse=1;
		}
		else if (inTheArea(223, 286, 15, 98)){
			mouse = peaShooter;
			kindOfMouse=2;
		}
		else if (inTheArea(294, 359, 15, 98)){
			mouse = splitPea;
			kindOfMouse=3;
		}
		else if (inTheArea(367, 432, 15, 98)){
			mouse = lilyPad;
			kindOfMouse=4;
		}
		else if (inTheArea(439, 502, 15, 98)){
			mouse = SeaShroom;
			kindOfMouse=5;
		}
		else if (inTheArea(513, 576, 15, 98)){
			mouse = jalapeno;
			kindOfMouse=6;
		}
		else if (inTheArea(587, 651, 15, 98)){
			mouse = spikeRock;
			kindOfMouse=7;
		}
		else if (inTheArea(660, 724, 15, 98)){
			mouse = wallNut;
			kindOfMouse=8;
		}
		else if (inTheArea(733, 798, 15, 98)) {
			mouse = shovel;
			kindOfMouse=9;
		}
		else if(!clickSun()) 
			for (int i=0;i<5;i++)
				for (int j=0; j<9;j++)
				    if(inTheArea(gx[j], gx[j]+70, gy[i], gy[i]+90))
				    	if(i!=2) {   //不是水池行,只能种太阳花，豌豆射手，分裂射手，火爆辣椒，地刺，坚果
				    		if (f[i][j]==-1 && kindOfMouse>=1 && kindOfMouse<=8 && myMoney>=cost[kindOfMouse]) {
				    		 
				    		if (kindOfMouse==1) {
				    			myMoney-=cost[1];
				    			plants.add(new SunFlower(i,j,gx[j],gy[i],timer));
				    			f[i][j]=1;
				    		} 
				    		else if (kindOfMouse==2) {			    			
				    			myMoney-=cost[2];
				    			plants.add(new PeaShooter(i,j,gx[j],gy[i],timer));
				    			plants.get(plants.size()-1).addPea();
				    			f[i][j]=1;
				    		} 
				    		else if (kindOfMouse==3) {			    			
				    			myMoney-=cost[3];
				    			plants.add(new SplitPea(i,j,gx[j],gy[i],timer));
				    			f[i][j]=1;
				    		}
					    	else if (kindOfMouse==6) {			    			
				    			myMoney-=cost[6];
				    			plants.add(new Jalapeno(i,j,gx[j],gy[i],timer));
				    			f[i][j]=1;
				    		}
				    		else if (kindOfMouse==7) {			    			
				    			myMoney-=cost[7];
				    			plants.add(new SpikeRock(i,j,gx[j],gy[i],timer));
				    			f[i][j]=1;
				    		}
				    	    else if (kindOfMouse==8) {			    			
			    				myMoney-=cost[8];
			    				plants.add(new WallNut(i, j, gx[j],gy[i],timer));
			    				f[i][j]=1;
			    		     }
				    		}
				    		else if (kindOfMouse==9 && f[i][j]==1) {
				    			plants.remove(find(i,j));
				    			mouse = shovel;
				    			f[i][j]=-1;
				    		}
				    	}
				    	else {   //选择了第三行，i=2
				    		if (f[i][j]==-1 && kindOfMouse>=4 && kindOfMouse<=5 && myMoney>=cost[kindOfMouse]) { //检测有没有睡莲，没有就只能种睡莲和水蘑菇
				    			if(kindOfMouse==5) {
				    				myMoney-=cost[5];
					    			plants.add(new SeaShroom(i,j,gx[j],gy[i],timer));
					    			f[i][j]=1;
				    			}
				    			if(kindOfMouse==4) {
				    			myMoney-=cost[4];
				    			plants.add(new LilyPad(i,j,gx[j],gy[i],timer));
				    			f[i][j]=0;
				    		    }
				    		}
				    		if(f[i][j]==0&& kindOfMouse>=1 && kindOfMouse<=8 && myMoney>=cost[kindOfMouse]) {
				    			if (kindOfMouse==1) {
					    			myMoney-=cost[1];
					    			plants.add(new SunFlower(i,j,gx[j],gy[i],timer));
					    			f[i][j]=1;
					    		} 
				    			else if (kindOfMouse==2) {			    			
					    			myMoney-=cost[2];
					    			plants.add(new PeaShooter(i,j,gx[j],gy[i],timer));
					    			plants.get(plants.size()-1).addPea();
					    			f[i][j]=1;
					    		} 
				    			else if (kindOfMouse==3) {			    			
					    			myMoney-=cost[3];
					    			plants.add(new SplitPea(i,j,gx[j],gy[i],timer));
					    			f[i][j]=1;
					    		}
						    	else if (kindOfMouse==6) {			    			
					    			myMoney-=cost[6];
					    			plants.add(new Jalapeno(i,j,gx[j],gy[i],timer));
					    			f[i][j]=1;
					    		}
					    	    else if (kindOfMouse==8) {			    			
				    				myMoney-=cost[8];
				    				plants.add(new WallNut(i,j,gx[j],gy[i],timer));
				    				f[i][j]=1;
				    		    }
				    		}
				    		else if (kindOfMouse==9 &&f[i][j]==1&&plants.get(find(i,j))instanceof SeaShroom == false) {
				    			plants.remove(find(i,j));
				    			mouse = shovel;
				    			f[i][j]=0;
				    		}
				    		else if (kindOfMouse==9 &&f[i][j]==1&&plants.get(find(i,j))instanceof SeaShroom == true) {
				    			plants.remove(find(i,j));
				    			mouse = shovel;
				    			f[i][j]=-1;
				    		}
				    		else if (kindOfMouse==9 && f[i][j]==0) {
				    			plants.remove(find(i,j));
				    			mouse = shovel;
				    			f[i][j]=-1;
				    		}
				    	}	
	}
	
	private int findRight(int h,Zombie z) {
		   int right=-1;
		   for (int j=0;j<9;j++) {
			   if(f[h][j]==1||f[h][j]==0) {
				   if(z instanceof MinerZombie&&z.getX()<=gx2[j]) {
					   right=j;
				   }
				   else if(z instanceof MinerZombie==false&&z.getX()>=gx[j]) {
					   right=j;
				   }
			   }
		   }
		   return right;
	   }
	   
	//找植物
	private int find(int h,int l) {
		   int n=0;
		   System.out.println(h);
		   if(h!=2) {
			 for(Plant p: plants) {
			   if(p.getH()==h && p.getL()==l) {
				   return n;
			   }
			   n++;
		     }
		   }
		   else {
			   int temp1=0;
			   int temp2=0;
			   boolean flag = false;
			   for(Plant p: plants) {
				   if(p.getH()==h && p.getL()==l) {
					   if(p instanceof LilyPad==false) {
						  flag = true;
						  temp1=n; 
					   }
					   else {
						   temp2 = n;
					   }
				   }
				   n++;
			    }
			   if(flag==false) {
				   return temp2;
			   }
			   else {
				   return temp1;
			   }
		   }

		   return -1;
	   }
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	    mouse = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void run() {
	      while (play) {
	         try {
	            Thread.sleep(100);
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	         repaint(); 
	         if(!jf.isDisplayable()) {  //此窗口未显示
	        	 BGM.stop();
	         }
	      }
	}

}
