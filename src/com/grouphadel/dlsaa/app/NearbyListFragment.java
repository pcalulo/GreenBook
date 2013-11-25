package com.grouphadel.dlsaa.app;

import java.util.List;

import com.grouphadel.dlsaa.R;
import com.grouphadel.dlsaa.adapters.NearbyStoreAdapter;
import com.grouphadel.dlsaa.models.PartnerBusiness;
import com.grouphadel.dlsaa.storage.DBHelper;
import com.grouphadel.dlsaa.storage.PartnerBusinessDAO;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NearbyListFragment extends ListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new NearbyStoreAdapter(this.getActivity()));
		refreshData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_nearby_list, null);
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
}
