package cn.plane.all;

import java.awt.image.BufferedImage;
import java.util.Random;




public class Bee extends FlyObject implements Award {
	
	private int xSpeed; //x坐标移动速度
	private int ySpeed; //y坐标移动速度
	private int awardType;//奖励类型 奖励一个生命或者40火力值

	
	public Bee(){
		super(60,51);
		Random rand = new Random();
		xSpeed = 1 ;
		ySpeed = 2 ;
		awardType = rand.nextInt(2);
	}
	public void step() {
		y+=ySpeed; //初始值为2
		x+=xSpeed; //初始值为1
		if(x<=0 || x>=World.WIDTH-this.width) {
			xSpeed*=-1;
		}
	}
	int index = 1;
	public BufferedImage getImage() {
		if(isLife()) {
			return images.bees[0];
		}else if (isDead()) {
			BufferedImage img = images.bees[index++];
			if(index==images.bees.length) {
				state =REMOVE;
			}
			return img;
			}
		
		return null;
	}
	public int getAwardType() {
		return awardType;
	}
}
