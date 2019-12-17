package cn.plane.all;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

//整个游戏世界
public class World extends JPanel{
	
	public static final int WIDTH = 400; //窗口的宽
	public static final int HEIGHT = 700; //窗口的高
	
	public static final int START = 0 ;//启动状态
	public static final int RUNNING = 1 ;//运行状态
	public static final int PAUSE = 2 ;//暂停状态
	public static final int GAME_OVER = 3 ;//游戏结束
	private int state = START;//当前状态

	Sky sky = new Sky(); //天空对象
	Hero hero = new Hero(); //英雄机对象
	FlyObject[] enemies = {}; //敌人数组
	Bullet[] bullets = {}; //子弹数组
	
	/** 创建敌人(小敌机、大敌机、小蜜蜂)对象 */
	public FlyObject nextOne(){
		Random rand = new Random();//随机数对象
		int type = rand.nextInt(20);//0-19随机数		
		if(type<8) {//0到4时，返回小蜜蜂
			return new Bee();
		}else if (type<12) {//5到11时，返回小敌机
			return new Airplane();
		}else  {//12到19时，返回大敌机
			return new BigAirplane();
		}		
	}
	
	
	public void action() {
		//创建侦听其对象 匿名事件
		MouseAdapter l = new MouseAdapter() {
			/** 重写mouseMoved()鼠标移动事件 */			
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) {
					 int x = e.getX();//获取鼠标的x坐标
					 int y = e.getY();//获取鼠标的y坐标
					 hero.moveTo(x, y);//英雄机随着鼠标移动
				}
			}
			public void mouseClicked(MouseEvent e) {//启动变运行，结束清空变运行
					switch(state){
						case START:
							state=RUNNING;
							break;
						case GAME_OVER:
							score=0;
							sky=new Sky();
							hero = new Hero();
							enemies = new FlyObject[0];
							bullets=new Bullet[0];
							state=START;
							break;
					}
			}
			public void mouseExited(MouseEvent e) {//运行变暂停
				 if(state==RUNNING) {
					 state=PAUSE;
				 }
			}
			public void mouseEntered(MouseEvent e) {//暂停变运行
				if(state==PAUSE) {
					state=RUNNING;
				}
			}
		};
		this.addMouseListener(l);//处理鼠标操作
		this.addMouseMotionListener(l);//处理鼠标划动事件
		
		Timer timer= new Timer();//定时器对象
		int intervel = 10;//以毫秒为单位
		timer.schedule(new TimerTask() {
			
			
			public void run() { //定时干的事(每10毫秒走一次)
				if(state==RUNNING) {
					enterAction();//调用方法 实现 敌人入场 在enteraction中
					shootAction();//子弹入场 
					stepAction();//飞行物移动
					outOfBoundsAction();//删除越界的敌人和子弹
					bulletBangAction(); //子弹与敌机的碰撞
					heroBangAction();//英雄机与敌人碰撞
					checkGameOverAction();//检测游戏结束
				}
				
				repaint();//重画(重新调用paint方法)
				
				
			}
		},intervel,intervel);//定时计划
	}
	
	/** 游戏结束 */
	public void checkGameOverAction() {
		if(hero.getLife()<=0) {
			state=GAME_OVER;
		}
	}
	/** 删除越界的敌人与子弹 */
	public void outOfBoundsAction() {
		int index = 0;// 1)不越界敌人数组下标 2)不越界敌人的个数
		FlyObject[] enemyLives = new FlyObject[enemies.length]; // 不越界敌人数组
		for (int i = 0; i < enemies.length; i++) {// 遍历所有敌人
			FlyObject obj = enemies[i];// 获取每一个敌人
			if (!obj.outOfBounds() && !obj.isRemove()) {// 不越界并且没删除状态
				enemyLives[index] = obj; // 将不越界敌人添加到不越界敌人数组中
				index++;// 1)下标增一 2)不越界敌人个数增一
			}

		}
		enemies = Arrays.copyOf(enemyLives, index);// 将不越界敌人数组复制到enemies中，enemies的长度为index(不越界敌人个数)
		System.out.println("1:" + index);
		index = 0;// 归零
		Bullet[] bulletLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds() && !b.isRemove()) {// 不越界 并且没删除状态
				bulletLives[index] = bullets[i];
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);
		System.out.println("2:" + index);
	}


	/** 飞行物移动 */
	public void stepAction() {
		sky.step();//天空动
		for(int i = 0 ; i < enemies.length;i++) {//遍历所有敌人
			enemies[i].step();//敌人动
		}
		for(int i = 0 ;i<bullets.length;i++) {//遍历所有子弹
			bullets[i].step();//子弹动
		}
	}
	
	int enterIndex = 0;//敌人入场计数
	/** 敌人入场 */
	public void enterAction() {// 每10毫秒走一次
		enterIndex++;// 每10毫秒增1
		if (enterIndex % 40 == 0) {// 每400(10*40)毫秒走一次
			FlyObject obj = nextOne();// 获取敌人对象
			enemies = Arrays.copyOf(enemies, enemies.length + 1);// 扩容
			enemies[enemies.length - 1] = obj;// 将敌人对象添加到enemies的最后一个元素上
		}
	}

	int shootIndex = 0;// 子弹入场计数

	/** 子弹入场 */
	public void shootAction() { // 每10毫秒走一次
		shootIndex++;// 每10毫秒增1
		if (shootIndex % 30 == 0) { // 每300(10*30)毫秒走一次
			Bullet[] bs = hero.shoot();// 获取英雄机发射出来的子弹对象
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);// 扩容(bs有几个就扩大几个容量)
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// 数组的追加
		}
	}
	
	int score = 0 ;
	public void bulletBangAction() {  //每10毫秒走一次
		for(int i = 0 ; i <bullets.length;i++) { //遍历所有子弹
			Bullet b = bullets[i]; 		//获取每一个子弹
			for(int j = 0 ;j<enemies.length;j++) { //遍历所有敌人
				FlyObject f = enemies[j]; //获取每一个敌人
				if(b.isLife()&&f.isLife()&&f.hit(b)) { //子弹活着  敌人活着 子弹和敌人碰撞 
					b.goDead(); //子弹死
					f.goDead(); //敌人死					
					if(f instanceof Enemy) {
						Enemy e = (Enemy)f;//超类中没有 需要强转 从别的类中借出来
						score+=e.getScore();
						System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
					}
					if(f instanceof Award) {//若被撞敌人为奖励
						Award a = (Award) f;//则被撞敌人
						int type = a.getAwardType();//玩家得分
						switch(type) { //根据奖励类型获取不同的奖励
						case Award.DOUBLE_FIRE://奖励类型为火力
							hero.addDoubleFire();//英雄机增加火力
							break;
						case Award.LIFE://若奖励类型为命
							hero.addLife();//则英雄机增命
							break;
						}
					}
				}
			}
		}
	}
	/**  英雄机与敌人的碰撞*/
	public void heroBangAction() {
		for(int k = 0 ; k < enemies.length;k++){
			FlyObject f = enemies[k];
			if(hero.isLife()&&enemies[k].isLife()&&hero.hit(f)) {
				f.goDead();
				hero.subtractLife();
				hero.clearDoubleFire();
			}	
		}
	}
	
	//画
	public void paint(Graphics g) {
		sky.paintObject(g);
		hero.paintObject(g);
		for(int i = 0 ; i <enemies.length;i++) {
			enemies[i].paintObject(g);
		}
		for(int i = 0 ; i < bullets.length;i++) {
			bullets[i].paintObject(g);	
		}
		g.drawString("成绩:"+score,10,25);
		g.drawString("生命:"+hero.getLife(),10,45 );
		g.drawString("火力值"+hero.getDoubleFire(),10,65);
		
		switch (state) {
		case START:
			g.drawImage(images.start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(images.pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(images.gameover,0,0,null);
			break;
			
		}
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame(); //window窗口对象
		World world = new World(); //实例化对象
		frame.add(world);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //使用 System exit方法退出应用程序
		frame.setSize(WIDTH,HEIGHT); //设置长和宽
		frame.setLocationRelativeTo(null); //设置窗口相对于指定组件的位置
		frame.setVisible(true); //设置窗口是否显示
		world.action(); //实例化窗口动作
	
	
	
	
	}
}
