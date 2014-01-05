package com.cd.pokepraiser.filter;

import android.text.InputFilter;
import android.text.Spanned;

public class EVFilter implements InputFilter {

	private static final int MIN = 0;
	private static final int MAX = 252;	
	
	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(input))
                return null;
        } catch (NumberFormatException nfe) { }     
        return "";
	}

	private boolean isInRange(int newVal){
		return newVal >= MIN && newVal <= MAX;
	}
	
}
