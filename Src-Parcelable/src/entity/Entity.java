package com.example.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;


public class Entity implements Parcelable
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
	public Entity()
	{
	
	}
	
	
	// parcel constructor
	public Entity(Parcel parcel)
	{
		readFromParcel(parcel);
	}
	
	
	@Override
	public int describeContents()
	{
		return 0;
	}


	@Override
	public void writeToParcel(Parcel parcel, int flags)
	{
		// order is important
		parcel.writeByte((byte) (booleanVar ? 1 : 0));
		parcel.writeInt(intVar);
		parcel.writeLong(longVar);
		parcel.writeString(stringVar);
		parcel.writeLong(dateVar!=null ? dateVar.getTime() : -1l);
		parcel.writeLong(calendarVar!=null ? calendarVar.getTimeInMillis() : -1l);
		parcel.writeParcelable(myModel, flags);
		parcel.writeStringList(stringList);
	}


	private void readFromParcel(Parcel parcel)
	{
		// order is important
		booleanVar = parcel.readByte() == 1;
		intVar = parcel.readInt();
		longVar = parcel.readLong();
		stringVar = parcel.readString();
		
		long dateVarLong = parcel.readLong();
		if(dateVarLong!=-1l) dateVar = new Date(dateVarLong);
		else dateVar = null;
		
		long calendarVarLong = parcel.readLong();
		if(calendarVarLong!=-1l)
		{
			calendarVar = Calendar.getInstance();
			calendarVar.setTimeInMillis(calendarVarLong);
		}
		else
		{
			calendarVar = null;
		}
		
		myModel = parcel.readParcelable(MyModel.class.getClassLoader());
		parcel.readStringList(stringList);
	}
	
	
	public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>()
	{
		public Entity createFromParcel(Parcel parcel)
		{
			return new Entity(parcel);
		}


		public Entity[] newArray(int size)
		{
			return new Entity[size];
		}
	};
}
