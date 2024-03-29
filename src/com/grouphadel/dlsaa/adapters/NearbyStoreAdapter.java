package com.grouphadel.dlsaa.adapters;

import java.util.ArrayList;
import java.util.List;

import com.grouphadel.dlsaa.R;
import com.grouphadel.dlsaa.dialogs.StoreCategoryFilterDialog;
import com.grouphadel.dlsaa.models.PartnerBusiness;
import com.grouphadel.dlsaa.storage.DBHelper;
import com.grouphadel.dlsaa.storage.PartnerBusinessDAO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

public class NearbyStoreAdapter implements ListAdapter,
		StoreCategoryFilterDialog.CategoryFilterListener {
	private static final String TAG = NearbyStoreAdapter.class.getSimpleName();

	private static final int NUM_VIEW_TYPES = 2;
	private static final int VIEW_TYPE_FILTER = 0;
	private static final int VIEW_TYPE_STORE = 1;

	private FragmentActivity mActivity;
	private List<DataSetObserver> mObservers;
	private List<PartnerBusiness> mBusinesses;

	private String mCurrentFilter;

	public NearbyStoreAdapter(FragmentActivity activity) {
		mObservers = new ArrayList<DataSetObserver>();
		mBusinesses = new ArrayList<PartnerBusiness>();
		mActivity = activity;

		mCurrentFilter = null;
	}
	
	public String getSelectedCategory() {
		return mCurrentFilter;
	}
	
	public void setSelectedCategory(String category) {
		mCurrentFilter = category;
		
		DBHelper dbHelper = DBHelper.getInstance(mActivity);
		PartnerBusinessDAO businessDao = dbHelper.createBusinessDAO();
		
		if (category == null) {
			this.setData(businessDao.getAll());
		} else {
			this.setData(businessDao.getAllNearby(mCurrentFilter));
		}
	}

	public void setData(List<PartnerBusiness> businesses) {
		mBusinesses = businesses;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mBusinesses.size() + 1;
	}

	@Override
	public Object getItem(int index) {
		if (index - 1 >= mBusinesses.size())
			throw new ArrayIndexOutOfBoundsException("HOY! Bad index " + index);

		// Adjust for the filter item, to get the correct PartnerBusiness object
		return mBusinesses.get(index - 1);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		switch (position) {
		case 0:
			return VIEW_TYPE_FILTER;
		default:
			return VIEW_TYPE_STORE;
		}
	}

	/**
	 * A helper function for inflating the correct view for a given position.
	 * 
	 * @param position
	 *            The position to inflate a view for
	 * @return A view appropriate for the specified position in the list
	 */
	private View inflateViewForPosition(int position) {
		View view = null;
		int viewType; // let getItemViewType figure out the view to create

		LayoutInflater inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		viewType = getItemViewType(position);

		switch (viewType) {
		case VIEW_TYPE_FILTER:
			view = inflater.inflate(R.layout.view_filter_card, null);
			break;
		case VIEW_TYPE_STORE:
			view = inflater.inflate(R.layout.view_store_card, null);
			break;
		}

		return view;
	}

	private void configureFilterCard(View view, int position) {
		TextView filterCategoryLabel = (TextView) view
				.findViewById(R.id.filter_category_label);
		ImageButton filterButton = (ImageButton) view
				.findViewById(R.id.filter_button);
		
		if (mCurrentFilter == null) {
			filterCategoryLabel.setText("All Stores");
		} else {
			filterCategoryLabel.setText(mCurrentFilter);
		}

		filterButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				StoreCategoryFilterDialog dialog = new StoreCategoryFilterDialog();
				dialog.setCategoryFilterListener(NearbyStoreAdapter.this);

				dialog.show(mActivity.getSupportFragmentManager(), "categories");
			}
		});
	}

	private void configureStoreCard(View view, int position) {
		final PartnerBusiness store = (PartnerBusiness) getItem(position);
		TextView storeNameText = (TextView) view.findViewById(R.id.store_name);
		TextView discountText = (TextView) view
				.findViewById(R.id.discount_text);
		Button button = (Button) view.findViewById(R.id.store_navigate_button);

		storeNameText.setText(store.getName());
		discountText.setText("Discount: " + store.getDiscountInfo());

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				double latitude = 14.566068;
				double longitude = 120.992761;
				String label = store.getName();
				String uriBegin = "geo:" + latitude + "," + longitude;
				String query = latitude + "," + longitude;
				String encodedQuery = Uri.encode(query);
				String uriString = uriBegin + "?q=" + encodedQuery;
				Uri uri = Uri.parse(uriString);

				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				mActivity.startActivity(intent);
			}
		});
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		// Create or recycle a view
		if (convertView != null) {
			view = convertView;
		} else {
			view = inflateViewForPosition(position);
		}

		// Set view contents appropriately
		if (getItemViewType(position) == VIEW_TYPE_STORE) {
			configureStoreCard(view, position);
		} else {
			configureFilterCard(view, position);
		}

		return view;
	}

	@Override
	public int getViewTypeCount() {
		return NUM_VIEW_TYPES;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		this.mObservers.add(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		this.mObservers.remove(observer);
	}

	public void notifyDataSetChanged() {
		for (DataSetObserver observer : mObservers) {
			observer.onChanged();
		}
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onCategoryFilterSet(String category) {
		Log.d(TAG, "onCategoryFilterSet: " + category);
		setSelectedCategory(category);
	}

}
