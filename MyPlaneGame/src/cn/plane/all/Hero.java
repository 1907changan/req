package cn.plane.all;

import java.awt.image.BufferedImage;



public class Hero extends FlyObject{
	
	private int life; //生命
	private int doubleFire; //火力
	
	public Hero(){
		super(97,139,140,400);
		
		life = 3 ;
		doubleFire = 0 ;
	}
	public void moveTo(int x , int y) {//鼠标的x和y 
		this.x=x-this.width/2;
		this.y=y-this.height/2;
		
	}
	
	public void step() { 
		
	}

	int index = 0 ;
	public BufferedImage getImage() {		//每10毫秒切换一次图片。
			return images.heros[index++%images.heros.length];
			/*			images.heros.length：代表照片的长度 也就是2 
			 * 			可写成 index++% 2
			 * 									index = 0 ;
			 * 10毫秒 返回heros[0]				index = 1 ;
			 * 20毫秒 返回heros[1]				index = 2 ;
			 * 30毫秒 返回heros[2]  				index = 3 ;
			 * 40毫秒 返回heros[3]				index = 4 ;
			 * 
			 */
			
	}
	public Bullet[] shoot () {//射击
		int xStep = this.width/4;
		int yStep = 20 ;
		if(doubleFire>0) {
					Bullet [] bs=new Bullet[2];
					bs[0]=new Bullet(this.x+1*xStep,this.y-yStep);
					bs[1]=new Bullet(this.x+3*xStep,this.y-yStep);
					doubleFire-=2;//-火力值
					return bs;
		}else{
			Bullet [] bs = new Bullet[1];
			bs[0]=new Bullet(this.x+2*xStep,this.y-yStep);
			return bs ;
		}
	}
	//数据私有 行为公开化
	public void addLife() {//增命
		life++ ;
	}
	public void addDoubleFire() {//增加火力
		doubleFire+=40;
	}
	public int getLife() {//获取英雄机的命数
		return life;
	}
	public void subtractLife() {//减命
		life--;
	}
	
	public void clearDoubleFire() {//清空火力值
		doubleFire=0;
	}
	public int getDoubleFire() {
		return doubleFire;
	}

}
