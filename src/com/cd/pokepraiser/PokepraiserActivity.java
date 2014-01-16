package com.cd.pokepraiser;

import java.util.ArrayList;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.cd.pokepraiser.adapter.NavDrawerAdapter;
import com.cd.pokepraiser.data.NavDrawerItem;
import com.cd.pokepraiser.fragment.AbilitiesListFragment;
import com.cd.pokepraiser.fragment.AbilityDetailFragment;
import com.cd.pokepraiser.fragment.AttackDetailFragment;
import com.cd.pokepraiser.fragment.AttacksListFragment;
import com.cd.pokepraiser.fragment.PokeSearchFragment;
import com.cd.pokepraiser.fragment.PokemonDetailFragment;
import com.cd.pokepraiser.fragment.PokemonListFragment;
import com.cd.pokepraiser.fragment.TeamBuilderFragment;
import com.cd.pokepraiser.fragment.TeamManagerFragment;
import com.cd.pokepraiser.fragment.TeamMemberFragment;

public class PokepraiserActivity extends SherlockFragmentActivity {
	
	private static final String POKE_LIST_TO_DETAIL 		= "a";
	private static final String POKE_DETAIL_TO_ATTACK 		= "b";
	private static final String POKE_DETAIL_TO_ABILITY 		= "c";
	private static final String ATTACK_DETAIL_TO_POKE 		= "d";
	private static final String ABILITY_DETAIL_TO_POKE 		= "e";
	private static final String SEARCH_TO_LIST		 		= "f";
	private static final String LIST_TO_SEARCH		 		= "g";
	private static final String ATTACK_LIST_TO_DETAIL 		= "h";
	private static final String ABILITY_LIST_TO_DETAIL 		= "i";
	private static final String POKE_LIST_TO_ATTACK 		= "j";
	private static final String POKE_LIST_TO_ABILITY 		= "k";
	private static final String POKE_LIST_TO_TEAMS	 		= "l";
	private static final String TEAM_MANAGER_TO_BUILDER		= "m";
	private static final String TEAM_BUILDER_TO_MEMBER		= "n";	
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private ArrayList<NavDrawerItem> mNavDrawerItems;
	private NavDrawerAdapter mDrawerAdapter;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;	
	
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_screen);
        
        if(savedInstanceState == null)
        	((PokepraiserApplication)getApplication()).loadResources();        

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
    	mNavDrawerItems = getNavDrawerItems();
    	mDrawerAdapter = new NavDrawerAdapter(getApplicationContext(), mNavDrawerItems);
    	
    	mDrawerList.setAdapter(mDrawerAdapter);
    	mDrawerList.setOnItemClickListener(new DrawerItemClickListener());    	

    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	getSupportActionBar().setHomeButtonEnabled(true);    	
    	
    	mDrawerToggle 
    		= new ActionBarDrawerToggle(this, 
    									mDrawerLayout, 
    									R.drawable.ic_drawer, 
    									R.string.app_name, 
    									R.string.app_name){
    		
    		public void onDrawerClosed(View view) {
    			getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}    		
    	};
    	
    	mDrawerLayout.setDrawerListener(mDrawerToggle);
    	
        if(savedInstanceState == null){        
        	PokemonListFragment frag = new PokemonListFragment();
	    	changeFragment(frag, frag.TAG);
        }
    }

    //protected void onStart();
    
    protected void onDestroy(){
    	((PokepraiserApplication)getApplication()).closeResources();
    	super.onDestroy();    	
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId() == android.R.id.home){
    		if(mDrawerLayout.isDrawerOpen(mDrawerList)){
    			mDrawerLayout.closeDrawer(mDrawerList);
    		}else{
    			mDrawerLayout.openDrawer(mDrawerList);    			
    		}
    	}
    	
        switch (item.getItemId()) {
            case R.id.action_team_add:
        		Fragment currFrag = getSupportFragmentManager().findFragmentById(R.id.content_frame);            	
            	((PokemonDetailFragment)currFrag).openTeamAddDialog();
            	return true;
            	
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}    
    
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}    

    @Override
    public void onBackPressed() {
    	FragmentManager fragMan = getSupportFragmentManager();
    	final int stackSize = fragMan.getBackStackEntryCount();
    	
    	if(stackSize == 0){
    		super.onBackPressed();
    		return;
    	}
    	
    	BackStackEntry backStackFrame = fragMan.getBackStackEntryAt(stackSize - 1);
		final String lastTrans = (String) backStackFrame.getName();
		
		fragMan.popBackStack(lastTrans, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }    
    
	public void changeFragment(Fragment newFrag, String newTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final String transition = getTransitionString(newFrag);
        FragmentTransaction txn = fragmentManager.beginTransaction();
        
        if(transition != null)
        	txn.replace(R.id.content_frame, newFrag, newTag).addToBackStack(transition);
        else{
        	txn.replace(R.id.content_frame, newFrag);
        }
        	
        txn.commit();
        
        invalidateOptionsMenu();
	}

	private String getTransitionString(Fragment newFrag) {
		Fragment currFrag = getSupportFragmentManager().findFragmentById(R.id.content_frame);		
		
		if(currFrag instanceof PokemonListFragment && newFrag instanceof PokemonDetailFragment){
			return POKE_LIST_TO_DETAIL;
		}else if(currFrag instanceof PokemonDetailFragment && newFrag instanceof AttackDetailFragment){
			return POKE_DETAIL_TO_ATTACK;
		}else if(currFrag instanceof PokemonDetailFragment && newFrag instanceof AbilityDetailFragment){
			return POKE_DETAIL_TO_ABILITY;
		}else if(currFrag instanceof AttackDetailFragment && newFrag instanceof PokemonDetailFragment){
			return ATTACK_DETAIL_TO_POKE;
		}else if(currFrag instanceof AbilityDetailFragment && newFrag instanceof PokemonDetailFragment){
			return ABILITY_DETAIL_TO_POKE;
		}else if(currFrag instanceof PokeSearchFragment && newFrag instanceof PokemonListFragment){
			return SEARCH_TO_LIST;
		}else if(currFrag instanceof PokemonListFragment && newFrag instanceof PokeSearchFragment){
			return LIST_TO_SEARCH;
		}else if(currFrag instanceof AttacksListFragment && newFrag instanceof AttackDetailFragment){
			return ATTACK_LIST_TO_DETAIL;
		}else if(currFrag instanceof AbilitiesListFragment && newFrag instanceof AbilityDetailFragment){
			return ABILITY_LIST_TO_DETAIL;
		}else if(currFrag instanceof PokemonListFragment && newFrag instanceof AttacksListFragment){
			return POKE_LIST_TO_ATTACK;
		}else if(currFrag instanceof PokemonListFragment && newFrag instanceof AbilitiesListFragment){
			return POKE_LIST_TO_ABILITY;
		}else if(currFrag instanceof PokemonListFragment && newFrag instanceof TeamManagerFragment){
			return POKE_LIST_TO_TEAMS;
		}else if(currFrag instanceof TeamManagerFragment && newFrag instanceof TeamBuilderFragment){
			return TEAM_MANAGER_TO_BUILDER;
		}else if(currFrag instanceof TeamBuilderFragment && newFrag instanceof TeamMemberFragment){
			return TEAM_BUILDER_TO_MEMBER;
		}
		
		return null;
	}
	
	private ArrayList<NavDrawerItem> getNavDrawerItems(){
		final ArrayList<NavDrawerItem> navDrawerItems 	= new ArrayList<NavDrawerItem>();
		
		final TypedArray icons						= getResources().obtainTypedArray(R.array.icons);
		final TypedArray titles						= getResources().obtainTypedArray(R.array.titles);		
		
		for(int i = 0; i < titles.length(); i++){
			NavDrawerItem navDrawerItem = new NavDrawerItem();

			final String theTitle  = titles.getString(i);			
			final int theIcon = icons.getResourceId(i, 0);
			
			if(theIcon != 0){
				navDrawerItem.setIcon(theIcon);
				navDrawerItem.setTitle(theTitle);				
			}else{
				navDrawerItem.setTitle(theTitle);
			}
			
			navDrawerItems.add(navDrawerItem);
		}
		
		icons.recycle();
		titles.recycle();
		
		return navDrawerItems;
	}
	
	private void selectNavItem(int pos){
		boolean isValidItem = false;
		Fragment newFrag = null;
		String fragTag = null;
		
		switch(pos){
			case 0: //Search Title
				break;
			case 1: //Search pokemon
				isValidItem = true;
				newFrag = new PokemonListFragment();
				fragTag = PokemonListFragment.TAG;
				break;
			case 2: //Search Attacks
				isValidItem = true;
				newFrag = new AttacksListFragment();
				fragTag = AttacksListFragment.TAG;				
				break;
			case 3: //Search Abilities
				isValidItem = true;				
				newFrag = new AbilitiesListFragment();
				fragTag = AbilitiesListFragment.TAG;				
				break;
			case 4: //Advanced Search
				isValidItem = true;				
				newFrag = new PokeSearchFragment();
				fragTag = PokeSearchFragment.TAG;				
				break;
			case 5: //Tools title
				break;
			case 6: //Teambuilder
				isValidItem = true;
				newFrag = new TeamManagerFragment();
				fragTag = TeamManagerFragment.TAG;
				break;
			default: return;
		}
		
		if(isValidItem){
			getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			changeFragment(newFrag, fragTag);			
			mDrawerList.setItemChecked(pos, true);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int pos, long arg3) {
			selectNavItem(pos);
		}
		
	}
}
