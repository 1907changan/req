package cn.plane.all;

import java.awt.image.BufferedImage;


public class BigAirplane extends FlyObject implements Enemy{

	private int speed;//移动速度

	public BigAirplane() {
		super (66,89);
		
		speed = 2;
	}
	public void step(){
		y+=speed; //大敌机起始速度为2
	}
	int index = 1 ;
	public BufferedImage getImage() {
		if(isLife()) {
			return images.bigairplanes[0];
		}else if (isDead()) {
			BufferedImage img = images.bigairplanes[index++];
			if(index==images.bigairplanes.length) {
				state =REMOVE;
			}
			return img;
			}
		
		return null;
	}
	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 3;
	}
}
