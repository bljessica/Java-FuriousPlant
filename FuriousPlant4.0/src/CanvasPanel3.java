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

//黑夜  
public class CanvasPanel3 extends JPanel implements Runnable,MouseListener,MouseMotionListener{
	
	private Image sun;
	private Image sunShroom;
	private Image puffShroom;
	private Image scaredyShroom;
	private Image scaredyShroomCry;
	private Image fumeShroom;
	private Image iceShroom;
	private Image doomShroom;
	private Image beginBoom;
	private Image boom;
	private Image boomDie;
	private Image hole;
	private Image splitPea;
	private Image bullet;
	private Image pumpkinHead;
	private Image shovel;
	private Image lawnCleaner;
	private Image back;
	private Image mouse;
	private Image puffShroomBullet;
	private Image fumeShroomBullet;
	private Image stringZombie;
	private Image dancingZombieDance;
	private Image dancingZombieWalk;
	private Image dancingZombieAttack;
	private Image fail;
	private Image won;
	private Image hand;

	private Thread thread;
	
	public static Random random = new Random();
	
	private PlaySound BGM = new PlaySound("Audios/Ultimate Battle.mp3",true);
	private PlaySound loseBGM = new PlaySound("Audios/losemusic.mp3",false);
	private PlaySound winBGM = new PlaySound("Audios/winmusic.mp3",false);
	
	private boolean win = false;
	private boolean lose = false;
	private boolean play = true;
	private int kindOfMouse;
	private int[][] f = new int[5][9];     //每个格子是什么植物
	private int[] gx = {92,177,255,339,417,497,581,655,732};
	private int[] gy = {99,189,290,385,485};
	private Zombie[][] zombies = new Zombie[5][40];
	private ArrayList<Plant> plants = new ArrayList<Plant>();
	private int[] numOfZombies = new int[5];
	private int[] numOfZombiesForA = new int[5];
	private int[] carX = new int[5];
	private int timer = 0;
	private int drag = 0;
	private String money = "50";
	private int myMoney = 50;
	private int[] cost = {0,25,5,50,50,100,100,100,20}; 
	          //null,sunShroom,puffShroom,scaredShroom,fumeShroom,iceShroom,beginBoom,splitPea,pumpkinHead;
	private boolean iced;
	private boolean fired;
	private int location_x,location_y;
    private	int startX = 850;
    private JFrame jf;
	
	public CanvasPanel3(JFrame jf) {
		super();
		this.jf = jf;
		loadImage();
		mouse = null;
		win = false;
		lose = false;
		play = true;
		for(int i=0;i<5;i++) {
			carX[i] = -1;
			for(int j=0;j<9;j++) {
				f[i][j] = 0;
			}
		}
		loadZombie();
		if(thread == null || !thread.isAlive()) {
			thread = new Thread(this);
		}
		thread.start();
		BGM.play();
	}
	
	private void loadImage() {
		sun = new ImageIcon("Images/Plants/sun.png").getImage();
		sunShroom =new ImageIcon("Images/Plants/sunShroom.gif").getImage();
		puffShroom = new ImageIcon("Images/Plants/puffShroom.gif").getImage();
		scaredyShroom = new ImageIcon("Images/Plants/scaredyShroom.gif").getImage();
		scaredyShroomCry = new ImageIcon("Images/Plants/scaredyShroomCry.gif").getImage();
		fumeShroom = new ImageIcon("Images/Plants/fumeShroom.gif").getImage();
        iceShroom = new ImageIcon("Images/Plants/iceShroom.gif").getImage();
        doomShroom= new ImageIcon("Images/Plants/doomShroom.gif").getImage();
		beginBoom = new ImageIcon("Images/Plants/beginBoom.gif").getImage();
		boom=new ImageIcon("Images/Plants/peng0.1.gif").getImage();
		boomDie=new ImageIcon("Images/Zombies/boomDie.gif").getImage();
		hole= new ImageIcon("Images/Plants/hole.png").getImage();
		splitPea = new ImageIcon("Images/Plants/splitPea.gif").getImage();
		bullet = new ImageIcon("Images/Plants/bullet.png").getImage();
		pumpkinHead= new ImageIcon("Images/Plants/pumpkinHead.gif").getImage();
		puffShroomBullet = new ImageIcon("Images/Plants/puffShroomBullet.png").getImage();
		fumeShroomBullet = new ImageIcon("Images/Plants/fumeShroomBullet.png").getImage();
		
		won = new ImageIcon("Images/UI/trophy.png").getImage();
		fail = new ImageIcon("Images/UI/zombiesWon.png").getImage();
		shovel = new ImageIcon("Images/UI/shovel.png").getImage();
		lawnCleaner = new ImageIcon("Images/UI/lawnCleaner.png").getImage();
		back = new ImageIcon("Images/UI/backgroundnight.png").getImage();
		hand = new ImageIcon("Images/UI/hand.png").getImage();

		stringZombie = new ImageIcon("Images/Zombies/p.png").getImage();
		dancingZombieDance = new ImageIcon("Images/Zombies/dancingZombieDance.gif").getImage();
		dancingZombieAttack=new ImageIcon("Images/Zombies/dancingZombieAttack.gif").getImage();
		dancingZombieWalk=new ImageIcon("Images/Zombies/dancingZombieWalk.gif").getImage();
	}
	
	private Zombie getRandZombie(int x) {
		int randNum=random.nextInt(10);
		switch (randNum) {
		case 0:
			return new ConeheadZombie(x);
			
		case 1:
			return new ConeheadZombie(x);
			
		case 2:
			return new BucketheadZombie(x);
			
		case 3:
			return new BucketheadZombie(x);
			
		case 4:
			return new DancingZombie(x,0);
			
		case 5:
			return new PoleVaultingZombie(x);
			
		case 6:
			return new StringZombie(x);
			
		default:
			return new OrdinaryZombie(x);
		}
	}
	
	private void loadZombie() {
		if(MenuPanel.fight == false) {
		   int randNum,front;
		   randNum=random.nextInt(5);
		   
		   zombies[randNum][0]=new FlagZombie(startX+random.nextInt(200));
		   numOfZombies[randNum]++;
		   
		   for (int i=0;i<5;i++) {
	
		       randNum=random.nextInt(4)+2;
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
		       
		       front=4900;
		       randNum=random.nextInt(5)+2;
		       for (int j=0;j<randNum;j++) {
		    	   front+=random.nextInt(150);
		    	   zombies[i][numOfZombies[i]]= getRandZombie(front);
			       numOfZombies[i]++;
		       }
		   }
		   randNum=random.nextInt(5);
		   addAZombie(randNum, 4800);
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
	    	else {index=i; break;}  //第i格一直是空的
	        
	    if (x>=4800) zombies[h][index] = new FlagZombie(x);
	    else zombies[h][index] = new BackupDancer(x);
	    numOfZombies[h]++;
	}
	
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
	
	private void destroyAZombie(int h,int index) {
		index++;
		for(int i=index;i<numOfZombies[h];i++) {
			zombies[h][i-1] = zombies[h][i];
		}
		numOfZombies[h]--;
		zombies[h][numOfZombies[h]] = null;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(back,5,8,this);
		int leftOfZombie;
		timer++;
		
		if(lose == false &&win == false) {
		for(Plant plant:plants) {
			if(plant instanceof SunShroom) {   //阳光菇
				SunShroom s =(SunShroom)plant;
				g.drawImage(s.getImage(), s.getX()+14, s.getY()+10, this);
				
				if(timer-s.getFrontTime()>200) {
					((SunShroom)plant).addSun(random.nextInt(60));
					plant.setFrontTime(timer);
				}
				
				for(Integer o:s.getSunX())  //画太阳
					g.drawImage(sun, o, s.getY()+30, this);

			}
			else if(plant instanceof PuffShroom) {   //小喷菇
				PuffShroom p = (PuffShroom)plant;
				g.drawImage(puffShroom, p.getX()+25, p.getY(), this);
                int h = p.getH();
                if(numOfZombiesForA[h]>0 && numOfZombies[h]>0) {
                	leftOfZombie = zombies[h][0].getX()+40;
                	if(timer - p.getFrontTime()>50) {
                		plant.addPea();
                		plant.setFrontTime(timer);
                	}
                	for(int o:p.getBX()) {
                		if(o-p.getX()>230) p.removePea();
                		
                		if(o>leftOfZombie) {
                			zombies[h][0].receiveDamage(p.getAtk());
                			if(zombies[h][0].dead()) destroyAZombie(h,0);
                			
                			plant.removePea();
                		}
                		g.drawImage(puffShroomBullet, o+40, p.getBY()+80, this);
                	}
                }
			}
			else if(plant instanceof ScaredyShroom) {   //胆小菇
				ScaredyShroom s = (ScaredyShroom)plant;
				int h = s.getH();
				if(numOfZombies[h]>0 && zombies[h][0].getX()<s.getX()+200)
					g.drawImage(scaredyShroomCry, s.getX()+25, s.getY(), this);
				else {
				    g.drawImage(scaredyShroom, s.getX()+25, s.getY(), this);
                
                    if(numOfZombiesForA[h]>0 && numOfZombies[h]>0) {
                    	leftOfZombie = zombies[h][0].getX()+40;
                	    if(timer - s.getFrontTime()>30) {
                		    plant.addPea();
                    		plant.setFrontTime(timer);
                    	}
                	    for(int o:s.getBX()) {
                		    if(o>leftOfZombie) {
                			    zombies[h][0].receiveDamage(s.getAtk());
                			    if(zombies[h][0].dead()) destroyAZombie(h,0);
                			
                    			plant.removePea();
                	    	}
                		    g.drawImage(puffShroomBullet, o+40, s.getBY()+80, this);
                    	}
                    }
				}
			}
			else if(plant instanceof FumeShroom) {   //大喷菇
				FumeShroom f = (FumeShroom)plant;
				g.drawImage(fumeShroom, f.getX()+5, f.getY()+10, this);
				int h = f.getH();
				if(numOfZombiesForA[h]>0 && numOfZombies[h]>0) {
					leftOfZombie = zombies[h][0].getX();
					if((timer - f.getFrontTime()>50)&&(timer - f.getFrontTime()<75)) {
						g.drawImage(fumeShroomBullet,f.getX()+80 , f.getY()+20, this);
						
					}
					if((timer - f.getFrontTime()==74)&&(f.getX()+330>=leftOfZombie)) {
						for (int i=0;i<numOfZombies[h];i++)
							if (zombies[h][i].getX()>f.getX()+330) break;
							else {
								zombies[h][i].receiveDamage(f.getAtk());
								if(zombies[h][i].dead()) destroyAZombie(h,i);
							}

					}
					else if(timer - f.getFrontTime()>=75) 
						f.setFrontTime(timer);
					
				}
			}
			else if (plant instanceof SplitPea) {   //分裂射手
				  SplitPea p= (SplitPea) plant;
				  g.drawImage(splitPea, p.getX()+5, p.getY()+10, this);
				  
				  ArrayList<Integer> bx=p.getBX();
				  int h=p.getH();
				  
				  if(numOfZombiesForA[h]>0  && numOfZombies[h]>0) {   //发射豌豆
				     leftOfZombie=zombies[h][0].getX()+40;
				     if( timer-p.getFrontTime()>50) {
				    	   plant.addPea();
				    	   plant.setFrontTime(timer);
				    	 }
				     int od=0;
				     for (int o: bx) {
				    	if(o<0) ((SplitPea)plant).removePea(od); else
				        if (o>leftOfZombie) {
					        zombies[h][0].receiveDamage(p.getAtk());
					        if (zombies[h][0].dead()) destroyAZombie(h,0);
					
					        ((SplitPea)plant).removePea(0);
				        }
				        g.drawImage(bullet,o+45,p.getBY()+54,this); od++;
				     }
				  }
				  
			   }
			else if(plant instanceof IceShroom) {   //冰冻菇
				IceShroom s=(IceShroom) plant;
				g.drawImage(iceShroom, s.getX(), s.getY(), this);
				
				if(timer-s.getFrontTime()<200) iced=false;
				else if (timer-s.getFrontTime()<350) iced = true;
				else {
					  iced = false;
					  plant.setFrontTime(timer);
				  }
			}
			else if(plant instanceof DoomShroom) {    //毁灭菇
				 DoomShroom d=(DoomShroom) plant;
				  
				  if (timer-d.getPlantedTime()<58) { //正常
					  g.drawImage(doomShroom, d.getX()-5, d.getY()-10, this);
				  } else
				  if (timer-d.getPlantedTime()<74) { //开始爆炸
					  g.drawImage(beginBoom, d.getX()-5, d.getY()-10, this);
				  }
				  else if (timer-d.getPlantedTime()<115) { //爆炸
					  g.drawImage(boom, d.getX()-100, d.getY()-150, this);
					  fired = true;
				  }
				  else  { //变坑
					  f[d.getH()][d.getL()]=11;
					  g.drawImage(hole, d.getX(), d.getY()+30, this);
					  if(timer-d.getPlantedTime()==210) {//   
						  fired = false;
						  for(int i=0;i<5;i++) 
							  for (int j=0;j<numOfZombiesForA[i];j++)
								  destroyAZombie(i,0);
				      }
				  }
			}
			else if(plant instanceof PumpkinHead) {   //南瓜头
				PumpkinHead f = (PumpkinHead)plant;
				g.drawImage(pumpkinHead, f.getX(), f.getY()+20, this);
			}
		}
			
		Image move;
		Image attack;
		boolean dance;
		int x,endOfZombie;
		int zombieSum = 0;
		
	    for (int i=0; i<5; i++) {
	    	numOfZombiesForA[i]=0;
	    	if (carX[i]>=790) carX[i]=-100;
	        else if (carX[i]>=0) carX[i]+=7;
	    	  
	    	g.drawImage(lawnCleaner, carX[i]+20, 120+i*94, this); 
	 
	        for (int j=0;j<numOfZombies[i];j++) {
	        	x=zombies[i][j].getX();
	        	dance=false; attack=null; move=null;
	        	
	        	if(x<=carX[i]-30) { //小车
	        		if(carX[i]<0) carX[i]=0;  
	        		destroyAZombie(i,0);
	        		j--; continue;	        		
	        	} 
	            if(x>=startX) {   //画僵尸
	        	    zombies[i][j].setX(--x);
	        		continue;
	        	}
	            
	        	if (x<770) numOfZombiesForA[i]++;
	        	
	            int right=findRight(i,x);
	    	    if (right<0) endOfZombie=-50; 
	    	    else endOfZombie=gx[right];	          

	        	if(zombies[i][j] instanceof StringZombie) {  //皮筋僵尸
	        		StringZombie s = (StringZombie)zombies[i][j];
	        		if(!s.isShow()) {
	        			 if(plants.size()==0) {
	        				 destroyAZombie(i, j);
				        	 j--;
				        	 continue;
	        			 }
		        		 int r=random.nextInt(plants.size());
		        	     ((StringZombie)zombies[i][j]).
		        	            setPhl(plants.get(r).getH(), plants.get(r).getL());
		        		 zombies[i][j].setX(gx[plants.get(r).getL()]); 
		        		 
		        		 ((StringZombie)zombies[i][j]).setY(plants.get(r).getY()); //皮筋僵尸当前位置
		        		 ((StringZombie)zombies[i][j]).setFrontTime(timer);
		        		 ((StringZombie)zombies[i][j]).setShow(true);
		        		
	        		}

			        if(timer - s.getFrontTime() <= gy[s.getPh()]-70) { //要到达的位置
			        	System.out.println(s.getX()-45);
			        	System.out.println(timer+"HHH"+s.getFrontTime()+"y="+s.getY());
		        		g.drawImage(stringZombie, s.getX()-45, timer - s.getFrontTime(), this);
		        		((StringZombie)zombies[i][j]).setY(timer - s.getFrontTime());
		        	}
		        	else if(timer - s.getFrontTime() <= gy[s.getPh()]-50){
		       			g.drawImage(stringZombie, s.getX()-45, s.getY(), this);
		       		} else {
		        		if(find(s.getPh(), s.getPl())>=0) {
		       			    plants.remove(find(s.getPh(), s.getPl()));
		       			    if(f[s.getPh()][s.getPl()]==5) iced=false;
		       			    f[s.getPh()][s.getPl()]=0;
		        		}
		       			destroyAZombie(i, j);
		       			j--;
		       		}
	
			        System.out.println(timer+"HHH@@@2"+s.getFrontTime()+"y="+s.getY());
	       	    }
	        	else {   //其他僵尸选择图片
		            if (zombies[i][j] instanceof PoleVaultingZombie) {   //撑杆跳僵尸
		            	PoleVaultingZombie p = ((PoleVaultingZombie)zombies[i][j]);
		            	if (x<=endOfZombie && !p.isJumpflag()) {  //僵尸到end
	                        ((PoleVaultingZombie)zombies[i][j]).setJumpflag(true);
	                        zombies[i][j].setX(x-70);
		            	}
		            	
		                if(fired) move=boomDie;
		                else if(iced) move=p.getIced();
		                else move=p.getMove();
		            	attack=p.getAttack();
		    	    } 
		            else if (zombies[i][j] instanceof DancingZombie) {   //舞王僵尸
			            	if (x>endOfZombie) {  //僵尸未到end
		                        DancingZombie d = (DancingZombie)zombies[i][j];
				    	        if(timer - d.getFrontDanceTime()<150) move = dancingZombieWalk; 
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
			                if(fired) move=boomDie;
			                else if(iced) move=zombies[i][j].getIced();
			    	    }
		            else{
		    	    	if (iced) move = zombies[i][j].getIced();
		    	    	else if(fired) move=boomDie;
		    	    	else move=zombies[i][j].getMove();
		    	    	attack=zombies[i][j].getAttack();
		    	    	
		    	    } 	                 
	    	    if (x<=endOfZombie && !fired) {  //画
                	g.drawImage(attack, x, 40+i*97, this); 
                	if(carX[i] == -100 && endOfZombie == -50 ) lose=true;
                	if(right>=0) {
                	    int id=find(i, right);
                	    if(id>=0) {
	                	    plants.get(id).receiveDamage(1);
	                	    if(plants.get(id).dead()) { 
	                	    	Plant tmpP=plants.get(id);
	                	    	 if(f[i][right]==5) iced=false;
	                	    	if(f[i][right]==18) f[i][right]=((PumpkinHead)tmpP).getPlant(); 
	                	    	else f[i][right]=0; 
	                	        plants.remove(id); 
	                	    }
                	    }
                	}
            	}else {
            	    g.drawImage(move, --x, 40+i*97, this);
            	    if(!dance && !iced && !fired) zombies[i][j].setX(x);
            	}
	        	}
	        	      	
	        } //循环的括回
	        zombieSum+=numOfZombies[i];
	    }
	    if(zombieSum == 0) win = true;
	    
	    g.drawImage(mouse, location_x - 45, location_y - 55, 90, 100, null);
	    money=String.valueOf(myMoney);
	    g.drawChars(money.toCharArray(), 0, money.length(), 106, 95);
		}
		
	    if(lose) g.drawImage(fail, 200, 100, this);
	      
	    if(win) g.drawImage(won, 350,250 , this);
	}
	
	public void endGame() {
	     play = false;
	     BGM.stop();
	     thread.stop();
	     jf.dispose();
		 MenuFrame f = new MenuFrame();
		 f.setVisible(true);
	}

	 public void nextGame() {
		 endGame();
	 }
	
	public void run() {
	      while (play) {
	         try {
	            Thread.sleep(100);
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	         repaint(); 
	         if(!jf.isDisplayable()) {
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
	
	private boolean inTheArea(int l,int r,int down,int up) {
		if (location_x>=l && location_x<=r && location_y>=down && location_y<=up) 
			return true;
		else return false;
	}
	
	private boolean clickSun() {
		for (Plant p:plants) 
		    if(p instanceof SunShroom) {
			    ArrayList<Integer> suns=((SunShroom) p).getSunX();
				int id=0;
				for (Integer x:suns) { 
					if(inTheArea(x+10, x+50, p.getY()+40, p.getY()+100)) {
						((SunShroom) p).decSun(id);
					    myMoney+=25;
						return true;
					}
					id++;
				}
			}
		 return false;
	}
	
	private void exitGame() {
		play = false;
		   thread.stop();
		   BGM.stop();
		   System.exit(0);
	   }

	@Override
	public void mouseClicked(MouseEvent e) {
	    location_x = e.getX();
	    location_y = e.getY();
	    if(lose) endGame();
		if(win) exitGame();
		if(inTheArea(818, 890, 23, 52)) exitGame();
		if (inTheArea(148, 210, 15, 98)) {
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = sunShroom; 
			kindOfMouse=1;
			}
		else if (inTheArea(223, 286, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = puffShroom;
			kindOfMouse=2;
			}
		else if (inTheArea(294, 359, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = scaredyShroom;
			kindOfMouse=3;
			}
		else if (inTheArea(367, 432, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = fumeShroom;
			kindOfMouse=4;
			}
		else if (inTheArea(439, 502, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = iceShroom;
			kindOfMouse=5;
			}
		else if (inTheArea(513, 576, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = doomShroom;
			kindOfMouse=6;
			}
		else if (inTheArea(587, 651, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = splitPea;
			kindOfMouse=7;
			}
		else if (inTheArea(660, 724, 15, 98)){
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse=pumpkinHead;
			kindOfMouse=8;
			}
		else if (inTheArea(733, 798, 15, 98)) {
			new PlaySound("Audios/seedlift.mp3",false).play();
			mouse = shovel;
			kindOfMouse=9;
			}
		else if (inTheArea(820, 893, 65, 100)) {
			new PlaySound("Audios/buttonclick.mp3",false).play();
			mouse = hand;
			kindOfMouse=10;
			}
		else if(!clickSun()) 
			for (int i=0;i<5;i++)
				for (int j=0; j<9;j++)
				    if(inTheArea(gx[j], gx[j]+75, gy[i], gy[i]+90))
				    	if (f[i][j]==0 && kindOfMouse>=1 && kindOfMouse<=7 && myMoney>=cost[kindOfMouse]) {
				    		f[i][j]=kindOfMouse;
				    		new PlaySound("Audios/plant1.mp3",false).play();
				    		myMoney-=cost[kindOfMouse];
				    		
				    		switch (kindOfMouse) {
				    		case 1:
				    			plants.add(new SunShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 2:
				    			plants.add(new PuffShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 3:
				    			plants.add(new ScaredyShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 4:
				    			plants.add(new FumeShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 5:
				    			plants.add(new IceShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 6:
				    			plants.add(new DoomShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 7:
				    			plants.add(new SplitPea(i,j,gx[j],gy[i],timer));
				    			break;
				    		}
				    	}
				    	else if (kindOfMouse>=1 && kindOfMouse<=7 && myMoney>=cost[kindOfMouse]
				    			&& f[i][j]==8) {
				    		f[i][j]=18;
				    		plants.get(find(i, j)).setPlant(kindOfMouse);
				    	    new PlaySound("Audios/plant1.mp3",false).play();
				    		myMoney-=cost[kindOfMouse];
				    		switch (kindOfMouse) {
				    		case 1:
				    			plants.add(new SunShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 2:
				    			plants.add(new PuffShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 3:
				    			plants.add(new ScaredyShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 4:
				    			plants.add(new FumeShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 5:
				    			plants.add(new IceShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 6:
				    			plants.add(new DoomShroom(i,j,gx[j],gy[i],timer));
				    			break;
				    		case 7:
				    			plants.add(new SplitPea(i,j,gx[j],gy[i],timer));
				    			break;
				    		}
				        }
				    	else if(kindOfMouse==8 &&myMoney>=cost[8]) {
				    		Plant tmpP=new PumpkinHead(i,j,gx[j],gy[i],timer);
				    		tmpP.setPlant(f[i][j]);
				    		plants.add(tmpP);
				    		
				    		myMoney-=cost[8];
				    		if(f[i][j]>0)f[i][j]=18;
				    		else f[i][j]=8;
				    	}
				    	else if (kindOfMouse==9 && f[i][j]>0) {
				    		new PlaySound("Audios/shovel.mp3",false).play();
				    	    plants.remove(find(i,j));
				    	    mouse = shovel;
				    	    f[i][j]=0;
				        }
	}
	
	private int findRight(int h,int x) {
		   int right=-1;
		   for (int j=0;j<9;j++)
			   if(  ((f[h][j]>0 && f[h][j]<9)||(f[h][j]==18))  && gx[j]<=x) right=j;
		   return right;
	}
	
	private int find(int h,int l) {
		int n=0;
		if (f[h][l]==18)
			for(Plant p: plants) {
				if(p.getH()==h && p.getL()==l && p instanceof PumpkinHead) {
					 return n;
				}
				n++;
			}
		else
		for(Plant p: plants) {
			if(p.getH()==h && p.getL()==l) {
				 return n;
			}
			n++;
		}
		return -1;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i=0;i<5;i++)
			for (int j=0; j<9;j++) 
				if(kindOfMouse==10 && inTheArea(gx[j], gx[j]+70, gy[i], gy[i]+90) && f[i][j]>0) {
            	
					drag=f[i][j];
            	    f[i][j]=0; 
            	    plants.remove(find(i, j)); 
            	    
					switch (drag) {
		    		case 1:
		    			mouse= sunShroom;
		    			break;
		    		case 2:
		    			mouse = puffShroom;
		    			break;
		    		case 3:
		    			mouse = scaredyShroom;
		    			break;
		    		case 4:
		    			mouse = fumeShroom;
		    			break;
		    		case 5:
		    			mouse = iceShroom;
		    			break;
		    		case 6:
		    			mouse = beginBoom;
		    			break;
		    		case 7:
		    			mouse = splitPea;
		    			break;
		    		case 8:
		    			mouse=pumpkinHead;
		    			break;
		    		}
				}	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (int i=0;i<5;i++)
			for (int j=0; j<9;j++) 
				if(kindOfMouse==10 && inTheArea(gx[j], gx[j]+70, gy[i], gy[i]+90) && f[i][j]==0) {
					f[i][j]=drag;
					kindOfMouse=0;
					switch (f[i][j]) {
		    		case 1:
		    			plants.add(new SunShroom(i,j,gx[j],gy[i],timer));
		    			break;
		    		case 2:
		    			plants.add(new PuffShroom(i,j,gx[j],gy[i],timer));
		    			break;
		    		case 3:
		    			plants.add(new ScaredyShroom(i, j, gx[j], gy[i], timer));
		    			break;
		    		case 4:
		    			plants.add(new FumeShroom(i,j,gx[j],gy[i],timer));
		    			break;
		    		case 5:
		    			plants.add(new IceShroom(i,j,gx[j],gy[i],timer));
		    			break;
		    		case 6:
		    			plants.add(new DoomShroom(i,j,gx[j],gy[i],timer));
		    			break;
		    		case 7:
		    			plants.add(new SplitPea(i,j,gx[j],gy[i],timer));
		    			break;
		    		case 8:
		    			plants.add(new PumpkinHead(i,j,gx[j],gy[i],timer));
		    			break;
		    		}
				}	
		mouse = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
