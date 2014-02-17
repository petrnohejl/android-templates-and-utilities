package com.example.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


public class ExampleEntity
{
	private boolean booleanVar;
	private int intVar;
	private long longVar;
	private String stringVar;
	private Date dateVar;
	private Calendar calendarVar;
	private MyModel myModel;
	private ArrayList<String> stringList;


	// empty constructor
	public ExampleEntity()
	{
	
	}
	
	
	// copy constructor
	public ExampleEntity(ExampleEntity copyModel)
	{
		booleanVar = copyModel.booleanVar;
		intVar = copyModel.intVar;
		longVar = copyModel.longVar;
		if(copyModel.stringVar!=null) stringVar = new String(copyModel.stringVar);
		if(copyModel.dateVar!=null) dateVar = new Date(copyModel.dateVar.getTime());
		if(copyModel.calendarVar!=null)
		{
			calendarVar = Calendar.getInstance();
			calendarVar.setTime(copyModel.calendarVar.getTime());
		}
		if(copyModel.myModel!=null) myModel = new MyModel(copyModel.myModel);
		if(copyModel.stringList!=null)
		{
			stringList = new ArrayList<String>();
			Iterator<String> iterator = copyModel.stringList.iterator();
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
	public ArrayList<String> getStringList()
	{
		return stringList;
	}
	public void setStringList(ArrayList<String> stringList)
	{
		this.stringList = stringList;
	}
}
