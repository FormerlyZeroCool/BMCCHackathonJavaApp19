package com.amazonaws.samples;
import java.awt.Color;
import java.awt.Graphics;

public class DrawData {

	private boolean isCircle=false,isRect=false,isLine;
	private int x,y,width,height;
	private Color color=new Color(0,0,0);
	public DrawData(int x,int y,int radius)
	{
		isCircle=true;
		this.x=x;
		this.y=y;
		this.width=x+2*radius;
		this.height=y+2*radius;
	}
	public DrawData(int x,int y,int width,int height,boolean isRect)
	{
		this.isRect=isRect;
		this.isLine=!isRect;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	public void draw(Graphics g)
	{
		g.setColor(color);
		if(isCircle)
		{
			g.drawOval(x, y, width, height);
		}
		else if(isRect)
		{
			g.drawRect(x, y, width, height);
		}
		else 
		{
			g.drawLine(x, y, width, height);
		}
	}
}
