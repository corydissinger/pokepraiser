package com.cd.pokepraiser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.AttackInfoArrayAdapter;
import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.db.attacks.AttacksDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class AttacksListActivity extends PokepraiserActivity {

	private AttacksDataSource attacksDataSource;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attackslist_screen);
        
        attacksDataSource = new AttacksDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        
        attacksDataSource.open();
        AttackInfo[] theAttacks = attacksDataSource.getAttackInfoList();
        attacksDataSource.close();
        
        ListView attacksListContent = (ListView)findViewById(R.id.attacksList);
        
        AttackInfoArrayAdapter adapter = new AttackInfoArrayAdapter(this, android.R.layout.simple_list_item_1, theAttacks);
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
    }	
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.layout.activity_attackslist_screen, menu);
       return true;
    }    
	
}
