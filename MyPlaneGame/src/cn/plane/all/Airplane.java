package cn.plane.all;

import java.awt.image.BufferedImage;



public class Airplane extends FlyObject implements Enemy{
	
	private int speed; //移动速度
	
	public Airplane() {
		super(48,50);
		
		speed = 2;
	}
	public void step() {
		y+=speed; //速度为2
	}
	int index = 1;
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		if(isLife()) { //活着的返回第一张图片
			return images.airplanes[0];
		}else if(isDead()) { //死了的返回第2到5张图片 5后删除
			BufferedImage img = images.airplanes[index++];
			if(index==images.airplanes.length) {
				state =REMOVE;
			}
			return img;
			}	
		return null;
		/*
		 * 										index = 1
		 * 10毫秒时，img=airplanes[1]			index = 2  		return airplanes[1]
		 * 20毫秒时，img=airplanes[2]			index = 3		return airplanes[2]
		 * 30毫秒时，img=airplanes[3]			index = 4		return airplanes[3]
		 * 40毫秒时，img=airplanes[4]			index = 5		return REMOVE
		 * 50毫秒时		return null
		 * 
		 */
	}
	public int getScore() {
		return 1 ;
	}
}
