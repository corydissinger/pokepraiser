package com.cd.pokepraiser.data;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class AbilityDetail implements Parcelable {

	private AbilityAttributes abilityAttributes;
    private List<PokemonInfo> pokemonLearningAbility;
    
    public AbilityDetail(){}
    
    private AbilityDetail(Parcel pc){
    	abilityAttributes = (AbilityAttributes) pc.readValue(AbilityDetail.class.getClassLoader());
    	pc.readList(pokemonLearningAbility, AbilityDetail.class.getClassLoader());
    }
    
	public AbilityAttributes getAbilityAttributes() {
		return abilityAttributes;
	}
	public void setAbilityAttributes(AbilityAttributes abilityAttributes) {
		this.abilityAttributes = abilityAttributes;
	}
	public List<PokemonInfo> getPokemonLearningAbility() {
		return pokemonLearningAbility;
	}
	public void setPokemonLearningAbility(List<PokemonInfo> pokemonLearningAbility) {
		this.pokemonLearningAbility = pokemonLearningAbility;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(abilityAttributes);
		dest.writeList(pokemonLearningAbility);
	}	
	
	public static final Parcelable.Creator<AbilityDetail> CREATOR = new Parcelable.Creator<AbilityDetail>() {
		public AbilityDetail createFromParcel(Parcel pc) {
			return new AbilityDetail(pc);
		}
		
		public AbilityDetail[] newArray(int size) {
			return new AbilityDetail[size];
		}
	};	
}
