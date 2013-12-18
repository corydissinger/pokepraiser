package com.cd.pokepraiser.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PokemonSearchQuery implements Parcelable {
	
	private int typeOne			= -1;
	private int typeTwo			= -1;
	
	private int abilityId		= -1;
	
	private int attackIdOne		= -1;
	private int attackIdTwo		= -1;
	private int attackIdThree	= -1;
	private int attackIdFour	= -1;
	
	//These values are as follows:
	// -1 = No initialized
	//	0 = No sort selected by the user
	//	1 = Sort DESCENDING 
	//	2 = Sort ASCENDING 
	private int hpSort			= -1;
	private int attackSort		= -1;
	private int defSort			= -1;
	private int spAtkSort		= -1;
	private int spDefSort		= -1;
	private int speSort			= -1;
	
	public PokemonSearchQuery() {}
	
	public PokemonSearchQuery(Parcel pc) {
		typeOne			= pc.readInt();
		typeTwo			= pc.readInt();
		
		abilityId		= pc.readInt();
		
		attackIdOne		= pc.readInt();
		attackIdTwo		= pc.readInt();		
		attackIdThree	= pc.readInt();
		attackIdFour	= pc.readInt();
		
		hpSort			= pc.readInt();
		attackSort		= pc.readInt();
		defSort			= pc.readInt();
		spAtkSort		= pc.readInt();
		spDefSort		= pc.readInt();
		speSort			= pc.readInt();
		
	}
	
	public int getTypeOne() {
		return typeOne;
	}
	public void setTypeOne(int typeOne) {
		this.typeOne = typeOne;
	}
	public int getTypeTwo() {
		return typeTwo;
	}
	public void setTypeTwo(int typeTwo) {
		this.typeTwo = typeTwo;
	}
	public int getAbilityId() {
		return abilityId;
	}
	public void setAbilityId(int abilityId) {
		this.abilityId = abilityId;
	}
	public int getAttackIdOne() {
		return attackIdOne;
	}
	public void setAttackIdOne(int attackIdOne) {
		this.attackIdOne = attackIdOne;
	}
	public int getAttackIdTwo() {
		return attackIdTwo;
	}
	public void setAttackIdTwo(int attackIdTwo) {
		this.attackIdTwo = attackIdTwo;
	}
	public int getAttackIdThree() {
		return attackIdThree;
	}
	public void setAttackIdThree(int attackIdThree) {
		this.attackIdThree = attackIdThree;
	}
	public int getAttackIdFour() {
		return attackIdFour;
	}
	public void setAttackIdFour(int attackIdFour) {
		this.attackIdFour = attackIdFour;
	}
	public int getHpSort() {
		return hpSort;
	}
	public void setHpSort(int hpSort) {
		this.hpSort = hpSort;
	}
	public int getAttackSort() {
		return attackSort;
	}
	public void setAttackSort(int attackSort) {
		this.attackSort = attackSort;
	}
	public int getDefSort() {
		return defSort;
	}
	public void setDefSort(int defSort) {
		this.defSort = defSort;
	}
	public int getSpAtkSort() {
		return spAtkSort;
	}
	public void setSpAtkSort(int spAtkSort) {
		this.spAtkSort = spAtkSort;
	}
	public int getSpDefSort() {
		return spDefSort;
	}
	public void setSpDefSort(int spDefSort) {
		this.spDefSort = spDefSort;
	}
	public int getSpeSort() {
		return speSort;
	}
	public void setSpeSort(int speSort) {
		this.speSort = speSort;
	}
	
	public static final Parcelable.Creator<PokemonSearchQuery> CREATOR = new Parcelable.Creator<PokemonSearchQuery>() {
		public PokemonSearchQuery createFromParcel(Parcel pc) {
			return new PokemonSearchQuery(pc);
		}
		
		public PokemonSearchQuery[] newArray(int size) {
			return new PokemonSearchQuery[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel pc, int flags) {
		pc.writeInt(typeOne);
		pc.writeInt(typeTwo);
		
		pc.writeInt(abilityId);
		
		pc.writeInt(attackIdOne);
		pc.writeInt(attackIdTwo);		
		pc.writeInt(attackIdThree);
		pc.writeInt(attackIdFour);
		
		pc.writeInt(hpSort);
		pc.writeInt(attackSort);
		pc.writeInt(defSort);
		pc.writeInt(spAtkSort);
		pc.writeInt(spDefSort);
		pc.writeInt(speSort);
	}	
	
	public boolean isEmpty(){
		return typeOne == -1 &&
			   typeTwo == -1 &&
			   abilityId == -1 &&
			   attackIdOne == -1 &&
			   attackIdTwo == -1 &&
			   attackIdThree == -1 &&
			   attackIdFour == -1 &&
			   hpSort == -1 &&
			   attackSort == -1 &&
			   defSort == -1 &&
			   spAtkSort == -1 &&
			   spDefSort == -1 &&
			   speSort == -1;
	}
}
