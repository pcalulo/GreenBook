package com.grouphadel.dlsaa.app;

import com.grouphadel.dlsaa.R;
import com.grouphadel.dlsaa.adapters.NearbyStoreAdapter;

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
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_nearby_list, null);
	}

}
