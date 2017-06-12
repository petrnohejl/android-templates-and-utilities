package com.example.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ExampleEntity implements Parcelable
{
	public static final Parcelable.Creator<ExampleEntity> CREATOR = new Parcelable.Creator<ExampleEntity>()
	{
		public ExampleEntity createFromParcel(Parcel parcel)
		{
			return new ExampleEntity(parcel);
		}


		public ExampleEntity[] newArray(int size)
		{
			return new ExampleEntity[size];
		}
	};

	private boolean booleanVar;
	private int intVar;
	private long longVar;
	private String stringVar;
	private Date dateVar;
	private Calendar calendarVar;
	private MyEntity myEntity;
	private List<String> stringList;


	// empty constructor
	public ExampleEntity()
	{
	}


	// parcel constructor
	public ExampleEntity(Parcel parcel)
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
		parcel.writeLong(dateVar != null ? dateVar.getTime() : -1L);
		parcel.writeLong(calendarVar != null ? calendarVar.getTimeInMillis() : -1L);
		parcel.writeParcelable(myEntity, flags);
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
		if(dateVarLong != -1L) dateVar = new Date(dateVarLong);
		else dateVar = null;

		long calendarVarLong = parcel.readLong();
		if(calendarVarLong != -1L)
		{
			calendarVar = Calendar.getInstance();
			calendarVar.setTimeInMillis(calendarVarLong);
		}
		else
		{
			calendarVar = null;
		}

		myEntity = parcel.readParcelable(MyEntity.class.getClassLoader());
		parcel.readStringList(stringList);
	}
}
