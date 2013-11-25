package com.cd.pokepraiser.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.AttackInfoArrayAdapter;
import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.db.dao.AttacksDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class AttacksListActivity extends PokepraiserActivity {

	private AttacksDataSource attacksDataSource;
	
	private AttackInfoArrayAdapter adapter;
	
	private EditText attackSearch;
	
	private ArrayList<AttackInfo> theAttacks;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attacks_list_screen);
        
        attacksDataSource = new AttacksDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        
        attacksDataSource.open();
        theAttacks = attacksDataSource.getAttackInfoList();
        attacksDataSource.close();
        
        ListView attacksListContent = (ListView)findViewById(R.id.attacksList);
        
        adapter = new AttackInfoArrayAdapter(this, android.R.layout.simple_list_item_1, theAttacks);
        attacksListContent.setAdapter(adapter);
        
        attacksListContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View clickedView, int position, long arg3) {
				final AttackInfo attackItem = (AttackInfo) parent.getItemAtPosition(position);
				
	        	Intent i = new Intent(AttacksListActivity.this, AttacksDetailActivity.class);
        		i.putExtra(ExtrasConstants.ATTACK_ID, attackItem.getAttackDbId());
				startActivity(i);
			}
        });
        
        attackSearch = (EditText)findViewById(R.id.searchAttacks);
        
        attackSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                AttacksListActivity.this.adapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });         
    }	
	
}
