package com.cd.pokepraiser.util;

import com.cd.pokepraiser.R;

public class TypeUtils {

	public static int getTypeDrawableId(final int theType){
		switch(theType){
			case 0: return R.drawable.normal;
			case 1: return R.drawable.fire;
			case 2: return R.drawable.water;
			case 3: return R.drawable.grass;
			case 4: return R.drawable.electric;
			case 5: return R.drawable.ice;
			case 6: return R.drawable.fighting;
			case 7: return R.drawable.poison;
			case 8: return R.drawable.ground;
			case 9: return R.drawable.flying;
			case 10: return R.drawable.psychic;
			case 11: return R.drawable.bug;
			case 12: return R.drawable.rock;
			case 13: return R.drawable.ghost;
			case 14: return R.drawable.dragon;
			case 15: return R.drawable.dark;
			case 16: return R.drawable.steel;
			case 17: return R.drawable.fairy;
			default: return 0;
		}
	}
	
}
