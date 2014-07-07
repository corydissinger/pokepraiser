package com.cd.pokepraiser.data;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class AttackDetail implements Parcelable {

	private AttackAttributes attackAttributes;
	private List<PokemonInfo> pokemonLearningAttack;
	
	public AttackDetail(){}
	
	public AttackDetail(Parcel pc) {
		attackAttributes = (AttackAttributes) pc.readValue(AttackDetail.class.getClassLoader());
		pc.readList(pokemonLearningAttack, AttackDetail.class.getClassLoader());
	}
	
	public AttackAttributes getAttackAttributes() {
		return attackAttributes;
	}
	public void setAttackAttributes(AttackAttributes attackAttributes) {
		this.attackAttributes = attackAttributes;
	}
	public List<PokemonInfo> getPokemonLearningAttack() {
		return pokemonLearningAttack;
	}
	public void setPokemonLearningAttack(
			List<PokemonInfo> pokemonLearningAttack) {
		this.pokemonLearningAttack = pokemonLearningAttack;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(attackAttributes);
		dest.writeList(pokemonLearningAttack);
	}
	
	public static final Parcelable.Creator<AttackDetail> CREATOR = new Parcelable.Creator<AttackDetail>() {
		public AttackDetail createFromParcel(Parcel pc) {
			return new AttackDetail(pc);
		}
		
		public AttackDetail[] newArray(int size) {
			return new AttackDetail[size];
		}
	};	
}
