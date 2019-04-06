package com.amazonaws.samples;

import java.util.Date;

public class Appointment {

	private String desc;
	private Date startDate,endDate;
	public Appointment(String desc,Date startDate,Date endDate)
	{
		this.desc=desc;
		this.startDate=startDate;
		this.endDate=endDate;
	}
	public Appointment(String desc,Date startDate)
	{
		this.desc=desc;
		this.startDate=startDate;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public Date getEndDate()
	{
		return endDate;
	}
	public String getDesc()
	{
		return desc;
	}
	public void setStartDate(Date d)
	{
		startDate=d;
	}
	public void setEndDate(Date d)
	{
		endDate=d;
	}
}
