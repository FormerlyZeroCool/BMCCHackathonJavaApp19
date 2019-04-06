package com.amazonaws.samples;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class CanvasApp extends Canvas{

	private ArrayList<DrawData> drawings=new ArrayList<DrawData>();
	public CanvasApp(int x,int y,int width,int height)
	{
		this.setBounds(x, y, width, height);
		refreshList();
	}
	@Override
	public void paint(Graphics g)
	{
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for(DrawData data: drawings)
		{
			data.draw(g);
		}
	}
	public void refreshList()
	{
		drawings.add(new DrawData(0,0,100,100,false));
		drawings.add(new DrawData(0,0,100,100,true));
	}
}
