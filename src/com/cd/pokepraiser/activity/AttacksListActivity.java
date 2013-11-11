package com.cd.pokepraiser.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.cd.pokepraiser.R;
import com.cd.pokepraiser.adapter.AttackInfoArrayAdapter;
import com.cd.pokepraiser.data.AttackInfo;
import com.cd.pokepraiser.db.attacks.AttacksDataSource;

public class AttacksListActivity extends PokepraiserActivity {

	private AttacksDataSource attacksDataSource;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attackslist_screen);
        
        attacksDataSource = new AttacksDataSource(this);
        
        attacksDataSource.open();
        AttackInfo[] theAttacks = attacksDataSource.getAttackInfoList();
        attacksDataSource.close();
        
        ListView attacksListContent = (ListView)findViewById(R.id.attacksList);
        
        AttackInfoArrayAdapter adapter = new AttackInfoArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, theAttacks);
        attacksListContent.setAdapter(adapter);
    }	
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.layout.activity_attackslist_screen, menu);
       return true;
    }    
	
}
