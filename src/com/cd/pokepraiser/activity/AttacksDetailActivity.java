package com.cd.pokepraiser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
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
        
        TextView attackName		= (TextView) findViewById(R.id.attackName);
        TextView battleDesc 	= (TextView) findViewById(R.id.battleDesc);
        TextView secondaryDesc 	= (TextView) findViewById(R.id.secondaryDesc);
        
        TextView power 			= (TextView) findViewById(R.id.basePower);
        TextView accuracy		= (TextView) findViewById(R.id.baseAccuracy);
        TextView pp	 			= (TextView) findViewById(R.id.basePp);
    	TextView effectPct = (TextView) findViewById(R.id.effectPct);        

        attackName.setText(attackDetail.getName());
        battleDesc.setText(attackDetail.getBattleEffectDesc());
        secondaryDesc.setText(attackDetail.getSecondaryEffectDesc());
        
    	power.setText(attackDetail.getBasePower());
    	accuracy.setText(attackDetail.getBaseAccuracy());	
        pp.setText(attackDetail.getBasePp());
        
        if(attackDetail.getEffectPct() > 0){
        	effectPct.setText(Integer.toString(attackDetail.getEffectPct()) + "%");
        }else{
        	LinearLayout effectPctRow = (LinearLayout) findViewById(R.id.effectPctLayoutRow);
        	((LinearLayout)effectPctRow.getParent()).removeView(effectPctRow);
        }
    }
    
    @Override
	public void onBackPressed(){
    	finish();
    }
	
}
