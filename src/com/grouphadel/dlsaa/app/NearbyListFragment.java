package com.grouphadel.dlsaa.app;

import java.util.List;

import com.google.android.gms.location.LocationListener;
import com.grouphadel.dlsaa.MainActivity;
import com.grouphadel.dlsaa.R;
import com.grouphadel.dlsaa.adapters.NearbyStoreAdapter;
import com.grouphadel.dlsaa.models.PartnerBusiness;
import com.grouphadel.dlsaa.storage.DBHelper;
import com.grouphadel.dlsaa.storage.PartnerBusinessDAO;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NearbyListFragment extends ListFragment implements
		LocationListener {
	public static final String TAG = "NearbyListFragment";
	public static final String KEY_SELECTED_CATEGORY = "selectedCategory";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NearbyStoreAdapter adapter;

		adapter = new NearbyStoreAdapter(this.getActivity());

		// TODO: Optimize!
		setListAdapter(adapter);
		refreshData();

		if (savedInstanceState != null) {
			String selectedCategory = savedInstanceState
					.getString(KEY_SELECTED_CATEGORY);
			adapter.setSelectedCategory(selectedCategory);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_nearby_list, null);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		NearbyStoreAdapter adapter = (NearbyStoreAdapter) this.getListAdapter();

		outState.putString(KEY_SELECTED_CATEGORY, adapter.getSelectedCategory());
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		MainActivity activity = (MainActivity) getActivity();
		activity.registerLocationListener(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		
		MainActivity activity = (MainActivity) getActivity();
		activity.unregisterLocationListener(this);
	}

	private void refreshData() {
		DBHelper dbHelper = DBHelper.getInstance(this.getActivity());
		PartnerBusinessDAO busDao = dbHelper.createBusinessDAO();
		List<PartnerBusiness> businesses = busDao.getAll();

		NearbyStoreAdapter adapter = (NearbyStoreAdapter) getListAdapter();
		if (adapter == null) {
			adapter = new NearbyStoreAdapter(this.getActivity());
			setListAdapter(adapter);
		}

		adapter.setData(businesses);
	}

	@Override
	public void onLocationChanged(Location location) {
		String msgFormat = "Updated location: %f, %f (accuracy %.1fm)";
		String message = String.format(msgFormat, location.getLatitude(),
				location.getLongitude(), location.getAccuracy());
		Log.d(TAG, message);
	}
}
