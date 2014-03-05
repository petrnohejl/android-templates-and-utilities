package com.example.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.R;
import com.example.database.DatabaseCallListener;
import com.example.database.DatabaseCallManager;
import com.example.database.DatabaseCallTask;
import com.example.database.data.Data;
import com.example.database.data.ProductCreateData;
import com.example.database.data.ProductDeleteAllData;
import com.example.database.data.ProductReadAllData;
import com.example.database.data.ProductUpdateData;
import com.example.database.model.ProductModel;
import com.example.database.query.ProductCreateQuery;
import com.example.database.query.ProductDeleteAllQuery;
import com.example.database.query.ProductReadAllQuery;
import com.example.database.query.ProductUpdateQuery;
import com.example.task.TaskFragment;
import com.example.utility.Logcat;


public class ExampleFragment extends TaskFragment implements DatabaseCallListener
{
	private View mRootView;
	private DatabaseCallManager mDatabaseCallManager = new DatabaseCallManager();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mRootView = inflater.inflate(R.layout.fragment_example, container, false);
		return mRootView;
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		// cancel async tasks
		mDatabaseCallManager.cancelAllTasks();
	}
	
	
	@Override
	public void onDatabaseCallRespond(final DatabaseCallTask task, final Data data)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView==null) return; // view was destroyed
				
				if(task.getQuery().getClass().equals(ProductCreateQuery.class))
				{
					Logcat.d("Fragment.onDatabaseCallRespond(ProductCreateQuery)");
					
					// data
					ProductCreateData productCreateData = (ProductCreateData) data;
					ProductModel productModel = productCreateData.getProductModel();
					
					// TODO
				}
				else if(task.getQuery().getClass().equals(ProductReadAllQuery.class))
				{
					Logcat.d("Fragment.onDatabaseCallRespond(ProductReadAllQuery)");
					
					// data
					ProductReadAllData productReadAllData = (ProductReadAllData) data;
					List<ProductModel> productModelList = productReadAllData.getProductModelList();
					
					// TODO
				}
				else if(task.getQuery().getClass().equals(ProductUpdateQuery.class))
				{
					Logcat.d("Fragment.onDatabaseCallRespond(ProductUpdateQuery)");
					
					// data
					ProductUpdateData productUpdateData = (ProductUpdateData) data;
					ProductModel productModel = productUpdateData.getProductModel();
					
					// TODO
				}
				else if(task.getQuery().getClass().equals(ProductDeleteAllQuery.class))
				{
					Logcat.d("Fragment.onDatabaseCallRespond(ProductDeleteAllQuery)");
					
					// data
					ProductDeleteAllData productDeleteAllData = (ProductDeleteAllData) data;
					
					// TODO
				}
				
				// finish query
				mDatabaseCallManager.finishTask(task);
				
				// hide progress in action bar
				if(mDatabaseCallManager.getTasksCount()==0) showActionBarProgress(false);
			}
		});
	}


	@Override
	public void onDatabaseCallFail(final DatabaseCallTask task, final Exception exception)
	{
		runTaskCallback(new Runnable()
		{
			public void run()
			{
				if(mRootView==null) return; // view was destroyed
				
				if(task.getQuery().getClass().equals(ProductCreateQuery.class))
				{
					Logcat.d("Fragment.onDatabaseCallFail(ProductCreateQuery): " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
				}
				else if(task.getQuery().getClass().equals(ProductReadAllQuery.class))
				{
					Logcat.d("Fragment.onDatabaseCallFail(ProductReadAllQuery): " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
				}
				else if(task.getQuery().getClass().equals(ProductUpdateQuery.class))
				{
					Logcat.d("Fragment.onDatabaseCallFail(ProductUpdateQuery): " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
				}
				else if(task.getQuery().getClass().equals(ProductDeleteAllQuery.class))
				{
					Logcat.d("Fragment.onDatabaseCallFail(ProductDeleteAllQuery): " + exception.getClass().getSimpleName() + " / " + exception.getMessage());
				}
				
				// finish query
				mDatabaseCallManager.finishTask(task);
				
				// hide progress in action bar
				if(mDatabaseCallManager.getTasksCount()==0) showActionBarProgress(false);
			}
		});
	}
	
	
	private void createProduct(String name, int quantity, long timestamp, double price)
	{
		// show progress in action bar
		showActionBarProgress(true);
		
		// run async task
		ProductCreateQuery query = new ProductCreateQuery(name, quantity, timestamp, price);
		mDatabaseCallManager.executeTask(query, this);
	}
	
	
	private void readAllProducts()
	{
		// show progress in action bar
		showActionBarProgress(true);
		
		// run async task
		ProductReadAllQuery query = new ProductReadAllQuery();
		mDatabaseCallManager.executeTask(query, this);
	}
	
	
	private void updateProduct(long id, String name, int quantity, long timestamp, double price)
	{
		// show progress in action bar
		showActionBarProgress(true);
		
		// run async task
		ProductUpdateQuery query = new ProductUpdateQuery(id, name, quantity, timestamp, price);
		mDatabaseCallManager.executeTask(query, this);
	}
	
	
	private void deleteAllProducts()
	{
		// show progress in action bar
		showActionBarProgress(true);
		
		// run async task
		ProductDeleteAllQuery query = new ProductDeleteAllQuery();
		mDatabaseCallManager.executeTask(query, this);
	}
}
