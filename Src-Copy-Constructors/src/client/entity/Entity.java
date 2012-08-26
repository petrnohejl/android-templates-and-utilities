package com.example.client.entity;


public class Entity
{
	private boolean mBooleanVar;
	private int mIntVar;
	private long mLongVar;
	private String mStringVar;
	private Date mDateVar;
	private MyModel mMyModel;
	private ArrayList<String> mStrings;


	// empty constructor
	public Entity()
	{
	
	}
	
	
	// copy constructor
	public Entity(Entity copyModel)
	{
		mBooleanVar = copyModel.mBooleanVar;
		mIntVar = copyModel.mIntVar;
		mLongVar = copyModel.mLongVar;
		if(copyModel.mStringVar!=null) mStringVar = new String(copyModel.mStringVar);
		if(copyModel.mDateVar!=null) mDateVar = new Date(copyModel.mDateVar.getTime());
		if(copyModel.mMyModel!=null) mMyModel = new MyModel(copyModel.mMyModel);
		if(copyModel.mStrings!=null)
		{
			mStrings = new ArrayList<String>();
			Iterator<String> iterator = copyModel.mStrings.iterator();
			while(iterator.hasNext())
			{
				String s = iterator.next();
				mStrings.add(new String(s));
			}
		}
	}
	
	
	public boolean isBooleanVar()
	{
		return mBooleanVar;
	}
	public void setBooleanVar(boolean booleanVar)
	{
		mBooleanVar = booleanVar;
	}
	public int getIntVar()
	{
		return mIntVar;
	}
	public void setIntVar(int intVar)
	{
		mIntVar = intVar;
	}
	public long getLongVar()
	{
		return mLongVar;
	}
	public void setLongVar(long longVar)
	{
		mLongVar = longVar;
	}
	public String getStringVar()
	{
		return mStringVar;
	}
	public void setStringVar(String stringVar)
	{
		mStringVar = stringVar;
	}
	public Date getDateVar()
	{
		return mDateVar;
	}
	public void setDateVar(Date dateVar)
	{
		mDateVar = dateVar;
	}
	public MyModel getMyModel()
	{
		return mMyModel;
	}
	public void setMyModel(MyModel myModel)
	{
		mMyModel = myModel;
	}
	public ArrayList<String> getStrings()
	{
		return mStrings;
	}
	public void setStrings(ArrayList<String> strings)
	{
		mStrings = strings;
	}
}
