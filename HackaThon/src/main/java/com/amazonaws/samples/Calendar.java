package com.amazonaws.samples;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.amazonaws.services.dynamodbv2.document.Item;

public class Calendar extends Canvas{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public ArrayList<Day> days=new ArrayList<Day>();
	private Day bigDay;
	private DynamoIO db=new DynamoIO();
	public Calendar(int x,int y,int width,int height)
	{
		this.setBounds(x, y, width, height);
		refreshDays();
	}
	private int offset=60;
	private int width;
	public void refreshDays()
	{
		LocalDate date=LocalDate.now();
		String todayToString[]=date.toString().split(" ");
		//System.out.println(todayToString[0]);
		int dow=date.getDayOfWeek().getValue();
		int dom=date.getDayOfMonth();
		LocalDate fDom=LocalDate.now().minusDays((long)(dom+dow-6));
		System.out.println(this.getWidth());
		width=this.getWidth();
		for(int j=0;j<6;j++)
		{
			for(int k=0;k<7;k++)
			{
				
				days.add(new Day(k*(this.getWidth()/7),j*((this.getHeight()-offset)/6)+offset,this.getWidth()/7,(this.getHeight()-offset)/6,fDom));
				fDom=fDom.minusDays(-1);
			}
		}
	} 
	@Override
	public void paint(Graphics g)
	{
		
		for(int i=0;i<7;i++)
		{
			
			g.setColor(new Color(255,255,255));
			g.fillRect(i*(width/7), 30, (width/7), offset);
			g.setColor(new Color(0,0,0));
			g.drawRect(i*(width/7), 30, (width/7), offset);
			int dayOffset=50;
			switch(i)
			{
			case 0:
				g.drawString("Sunday",5+i*(width/7), dayOffset);
				break;
			case 1:
				g.drawString("Monday",5+ i*(width/7), dayOffset);
				break;
			case 2:
				g.drawString("Tuesday",5+ i*(width/7), dayOffset);
				break;
			case 3:
				g.drawString("Wednesday",5+ i*(width/7), dayOffset);
				break;
			case 4:
				g.drawString("Thursday",5+ i*(width/7), dayOffset);
				break;
			case 5:
				g.drawString("Friday",5+ i*(width/7), dayOffset);
				break;
			case 6:
				g.drawString("Saturday",5+ i*(width/7), dayOffset);
				break;
			}
		}
		for(Day day:days)
		{
			if(!day.isBig())
				day.draw(g);
		}
		if(getBigDay()!=null)
			getBigDay().draw(g);
		g.setColor(new Color(255,255,255));
		g.fillRect(180, 0, 180, 30);
		//System.out.println(days.get(days.size()/2).date.getMonth().toString());
		g.setColor(new Color(0,0,0));
		g.drawString(days.get(days.size()/2).date.getMonth().toString(), 250, 15);
		g.drawRect(180, 0, 180, 30);
		
	}
	public void setBigDay(Day d)
	{
			this.bigDay=d;
	}
	public Day getBigDay()
	{
		return bigDay;
	}
	public ArrayList<Day> getDays()
	{
		return days;
	}
	private String tableName="calendarTestTable";
	public void getApptByDay()
	{
			ArrayList<Item> dates=db.getAllCalendarItems(tableName);
			for(int i=0;i<dates.size();i++)
			{
				for(int j=0;j<days.size();j++)
				{
					//int dayEpoch = (int) (days.get(j).date.atStartOfDay(zoneId).toEpochSecond()/(3600*24));
					String eventDate[] = dates.get(i).get("userId").toString().split("-");//yyyy-MM-dd
					String eventDesc = dates.get(i).get("movieTitle").toString();
					String dayDate[]=days.get(j).date.toString().split("-");
					//System.out.println(dayDate[1]+"-"+dayDate[2]+" "+eventDate[1]+"-"+eventDate[2]);
					if(eventDate[1].equals(dayDate[1]) && eventDate[2].equals(dayDate[2]) )
					{
						if(days.get(j).appts.size()>0)
						{

							for(int k=0;k<days.get(j).appts.size();k++)
							{
								if(eventDesc.equals(days.get(j).appts.get(k).getDesc()))
								{
									
								}
								else
								{
									String sDate1=dates.get(i).get("eventDate").toString();//yyyy-MM-dd;
									Date date1;
									try {
										date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
										System.out.println(date1.toString());
										days.get(j).addAppt(new Appointment(eventDesc,date1));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}							
								}
									
							}
						}
						else if(dates.size()>0)
						{
							String sDate1=dates.get(i).get("userId").toString();//yyyy-MM-dd;
							Date date1;
							try {
								date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
								System.out.println(date1.toString());
								days.get(j).addAppt(new Appointment(eventDesc,date1));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
			}
		
		

	}
}
