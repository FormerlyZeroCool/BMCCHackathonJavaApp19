package com.amazonaws.samples;
import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Day {
	private int x,y,height,width;
	public LocalDate date;
	private int day;
	private boolean isBig=false;
	public ArrayList<Appointment> appts;
	private Color black=new Color(0,0,0),white=new Color(255,255,255);
	public Day(int x,int y,int width,int height,LocalDate date)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.date=date;
		this.day=date.getDayOfMonth();
		this.appts=new ArrayList<Appointment>();
	}
	public void addAppt(Appointment a)
	{
		appts.add(a);
	}
	public void clearAllAppt()
	{
		appts.clear();
	}
	public void draw(Graphics g)
	{
		g.setColor(white);
		g.fillRect(x, y, width, height);
		g.setColor(black);
		g.drawRect(x, y, width, height);
		g.drawRect(x+2, y+2, width-4, height-4);
		g.drawString(day+"", x+2, y+15);
		
		for(int i=0;i<appts.size();i++)
		{
			if(appts.get(i).getDesc().length()<9)
			g.drawString(appts.get(i).getDesc(), x+4, y+30+(i*20));
			else
				g.drawString(appts.get(i).getDesc().substring(0, 9), x+4, y+30+(i*20));
		}
		if(isBig)
		{
			g.setColor(white);
			g.fillRect(150, 100, 160, 300);
			g.setColor(black);
			g.drawRect(150,100,160,30);
			g.drawRect(150,100,160,300);
			String sdate[]=date.toString().split("-");
			String desc[];
			
			g.drawString(day+"", 165, 115);
			
			for(int i=0;i<appts.size();i++)
			{
				desc=appts.get(i).getDesc().toString().split("(?<=\\G.{18})");
				int x=0;
				while(x<desc.length)
				{
					g.drawString(desc[x], 160,x*15+ 165+i*(20));
					//g.drawString(appts.get(i).getDesc(), 160, x*15+165+i*20);
					x++;
				}
			}
		}
	}
	public int getDayInt()
	{
		return day;
	}
	public void setBig(boolean isBig)
	{
		this.isBig=isBig;
	}
	public boolean isBig()
	{
		return isBig;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width;
	}
}
