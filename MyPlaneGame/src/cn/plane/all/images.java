package cn.plane.all;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


//图片工具类
//Image是一个抽象类，BufferedImage是其实现类，是一个带缓冲区图像类，
public class images {
	public static BufferedImage sky; //天空
	public static BufferedImage bullet; //子弹
	public static BufferedImage heros[]; //英雄机
	public static BufferedImage airplanes[]; //敌机
	public static BufferedImage bigairplanes[]; //大敌机
	public static BufferedImage bees[]; //蜜蜂
	public static BufferedImage start;//启动图片
	public static BufferedImage pause;//暂停图片
	public static BufferedImage gameover;//游戏结束图片
	
	//加载图片
	public static BufferedImage loadImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyObject.class.getResource(fileName));//加载图片
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	static {
		start = loadImage("start.png"); //启动图片
		pause = loadImage("pause.png"); //暂停图片
		gameover=loadImage("gameover.png"); //结束图片
		
		sky =loadImage("background.png"); //天空图片
		bullet = loadImage("bullet.png"); //子弹图片
		heros = new BufferedImage[2]; //英雄机图片
		heros[0]=loadImage("hero0.png");
		heros[1]=loadImage("hero1.png");
		
		airplanes=new BufferedImage[5];
		bigairplanes=new BufferedImage[5];
		bees=new BufferedImage[5];
		
		airplanes[0]= loadImage("airplane0.png");			
		bigairplanes[0]=loadImage("bigairplane0.png");
		bees[0]=loadImage("bee0.png");
		
		for(int i = 1 ; i < airplanes.length;i++) {
			airplanes[i]=loadImage("bom"+i+".png");
			bigairplanes[i]=loadImage("bom"+i+".png");
			bees[i]=loadImage("bom"+i+".png");
			
		}
	}
}
