package com.cd.pokepraiser.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.actionbarsherlock.app.SherlockFragment;
import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;

/**
 * Created by Cory on 7/5/2014.
 */
public class SettingsFragment extends SherlockFragment {
    public static final String TAG					= "settings";

    private ViewGroup mParentView;

    private Button mCancelButton;
    private Button mSaveButton;
    private CheckBox mFontCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);

        mParentView     = (ViewGroup) inflater.inflate(R.layout.settings_screen, container, false);

        mSaveButton     = (Button) mParentView.findViewById(R.id.saveButton);
        mCancelButton   = (Button) mParentView.findViewById(R.id.cancelButton);
        mFontCheckBox   = (CheckBox) mParentView.findViewById(R.id.fontCheckbox);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        mFontCheckBox.setChecked(prefs.getBoolean(getString(R.string.preference_font), false));

        //Apply typeface
        ((PokepraiserApplication)getActivity().getApplication()).overrideFonts(mParentView);

        return mParentView;
    }

    public void saveSettings(){
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        final boolean isDefaultFontPreferred = mFontCheckBox.isChecked();

        editor.putBoolean(getString(R.string.preference_font), isDefaultFontPreferred);
        editor.commit();

        ((PokepraiserApplication)getActivity().getApplication()).resetCachedPrefs();
    }
}
