package com.cd.pokepraiser.db.util;

import com.cd.pokepraiser.R;


public class AttacksHelper {

	public static int getCategoryDrawableResource(int categoryDbVal){
		switch(categoryDbVal){
			case 0: return R.drawable.category_physical;
			case 1: return R.drawable.category_special;
			case 2: return R.drawable.category_other;
			default: return 0;
		}
	}
	
}
