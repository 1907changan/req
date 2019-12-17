package cn.plane.all;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class Sky extends FlyObject{
	
	private int speed;
	private int y1;//第二张图片的坐标


	public Sky(){//构造方法
		super(World.WIDTH, World.HEIGHT,0,0);
		
		speed = 1 ;
		y1 = -World.HEIGHT;
	}
	public void step(){ //重写飞行物移动方法step()
		y+=speed;
		y1+=speed; //y1为第二张图片的坐标
		if(y>=World.HEIGHT) {
			y =- World.HEIGHT;
		}
		if(y1>=World.HEIGHT) {
			y1=-World.HEIGHT;
		}
	}
	@Override
	public BufferedImage getImage() { //重写抽象方法getImage()
		// TODO Auto-generated method stub
		return images.sky;
	}
	//重写
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
}
