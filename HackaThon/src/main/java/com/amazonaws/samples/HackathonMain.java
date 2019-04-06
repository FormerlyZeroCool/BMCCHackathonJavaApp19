package com.amazonaws.samples;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HackathonMain {
	private static Calendar cal;
	private static ToDo toDo;
private static String amznID="amzn1.ask.account.AFWCZZPBSJGGE5BVRYZA4I27A6G4VB75BEWYF4QCEOGFC73FPA3UNMJSBKEY5EENEYTJLBCBRB6SESJNW7J55EYVO75QB76NB2DQELNH53WEPQNKDVXUWIUKITLFB26OSAZC45XPJUYKLZIDZYIIAOOJBUMMGLTKM62ZWPW63TPNIPCGSHIO5ME2ZOZZ3SQVBOKZFUPE2GJQLRY";
	public static void main(String[] args) {
		
		JFrame frame=new JFrame("Main Screen");
		frame.setVisible(true);
		frame.setSize(new Dimension(1200,685));
		 cal=new Calendar(0,10,540,600);
		cal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
				ArrayList<Day> days=cal.getDays();
				if(cal.getBigDay()!=null)
				{
					cal.getBigDay().setBig(false);
					cal.setBigDay(null);
				}
				else
				{
					boolean found=false;
					for(int i=0;i<days.size() && !found;i++)
					{
						if(days.get(i).getX()<=x && days.get(i).getY()<=y && days.get(i).getHeight()+days.get(i).getY()>=y && days.get(i).getWidth()+days.get(i).getX()>=x )
						{
							found=true;
							cal.setBigDay(days.get(i));
							cal.getBigDay().setBig(true);
							System.out.println("Hello I am looking to make someone big "+x+","+y+" : "+days.get(i).getX()+","+days.get(i).getY());
						}
					}
					cal.getApptByDay();
					
				}
				
			}
		});
		toDo=new ToDo(600,10,200,490);
		CanvasApp cApp=new CanvasApp(700,10,100,100);
		cApp.setBounds(700, 10, 100, 100);
		frame.getContentPane().add(toDo);
		frame.getContentPane().add(cal);
		//frame.getContentPane().add(cApp);
		AmazonDynamoDBSample db=new AmazonDynamoDBSample();
		//db.createTable();
		db.getAllItems("toDoList");
		Thread updateData=new Thread(){
			@Override
			public void run()
			{
				while (true)
				{
					toDo.refreshList();
					cal.getApptByDay();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		updateData.start();
		//DynamoIO io=new DynamoIO();
		//io.getAllTestItems();
		while(true)
		{
			try {
				Thread.sleep(32);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cal.repaint();
			toDo.repaint();
			cApp.repaint();
		}
	}

}
