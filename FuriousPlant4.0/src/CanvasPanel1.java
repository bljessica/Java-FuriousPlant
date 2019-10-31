
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import plants.*;
import zombies.*;

//阳光草地
public class CanvasPanel1 extends JPanel implements Runnable,MouseListener,MouseMotionListener{
   
   private Image potatoMine;
   private Image jalapeno;
   private Image jalapenoAttack;
   private Image boomDie;
   private Image spikeRock;
   private Image wallNut;
   private Image splitPea;
   private Image gatlingPea;
   private Image lawnCleaner;
   private Image sun;
   private Image bullet;
   private Image mouse;
   private Image shovel;
   private Image sunFlower;
   private Image peaShooter;
   private Image back;
   private Image dancingZombieDance;
   private Image dancingZombieWalk;
   private Image dancingZombieAttack;
   private Image fail;
   private Image won;
   private Thread thread;
   
   private PlaySound BGM = new PlaySound("Audios/kitanaiSekai.mp3",true);
   private PlaySound loseBGM = new PlaySound("Audios/losemusic.mp3",false);
   private PlaySound winBGM = new PlaySound("Audios/winmusic.mp3",false);

   private static Random random = new Random();
   
   private boolean win = false;
   private boolean lose = false;
   private boolean play = true;
   private int kindOfMouse;
   private int[][] f= new int[5][9];
   private int[] gx = {92,177,255,339,417,497,581,655,732};
   private int[] gy = {99,189,285,385,485};
   private Zombie[][] zombies=new Zombie[5][50];
   private Plant[] plants=new Plant[50];
   private int numOfPlants=0;
   private int[] numOfZombies=new int[5];
   private int[] numOfZombiesForA=new int[5];
   private int[] carX=new int[5];
   private int timer=0;
   private String money;
   private int myMoney;
   private int[] cost= {0,50,100,150,200,50,150,25,100};
             //null,sunflower,peashooter,splitPea,gatlingPea,wallnut,jalapeno,patatomine,spikerock;
   
   private int location_x, location_y;
   private int startX = 850;
   private boolean fired;
   
   private JFrame jf;
   
   public CanvasPanel1(JFrame jf) {
      super();
      this.jf = jf;
      win = false;
      lose = false;
      myMoney=100;
      fired=false;
      mouse = null;
      play = true;
      for (int i=0;i<5;i++) {
    	  carX[i]=-1;
    	  for (int j=0;j<9;j++) 
    		  f[i][j]=0;
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
	   sunFlower = new ImageIcon("Images/plants/sunFlower.gif").getImage();
	   peaShooter = new ImageIcon("Images/plants/peaShooter.gif").getImage();
	   bullet = new ImageIcon("Images/plants/bullet.png").getImage();
	   gatlingPea=new ImageIcon("Images/plants/gatlingPea.gif").getImage();
	   jalapeno=new ImageIcon("Images/plants/jalapeno.gif").getImage();
	   jalapenoAttack = new ImageIcon("Images/plants/jalapenoAttack.gif").getImage();
	   boomDie=new ImageIcon("Images/Zombies/boomDie.gif").getImage();
	   spikeRock=new ImageIcon("Images/plants/spikerock.gif").getImage();
	   wallNut = new ImageIcon("Images/plants/wallNut.gif").getImage();
	   splitPea = new ImageIcon("Images/plants/splitPea.gif").getImage();
	   sun = new ImageIcon("Images/plants/sun.png").getImage();	
	   potatoMine=new ImageIcon("Images/plants/potatoMine.gif").getImage();

	   won = new ImageIcon("Images/UI/trophy.png").getImage();
	   fail = new ImageIcon("Images/UI/zombiesWon.png").getImage();
	   lawnCleaner = new ImageIcon("Images/UI/lawnCleaner.png").getImage();
	   shovel =new ImageIcon("Images/UI/shovel.png").getImage();
	   back = new ImageIcon("Images/UI/background7d.png").getImage();
	   
	   dancingZombieDance = new ImageIcon("Images/Zombies/dancingZombieDance.gif").getImage();
	   dancingZombieAttack=new ImageIcon("Images/Zombies/dancingZombieAttack.gif").getImage();
	   dancingZombieWalk=new ImageIcon("Images/Zombies/dancingZombieWalk.gif").getImage();
   }
   
   //随机获取僵尸
	private Zombie getRandZombie(int x) {
		int randNum=random.nextInt(7);
		switch (randNum) {
			case 0:return new ConeheadZombie(x);
			case 1:return new ConeheadZombie(x);
			case 2:return new BucketheadZombie(x);
			case 3:return new DancingZombie(x,0);
			default:return new OrdinaryZombie(x);
		}
	}
	
	//加载僵尸
	private void loadZombie() {
		if(MenuPanel.fight == false) {
		   int randNum,front;
		   randNum=random.nextInt(5);
		   
		   zombies[randNum][0]=new FlagZombie(startX+random.nextInt(200));
		   numOfZombies[randNum]++;
		   
		   for (int i=0;i<5;i++) {
		       randNum=random.nextInt(4);
		       for (int j=0;j<randNum;j++) {
		    	   front=1500+random.nextInt(300)+j*300;
		    	   zombies[i][numOfZombies[i]]= getRandZombie(front);
			       numOfZombies[i]++;
		       }
		       for (int j=0;j<randNum;j++) {
		    	   front=3000+random.nextInt(200)+j*200;
		    	   zombies[i][numOfZombies[i]]= getRandZombie(front);
			       numOfZombies[i]++;
		       }
		       front=5000;
		       randNum=random.nextInt(5)+2;
		       for (int j=0;j<randNum;j++) {
		    	   front+=random.nextInt(150);
		    	   zombies[i][numOfZombies[i]]= getRandZombie(front);
			       numOfZombies[i]++;
		       }
		   }
		   randNum=random.nextInt(5);
		   addAZombie(randNum, 4900);
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
	
	private void addAZombie(int h,int x) {
		int index=0;  //所有格都比x大 或者 numOfZombies[h]==0 不进入循环
		
	    for(int i=numOfZombies[h]; i>0; i--) 
	    	if(zombies[h][i-1].getX()>x)
	            zombies[h][i] = zombies[h][i-1];
	    	else {
	    		index=i; 
	    		break;
	    	}  //第i格一直是空的
	        
	    if (x>=4800) zombies[h][index] = new FlagZombie(x);
	    else 
	    	zombies[h][index] = new BackupDancer(x);
	    numOfZombies[h]++;
	}
	
	//产生舞伴
	public void createDancers(int h ,int x) {
       // 左
        addAZombie(h, x-100);
       // 右
        addAZombie(h, x+100);
       //下
		if(h+1<5) 
        addAZombie(h+1, x);
	   //上
		if(h>0)   
		addAZombie(h-1, x);
	}
   
	//使一个僵尸死亡
	private void destroyAZombie(int h,int index) {
		index++;
		for(int i=index;i<numOfZombies[h];i++) {
			zombies[h][i-1] = zombies[h][i];
		}
		numOfZombies[h]--;
		zombies[h][numOfZombies[h]] = null;
	}
   
   @Override
   protected void paintComponent(Graphics g) {//继承了JPanel 的类重写paintComponent;
      super.paintComponent(g);
      g.drawImage(back, 5, 10, this);
      int leftOfZombie;
      timer++;
      
      if(lose == false &&win == false) { 
      for (int e=0;e<numOfPlants;e++) {
		  if (plants[e] instanceof SunFlower) {  //太阳花
			  SunFlower s=(SunFlower) plants[e];
			  g.drawImage(sunFlower, s.getX()+5, s.getY()+10, this); 
			  
			  if (timer-s.getFrontTime()>200) {
				  ((SunFlower) plants[e]).addSun(random.nextInt(60));
				  plants[e].setFrontTime(timer);
			  }
			  
			  for (Integer o:s.getSunX())
			  g.drawImage(sun, o, s.getY()+40, this);
		  }
		  else if (plants[e] instanceof PeaShooter) {   //豌豆射手
			  PeaShooter p= (PeaShooter) plants[e];
			  g.drawImage(peaShooter, p.getX()+5, p.getY()+10, this);
			  
			  ArrayList<Integer> bx=p.getBX();
			  int h=p.getH();
			  
			  if(numOfZombiesForA[h]>0 && numOfZombies[h]>0) {   //发射豌豆
			      leftOfZombie=zombies[h][0].getX();
			      if(timer-p.getFrontTime()>50) {
			    	   plants[e].addPea();
			    	   plants[e].setFrontTime(timer);
			      }
			     
			      for (int o: bx) {
			          if (o>leftOfZombie) {
				          zombies[h][0].receiveDamage(p.getAtk());
				          if (zombies[h][0].dead()) destroyAZombie(h,0);
				          plants[e].removePea();
			          }
			          g.drawImage(bullet,o+45,p.getBY()+50,this);
			      }
			  } 
		  }
		  else if (plants[e] instanceof SplitPea) {   //分裂射手
			  SplitPea p= (SplitPea) plants[e];
			  g.drawImage(splitPea, p.getX()+5, p.getY()+10, this);
			  
			  ArrayList<Integer> bx=p.getBX();
			  int h=p.getH();
			  
			  if(numOfZombiesForA[h]>0 ) {   //发射豌豆
			     leftOfZombie=zombies[h][0].getX();
			     if( timer-p.getFrontTime()>50) {
			    	   plants[e].addPea();
			    	   plants[e].setFrontTime(timer);
			     }
			     int od=0;
			     for (int o: bx) {
			    	if(o<0) ((SplitPea)plants[e]).removePea(od); else
			        if (o>leftOfZombie) {
				        zombies[h][0].receiveDamage(p.getAtk());
				        if (zombies[h][0].dead()) 
				        	destroyAZombie(h,0);
				        ((SplitPea)plants[e]).removePea(0);
			        }
			        g.drawImage(bullet,o+45,p.getBY()+54,this); od++;
			     }
			  }
		  }
		  else if (plants[e] instanceof GatlingPea) {  //枪管射手
			  GatlingPea w=(GatlingPea) plants[e];
			  g.drawImage(gatlingPea, w.getX()+5, w.getY()+10, this);
			  ArrayList<Integer> bx=w.getBX();
			  int h=w.getH();
			  
			  if(numOfZombiesForA[h]>0 && numOfZombies[h]>0) {   //发射豌豆
			     leftOfZombie=zombies[h][0].getX();//bug

			     if(timer-w.getFrontTime()==50) 
			    	 plants[e].addPea();
			     else if(timer-w.getFrontTime()==60) 
			    	 plants[e].addPea();
			     else if(timer-w.getFrontTime()==70) 
			    	 plants[e].addPea();
			     else if(timer-w.getFrontTime()>70) {
			    	   plants[e].setFrontTime(timer);
			     }
			     
			     for (int o: bx) {
			        if (o>leftOfZombie) {
				        zombies[h][0].receiveDamage(w.getAtk());
				        if (zombies[h][0].dead()) 
				        	destroyAZombie(h,0);
				        plants[e].removePea();
			        }
			        g.drawImage(bullet,o+45,w.getBY()+70,this);
			     }
			  }
		  }
		  else if (plants[e] instanceof WallNut) {  //坚果墙
			  WallNut w=(WallNut) plants[e];
			  g.drawImage(wallNut, w.getX()+15, w.getY()+20, this);
		  }
		  else if (plants[e] instanceof Jalapeno) {  //辣椒
			  Jalapeno j=(Jalapeno) plants[e];
			  if (timer-j.getFrontTime()<18) { //正常
				  g.drawImage(jalapeno, j.getX()-5, j.getY()-10, this);
			  } 
			  else if (timer-j.getFrontTime()<50) { //爆炸
				  g.drawImage(jalapenoAttack, 85, j.getY()-40, this);
				  ((Jalapeno) plants[e]).setFired(true);
			  }
			  else { //remove
				  removeAPlant(e);
				  e--;
				  fired = false;
				  break;
			  }
		      for(int i = 0;i<5;i++) {
		    	  if(j.isFired() && i==j.getH())
		    		  for (int k=0;k<numOfZombiesForA[i];k++) 
		    			  destroyAZombie(i, 0);
		      }
		  }
		  else if (plants[e] instanceof PotatoMine) {  //土豆地雷
			  PotatoMine p=(PotatoMine) plants[e];
			  if (p.ifGrowUp(timer)) 
				  f[p.getH()][p.getL()]=0;
			  if(p.isExplode()) {
				  g.drawImage(p.getImage(), p.getX()-18, p.getY()-30, this);
				  if (timer-p.getFrontTime()>50) {
					  removeAPlant(e);
					  e--;
				  }
			  }
			  else 
				  g.drawImage(p.getImage(), p.getX()+5, p.getY()+20, this);
			  int h=p.getH();
			  for (int j = 0; j < numOfZombies[h]; j++) {
			      if (f[p.getH()][p.getL()]==0 &&
			      (p.getX() - zombies[h][j].getX() >35) &&(p.getX() - zombies[h][j].getX() <39)) {
    	    	      ((PotatoMine) plants[e]).setExplode(true);
				      ((PotatoMine) plants[e]).setFrontTime(timer);
				      destroyAZombie(h, j);
				  }
				}
		  }
		  else if (plants[e] instanceof SpikeRock) {  //地刺
			  SpikeRock s=(SpikeRock) plants[e];
			  g.drawImage(spikeRock, s.getX()+5, s.getY()+40, this);
			  
		      int h=s.getH();
			  for (int j = 0; j < numOfZombies[h]; j++) {
				  if (s.getX()-zombies[h][j].getX()>30 && s.getX()-zombies[h][j].getX()<130)
					  zombies[h][j].receiveDamage(1);
							
				  if (zombies[h][j].dead()) {
					  destroyAZombie(h, j);
					  j--;
				  }
		      }
		  }
      }

        Image move;
		Image attack;
		boolean dance;
		int x,endOfZombie;
		int zombieSum = 0;
		
	    for (int i=0; i<5; i++) {  //每一行
	    	numOfZombiesForA[i]=0;  //i行出现的僵尸数
	    	if (carX[i]>=790) 
	    		carX[i]=-100;
	        else if (carX[i]>=0) 
	        	carX[i]+=7;
	    	g.drawImage(lawnCleaner, carX[i]+20, 120+i*94, this); 
	 
	        for (int j=0;j<numOfZombies[i];j++) {  //每一列
	        	x=zombies[i][j].getX();
	        	dance=false; 
	        	attack=null; 
	        	move=null;
	        	
	        	if(x<=carX[i]-30) { //小车
	        		if(carX[i]<0) 
	        			carX[i]=0;  
	        		destroyAZombie(i,0);
	        		j--; continue;	        		
	        	} 
	            if(x>=startX) {   //画僵尸
	        	    zombies[i][j].setX(--x);
	        		continue;
	        	}
	            
	        	if (x<770) numOfZombiesForA[i]++;  //进入屏幕
	        	
	            int right=findRight(i,x);   //当前一个僵尸面前植物的右边
	    	    if (right<0) 
	    	    	endOfZombie=-50; 
	    	    else 
	    	    	endOfZombie=gx[right];	          

	    	    if (zombies[i][j] instanceof DancingZombie) {   //舞王僵尸选择图片
	            	if (x>endOfZombie) {  //僵尸未到end
                        DancingZombie d = (DancingZombie)zombies[i][j];
		    	        if(timer - d.getFrontDanceTime()<150) 
		    	        	move = dancingZombieWalk; 
		    	        else if(timer - d.getFrontDanceTime()<300) {
		    	        	move = dancingZombieDance;
		    	        	dance=true;
		    	        }
		    	        else if(timer - d.getFrontDanceTime()>=300) {
		    	        	move = dancingZombieDance;
		    	        	createDancers(i, x);
		    	        	d.setFrontDanceTime(timer);	
		    	        	dance=true;
		    	        }
	            	}
	            	attack=dancingZombieAttack;  
	    	    } 
	    	    else {
	    	        if(fired) move=boomDie;
	    	    	else move=zombies[i][j].getMove();
	    	    	attack=zombies[i][j].getAttack();
	    	    }
	    	          	                 
	    	    if (x<=endOfZombie) {  //画攻击
	    	    	if(carX[i] == -100 && endOfZombie == -50 ) lose=true;
                	g.drawImage(attack, x, 40+i*97, this); 
                	if(right>=0) {
              	        int id=find(i, right);
              	        if (id>=0) {   
              	            plants[id].receiveDamage(1);  
              	            if(plants[id].dead()) { 
                 	            f[i][right]=0; 
                 	            removeAPlant(id); 
              	            }
              	        }
              	    }
          	    }
	    	    else {
          	        g.drawImage(move, --x, 40+i*97, this);
          	        if(!dance && !fired) zombies[i][j].setX(x);
             	}
	    	    
	       } //循环的括回
	       zombieSum+=numOfZombies[i];
      }	 
	    
	  if(zombieSum == 0)  win = true;  //胜利判断
		  
      g.drawImage(mouse, location_x - 45, location_y - 55, 90, 100, null);
      money=String.valueOf(myMoney); //太阳数
      g.drawChars(money.toCharArray(), 0, money.length(), 106, 95); 
      }
      
      if(lose)  
    	  g.drawImage(fail, 200, 100, this);
      else if(win)  
    	  g.drawImage(won, 350,250 , this);
   }
   
   //关卡失败
   public void endGame() {
	     play = false;
	     BGM.stop();
	     thread.stop();
	     jf.dispose();
		 MenuFrame f = new MenuFrame(); //到主界面
		 f.setVisible(true);
	}
 
    //关卡通过
	public void nextGame() {
		 play = false;
		 BGM.stop();
		 thread.stop();
		 jf.dispose();  //销毁JFrame
		 GameManager g = new GameManager(2,true);  //到下一关
		 g.setVisible(true);
	}
   
    public void run() {
      while (play) {
         try {
            Thread.sleep(100);
         } catch (Exception e) {
            e.printStackTrace();
         }
         repaint(); 
         if(!jf.isDisplayable()) {  //窗口未显示
        	 BGM.stop();
         }
      }
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
   
  //是否在某区域
   private boolean inTheArea(int l,int r,int down,int up) {
	   if (location_x>=l && location_x<=r && location_y>=down && location_y<=up) 
		   return true;
	   else 
		   return false;
   }
   
   //点击生产的阳光 
   private boolean clickSun() {
	   for (int i=0;i<numOfPlants;i++) 
		  if(plants[i] instanceof SunFlower) {
			  ArrayList<Integer> suns=((SunFlower) plants[i]).getSunX();
			  int id=0;
			  for (Integer x:suns) { 
				  if(inTheArea(x+5, x+60, plants[i].getY()+40, plants[i].getY()+100)) {
					  ((SunFlower) plants[i]).decSun(id);
					  myMoney+=50;
					  return true;
				  }
				  id++;
			  }
		  }
	   return false;
   }
   
 //退出游戏
   private void exitGame() {
	   play = false;
	   thread.stop();
	   BGM.stop();
	   System.exit(0);  //退出整个程序
   }

   @Override
    public void mouseClicked(MouseEvent e) {
	    if(lose) 
	    	endGame();
	    else if(win) 
	    	nextGame();
	    if(inTheArea(818, 890, 23, 52)) 
	    	exitGame();
        if (inTheArea(148, 210, 15, 98)) {
		    new PlaySound("Audios/seedlift.mp3",false).play();
		    mouse = sunFlower; 
		    kindOfMouse=1;
		}
	    else if (inTheArea(223, 286, 15, 98)){
	    	new PlaySound("Audios/seedlift.mp3",false).play();
	    	mouse = peaShooter;
	    	kindOfMouse=2;
		}
	    else if (inTheArea(294, 360, 15, 98)){
		    new PlaySound("Audios/seedlift.mp3",false).play();
		    mouse = splitPea;
		    kindOfMouse=3;
		}
	    else if (inTheArea(365, 435, 15, 98)){
	    	new PlaySound("Audios/seedlift.mp3",false).play();
		    mouse = gatlingPea;
	    	kindOfMouse=4;
		}
		else if (inTheArea(439, 505, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = wallNut;
			kindOfMouse=5;
		}
		else if (inTheArea(510, 580, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = jalapeno;
			kindOfMouse=6;
		}
		else if (inTheArea(585, 651, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = potatoMine;
			kindOfMouse=7;
		}
		else if (inTheArea(660, 725, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = spikeRock;
			kindOfMouse=8;
		}
		else if (inTheArea(730, 798, 15, 98)) {
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = shovel;
			kindOfMouse=9;
		}
		else if(!clickSun()) 
			for (int i=0;i<5;i++)
				for (int j=0; j<9;j++)
				    if(inTheArea(gx[j], gx[j]+75, gy[i], gy[i]+90))
				    	if (f[i][j]==0 && kindOfMouse>=1 && kindOfMouse<=8 && myMoney>=cost[kindOfMouse]) {
				    		f[i][j]=kindOfMouse; 
				    		myMoney-=cost[kindOfMouse];
				    		if (kindOfMouse==1) {
				    			plants[numOfPlants]=new SunFlower(i,j,gx[j],gy[i],timer);
				    		    numOfPlants++;
				    		} 
				    		else if (kindOfMouse==2) {			    			
				    			plants[numOfPlants]= new PeaShooter(i,j,gx[j],gy[i],timer);
				    			plants[numOfPlants].addPea();
				    			numOfPlants++;
				    		} 
				    		else if (kindOfMouse==3) {			    			
				    			plants[numOfPlants]=new SplitPea(i,j,gx[j],gy[i],timer);
				    			numOfPlants++;
				    		} 
				    		else if (kindOfMouse==4) {			    			
				    			plants[numOfPlants]=new GatlingPea(i,j,gx[j],gy[i],timer);
				    			numOfPlants++;
				    		} 
				    		else if (kindOfMouse==5) {			    			
				    			plants[numOfPlants]=new WallNut(i,j,gx[j],gy[i],timer);
				    			numOfPlants++;
			    		    } 
				    		else if (kindOfMouse==6) {
				    			plants[numOfPlants]=new Jalapeno(i,j,gx[j],gy[i],timer);
				    			numOfPlants++;
				    		} 
				    		else if (kindOfMouse==7) {			    			
				    			plants[numOfPlants]=new PotatoMine(i,j,gx[j],gy[i],timer);
				    			numOfPlants++;
				    		} 
				    		else if (kindOfMouse==8) {
				    			plants[numOfPlants]=new SpikeRock(i,j,gx[j],gy[i],timer);
				    			numOfPlants++;
				    		}
				    	}
				    	else if (kindOfMouse==9 && f[i][j]>0) {
				    		new PlaySound("Audios/buttonclick.mp3",false).play();
				    	    removeAPlant(find(i,j));
				    	    mouse = shovel;
				    	    f[i][j]=0;
				        }
		
   }
   
    private void removeAPlant(int index) {
	    for (int i=index;i<numOfPlants;i++) {
	    	plants[i]=plants[i+1];
	    }
	    numOfPlants--;
	    plants[numOfPlants]=null;
    }

    //僵尸身前的第一个植物
   private int findRight(int h,int x) {  //x 当前僵尸的x
	   int right=-1;
	   for (int j=0;j<9;j++)
		   if(f[h][j]>0 && f[h][j]<8 && gx[j]<=x) 
			   right=j;
	   return right;
   }
   
   private int find(int h,int l) {
	   for(int i=0;i<numOfPlants;i++) {
		   if(plants[i].getH()==h && plants[i].getL()==l) 
			   return i;
	   }
	   return -1;
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
   public void mouseReleased(MouseEvent e) {
       mouse = null;
   }
}


