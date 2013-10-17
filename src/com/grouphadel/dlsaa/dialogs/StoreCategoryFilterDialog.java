package com.grouphadel.dlsaa.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class StoreCategoryFilterDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String[] categories = new String[] { "All Stores",
				"Clothing and Footwear", "Culture and Arts", "Flower Shops",
				"Gadgetry", "Health and Wellness", "Hotels and Resorts",
				"Hobbies and Household", "Motoring", "Photography",
				"Optical Shops", "Restaurants and Bars", "Travels and Tours" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Select category");
		builder.setItems(categories, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		
		return builder.create();
	}
}
