package com.grouphadel.dlsaa.dialogs;

import com.grouphadel.dlsaa.models.PartnerBusiness;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class StoreCategoryFilterDialog extends DialogFragment {
	
	private CategoryFilterListener mFilterListener;
	
	public interface CategoryFilterListener {
		public void onCategoryFilterSet(String category);
	}
	
	public void setCategoryFilterListener(CategoryFilterListener filterListener) {
		mFilterListener = filterListener;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String[] categories = new String[] { "All Stores",
				"Clothing and Footwear", "Culture and Arts", "Flower Shops",
				"Gadgetry", "Health and Wellness", "Hotels and Resorts",
				"Hobbies and Household", "Motoring", "Photography",
				"Optical Shops", "Restaurants and Bars", "Travels and Tours" };

		final String[] encodedCategories = new String[] {
				PartnerBusiness.CATEGORY_CLOTHING,
				PartnerBusiness.CATEGORY_CULTURE,
				PartnerBusiness.CATEGORY_FLOWER,
				PartnerBusiness.CATEGORY_GADGETRY,
				PartnerBusiness.CATEGORY_HEALTH,
				PartnerBusiness.CATEGORY_HOTEL,
				PartnerBusiness.CATEGORY_HOBBIES,
				PartnerBusiness.CATEGORY_MOTORING,
				PartnerBusiness.CATEGORY_PHOTOGRAPHY,
				PartnerBusiness.CATEGORY_OPTICAL,
				PartnerBusiness.CATEGORY_RESTAURANT,
				PartnerBusiness.CATEGORY_TRAVEL };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Select category");
		builder.setItems(categories, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int categoryIndex = which - 1;
				if (categoryIndex < 0) {
					mFilterListener.onCategoryFilterSet(null);
				} else {
					mFilterListener.onCategoryFilterSet(encodedCategories[categoryIndex]);
				}
			}
		});

		return builder.create();
	}
}
