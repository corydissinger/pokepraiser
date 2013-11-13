package com.cd.pokepraiser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cd.pokepraiser.PokepraiserApplication;
import com.cd.pokepraiser.R;
import com.cd.pokepraiser.data.AttackDetail;
import com.cd.pokepraiser.db.attacks.AttacksDataSource;
import com.cd.pokepraiser.util.ExtrasConstants;

public class AttacksDetailActivity extends PokepraiserActivity {

	private AttacksDataSource attacksDataSource;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attacksdetail_screen);
        final Intent receivedIntent = getIntent();
        final int attackId			= receivedIntent.getIntExtra(ExtrasConstants.ATTACK_ID, 0);
        
        attacksDataSource = new AttacksDataSource(((PokepraiserApplication)getApplication()).getDatabaseReference());
        
        attacksDataSource.open();
        final AttackDetail attackDetail = attacksDataSource.getAttackDetail(attackId);
        attacksDataSource.close();
        
        TextView battleDesc 	= (TextView) findViewById(R.id.battleDesc);
        TextView secondaryDesc 	= (TextView) findViewById(R.id.secondaryDesc);
        
        battleDesc.setText(attackDetail.getBattleEffectDesc());
        secondaryDesc.setText(attackDetail.getSecondaryEffectDesc());
    }
    
    @Override
	public void onBackPressed(){
    	finish();
    }
	
}
