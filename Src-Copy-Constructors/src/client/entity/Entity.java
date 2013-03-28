package com.example.client.entity;


public class Entity
{
	private boolean booleanVar;
	private int intVar;
	private long longVar;
	private String stringVar;
	private Date dateVar;
	private Calendar calendarVar;
	private MyModel myModel;
	private ArrayList<String> strings;


	// empty constructor
	public Entity()
	{
	
	}
	
	
	// copy constructor
	public Entity(Entity copyModel)
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
		if(copyModel.strings!=null)
		{
			strings = new ArrayList<String>();
			Iterator<String> iterator = copyModel.strings.iterator();
			while(iterator.hasNext())
			{
				String s = iterator.next();
				strings.add(new String(s));
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
	public ArrayList<String> getStrings()
	{
		return strings;
	}
	public void setStrings(ArrayList<String> strings)
	{
		this.strings = strings;
	}
}
