package com.grouphadel.dlsaa;

import com.grouphadel.dlsaa.storage.DBHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		Button loadDataButton = (Button) findViewById(R.id.settings_load_data_button);
		loadDataButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onLoadDataButtonClick();
			}
		});
	}
	
	private void onLoadDataButtonClick() {
		DBHelper dbHelper = DBHelper.getInstance(this);
		dbHelper.loadPackagedBusinessData(dbHelper.getWritableDatabase());
	}

}
