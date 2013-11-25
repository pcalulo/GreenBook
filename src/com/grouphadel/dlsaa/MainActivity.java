package com.grouphadel.dlsaa;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.grouphadel.dlsaa.app.NearbyListFragment;
import com.grouphadel.dlsaa.app.PromotionsFragment;

public class MainActivity extends FragmentActivity {
	private String[] mSectionTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_nav_drawer);

		mSectionTitles = getResources().getStringArray(
				R.array.nav_drawer_options);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.list_item_nav_drawer, mSectionTitles));
		mDrawerList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectItem(position);
					}
				});
		
		selectItem(0);
	}

	private void selectItem(int position) {
		Fragment fragment;
		switch (position) {
		case 0:
			fragment = new NearbyListFragment();
			break;
		case 1:
			fragment = new PromotionsFragment();
			break;
		default:
			fragment = new Fragment();
		}

		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
		
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
