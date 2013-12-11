package com.grouphadel.dlsaa;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.LocationClient;
import com.grouphadel.dlsaa.app.ChapterLocatorFragment;
import com.grouphadel.dlsaa.app.NearbyListFragment;
import com.grouphadel.dlsaa.app.PetronCardRegistrationFragment;

public class MainActivity extends GooglePlayServicesEnabledActivity {
	private static String SELECTED_SCREEN_INDEX = "selectedScreenIndex";
	private static String TAG = MainActivity.class.getSimpleName();

	// Navigation Drawer stuff
	private String[] mSectionTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private int mSelectedScreenIndex = 0;

	private void initializeNavigationDrawer(Bundle savedInstanceState) {
		mSectionTitles = getResources().getStringArray(
				R.array.nav_drawer_options);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {

			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {

			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

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

		// If the app is just starting up, select the Nearby Stores screen
		if (savedInstanceState == null) {
			selectItem(mSelectedScreenIndex);
		} else {
			// If we have a savedInstanceState, use the selected screen index
			// from it
			mSelectedScreenIndex = savedInstanceState
					.getInt(SELECTED_SCREEN_INDEX);
			// and set the action bar title appropriately
			setTitleByScreenIndex(mSelectedScreenIndex);

			// The already-present fragment is preserved, so we don't have to
			// reset it.
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_nav_drawer);

		initializeNavigationDrawer(savedInstanceState);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		mDrawerToggle.syncState();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the index of the selected screen
		outState.putInt(SELECTED_SCREEN_INDEX, mSelectedScreenIndex);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void selectItem(int position) {
		Fragment fragment;
		String title;

		// Select and set the fragment to display
		switch (position) {
		case 0:
			fragment = new NearbyListFragment();
			break;
		case 1:
			fragment = new ChapterLocatorFragment();
			break;
		case 2:
			fragment = new PetronCardRegistrationFragment();
			break;
		default:
			fragment = new Fragment();
		}

		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.content_frame, fragment).commit();

		mDrawerLayout.closeDrawer(mDrawerList);
		mSelectedScreenIndex = position;
		setTitleByScreenIndex(position);
	}

	private void setTitleByScreenIndex(int screenIndex) {
		String title = getResources()
				.getStringArray(R.array.nav_drawer_options)[screenIndex];
		getActionBar().setTitle(title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// insert other options handlers here
		switch (item.getItemId()) {
		case R.id.action_settings:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		String msgFormat = "Updated location: %f, %f (accuracy %.1fm)";
		String message = String.format(msgFormat, location.getLatitude(),
				location.getLongitude(), location.getAccuracy());
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		Log.d(TAG, message);
	}
}
