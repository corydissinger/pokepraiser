package com.cd.pokepraiser.util;

import com.cd.pokepraiser.R;

public class StatUtils {

	private static final float MAX_RELEVANT_STAT_VALUE = 120.0f; //arceus??? many pokes have > 120 in a stat, or close to it.
	
	public static int getRoughPercentile(int theStatValue){
		if(theStatValue > MAX_RELEVANT_STAT_VALUE){
			return 100;
		}else{
			return (int)((theStatValue / MAX_RELEVANT_STAT_VALUE) * 100.0f);
		}
	}
	
	public static int getProgressColor(int statPercentile){
		if(statPercentile > 90){
			return R.drawable.progress_green;			
		}else if(statPercentile > 70){
			return R.drawable.progress_light_green;			
		}else if(statPercentile > 30){
			return R.drawable.progress_yellow;			
		}else if(statPercentile > 10){
			return R.drawable.progress_light_red;			
		}else{
			return R.drawable.progress_red;
		}
	}

	public static int calculateHPAtLevel(int baseStat, int evs, int ivs, int level){
		return (((ivs + (2 * baseStat) + (evs / 4) + 100) * level) / 100) + 10;
	}	
	
	//Nature is applied after this formula, see http://bulbapedia.bulbagarden.net/wiki/Stats#Permanent_stats
	public static int calculateStatAtLevel(int baseStat, int evs, int ivs, int level){
		return (((ivs + (2 * baseStat) + (evs / 4)) * level) / 100) + 5;
	}
}
