package com.cd.pokepraiser.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.util.ExtrasConstants;
import com.cd.pokepraiser.util.StatUtils;

public class MemberStatsTable extends Fragment {

	private int mEvs;
	private int mBaseStat;
	private int mLevel;
	private boolean mIsHp;
	
	private TextView mNegativeRange;
	private TextView mNeutralRange;
	private TextView mPositiveRange;
	
	public MemberStatsTable() {}

	@Override	
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		Bundle bundle;
		
		if(savedInstanceState != null){
			bundle = savedInstanceState;
		}else{
			bundle = getArguments();
		}		
		
		mEvs 		= bundle.getInt(ExtrasConstants.EVS);
		mBaseStat 	= bundle.getInt(ExtrasConstants.BASE_STAT);
		mIsHp		= bundle.getBoolean(ExtrasConstants.IS_HP);
		mLevel		= bundle.getInt(ExtrasConstants.LEVEL);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		ViewGroup parentContainer = (ViewGroup) inflater.inflate(R.layout.member_stats_table, container, false);
		
		mNegativeRange = (TextView) parentContainer.findViewById(R.id.negativeRange);
		mNeutralRange = (TextView) parentContainer.findViewById(R.id.neutralRange);
		mPositiveRange = (TextView) parentContainer.findViewById(R.id.positiveRange);
		
		refreshRanges();
		
		((PokepraiserApplication)getActivity().getApplication()).overrideFonts(parentContainer);
		
		return parentContainer;
	}
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ExtrasConstants.EVS, mEvs);
        outState.putInt(ExtrasConstants.BASE_STAT, mBaseStat);
        outState.putBoolean(ExtrasConstants.IS_HP, mIsHp);
        outState.putInt(ExtrasConstants.LEVEL, mLevel);
    } 

	public void refreshRanges(){
		//0 IVs and 31 IVs
		final int lowEnd = mIsHp ? 
							StatUtils.calculateHPAtLevel(mBaseStat, mEvs, 0, mLevel) :
							StatUtils.calculateStatAtLevel(mBaseStat, mEvs, 0, mLevel);
							
		final int highEnd = mIsHp ? 
							StatUtils.calculateHPAtLevel(mBaseStat, mEvs, 31, mLevel) :
							StatUtils.calculateStatAtLevel(mBaseStat, mEvs, 31, mLevel);
							
		if(mIsHp){
			final String theRange = lowEnd + " - " + highEnd;
			
			mNegativeRange.setText(theRange);
			mNeutralRange.setText(theRange);
			mPositiveRange.setText(theRange);
		}else{
			final String negativeRange = (int)Math.floor(lowEnd * 0.90f) + " - " + (int)Math.floor(highEnd * 0.90f);			
			final String neutralRange = lowEnd + " - " + highEnd;
			final String positiveRange = (int)Math.floor(lowEnd * 1.10f) + " - " + (int)Math.floor(highEnd * 1.10f);			
			
			mNegativeRange.setText(negativeRange);
			mNeutralRange.setText(neutralRange);
			mPositiveRange.setText(positiveRange);			
		}
	}
	
	public int getEvs() {
		return mEvs;
	}

	public void setEvs(int mEvs) {
		this.mEvs = mEvs;
	}
	
}
