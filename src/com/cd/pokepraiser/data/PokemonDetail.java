package com.cd.pokepraiser.data;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class PokemonDetail implements Parcelable{

	private PokemonAttributes pokemonAttributes;
	private AbilityInfo [] pokemonAbilities;
	private List<PokemonAttackInfo> pokemonAttacks;
	
	public PokemonDetail(Parcel pc){
		pokemonAttributes = (PokemonAttributes) pc.readValue(PokemonDetail.class.getClassLoader());
		pokemonAbilities = (AbilityInfo[]) pc.readArray(PokemonDetail.class.getClassLoader());
		pc.readList(pokemonAttacks, PokemonDetail.class.getClassLoader());
	}
	
	public PokemonDetail() {}

	public PokemonAttributes getPokemonAttributes() {
		return pokemonAttributes;
	}
	public void setPokemonAttributes(PokemonAttributes pokemonDetail) {
		this.pokemonAttributes = pokemonDetail;
	}
	public AbilityInfo[] getPokemonAbilities() {
		return pokemonAbilities;
	}
	public void setPokemonAbilities(AbilityInfo[] pokemonAbilities) {
		this.pokemonAbilities = pokemonAbilities;
	}
	public List<PokemonAttackInfo> getPokemonAttacks() {
		return pokemonAttacks;
	}
	public void setPokemonAttacks(List<PokemonAttackInfo> pokemonAttacks) {
		this.pokemonAttacks = pokemonAttacks;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(pokemonAttributes);
		dest.writeArray(pokemonAbilities);
		dest.writeList(pokemonAttacks);
	}
	
	public static final Parcelable.Creator<PokemonDetail> CREATOR = new Parcelable.Creator<PokemonDetail>() {
		public PokemonDetail createFromParcel(Parcel pc) {
			return new PokemonDetail(pc);
		}
		
		public PokemonDetail[] newArray(int size) {
			return new PokemonDetail[size];
		}
	};	
	
}
