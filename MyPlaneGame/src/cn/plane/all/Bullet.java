package cn.plane.all;

import java.awt.image.BufferedImage;

//子弹
public class Bullet extends FlyObject{

	private int speed ; //速度

	public Bullet(int x , int y) {
		super(8,20,x,y);
		
		speed = 3;
	}
	public void step() {
		y-=speed; //speed = 3
	}
	@Override
	public BufferedImage getImage() {//重写抽象方法getImage()
		if(isLife()) { //活着显示图片
			return images.bullet;
		}else if (isDead()) { //死了的移除
			state = REMOVE;
		}
		return null;
		
	}
	public boolean outOfBounds() {
		return this.y<=-this.height;
		
}
	
}
