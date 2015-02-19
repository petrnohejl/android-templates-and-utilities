package com.example.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class ExampleEntity
{
	private boolean booleanVar;
	private int intVar;
	private long longVar;
	private String stringVar;
	private Date dateVar;
	private Calendar calendarVar;
	private MyModel myModel;
	private List<String> stringList;


	// empty constructor
	public ExampleEntity()
	{
	
	}
	
	
	// copy constructor
	public ExampleEntity(ExampleEntity origin)
	{
		booleanVar = origin.booleanVar;
		intVar = origin.intVar;
		longVar = origin.longVar;
		if(origin.stringVar!=null) stringVar = new String(origin.stringVar);
		if(origin.dateVar!=null) dateVar = new Date(origin.dateVar.getTime());
		if(origin.calendarVar!=null)
		{
			calendarVar = Calendar.getInstance();
			calendarVar.setTime(origin.calendarVar.getTime());
		}
		if(origin.myModel!=null) myModel = new MyModel(origin.myModel);
		if(origin.stringList!=null)
		{
			stringList = new ArrayList<>();
			Iterator<String> iterator = origin.stringList.iterator();
			while(iterator.hasNext())
			{
				String s = iterator.next();
				stringList.add(new String(s));
			}
		}
	}
	
	
	public boolean isBooleanVar()
	{
		return booleanVar;
	}
	public void setBooleanVar(boolean booleanVar)
	{
		this.booleanVar = booleanVar;
	}
	public int getIntVar()
	{
		return intVar;
	}
	public void setIntVar(int intVar)
	{
		this.intVar = intVar;
	}
	public long getLongVar()
	{
		return longVar;
	}
	public void setLongVar(long longVar)
	{
		this.longVar = longVar;
	}
	public String getStringVar()
	{
		return stringVar;
	}
	public void setStringVar(String stringVar)
	{
		this.stringVar = stringVar;
	}
	public Date getDateVar()
	{
		return dateVar;
	}
	public void setDateVar(Date dateVar)
	{
		this.dateVar = dateVar;
	}
	public Calendar getCalendarVar()
	{
		return calendarVar;
	}
	public void setCalendarVar(Calendar calendarVar)
	{
		this.calendarVar = calendarVar;
	}
	public MyModel getMyModel()
	{
		return myModel;
	}
	public void setMyModel(MyModel myModel)
	{
		this.myModel = myModel;
	}
	public List<String> getStringList()
	{
		return stringList;
	}
	public void setStringList(List<String> stringList)
	{
		this.stringList = stringList;
	}
}
