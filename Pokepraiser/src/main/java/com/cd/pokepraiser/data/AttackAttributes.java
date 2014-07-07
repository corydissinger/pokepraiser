package com.cd.pokepraiser.data;

import java.io.Serializable;

public class AttackAttributes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8516058102749008509L;
	
	private String name;
	private String type;
	private String basePower;
	private String baseAccuracy;
	private String basePp;
	private int categoryDrawableId; 
	private int attackDbId;
	private int effectPct;
	private String battleEffectDesc;
	private String secondaryEffectDesc;
	private boolean contacts;
	private boolean sound;
	private boolean punch;
	private boolean snatchable;
	private boolean gravity;
	private boolean defrosts;
	private boolean hitsOpponentTriples;
	private boolean reflectable;
	private boolean blockable;
	private boolean mirrorable;
	private int priority;	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBasePower() {
		return basePower;
	}
	public void setBasePower(String basePower) {
		this.basePower = basePower;
	}
	public String getBaseAccuracy() {
		return baseAccuracy;
	}
	public void setBaseAccuracy(String baseAccuracy) {
		this.baseAccuracy = baseAccuracy;
	}
	public String getBasePp() {
		return basePp;
	}
	public void setBasePp(String basePp) {
		this.basePp = basePp;
	}
	public int getCategoryDrawableId() {
		return categoryDrawableId;
	}
	public void setCategoryDrawableId(int categoryDrawableId) {
		this.categoryDrawableId = categoryDrawableId;
	}
	public int getAttackDbId() {
		return attackDbId;
	}
	public void setAttackDbId(int attackDbId) {
		this.attackDbId = attackDbId;
	}
	public int getEffectPct() {
		return effectPct;
	}
	public void setEffectPct(int effectPct) {
		this.effectPct = effectPct;
	}
	public String getBattleEffectDesc() {
		return battleEffectDesc;
	}
	public void setBattleEffectDesc(String battleEffectDesc) {
		this.battleEffectDesc = battleEffectDesc;
	}
	public String getSecondaryEffectDesc() {
		return secondaryEffectDesc;
	}
	public void setSecondaryEffectDesc(String secondaryEffectDesc) {
		this.secondaryEffectDesc = secondaryEffectDesc;
	}
	public boolean isContacts() {
		return contacts;
	}
	public void setContacts(boolean contacts) {
		this.contacts = contacts;
	}
	public boolean isSound() {
		return sound;
	}
	public void setSound(boolean sound) {
		this.sound = sound;
	}
	public boolean isPunch() {
		return punch;
	}
	public void setPunch(boolean punch) {
		this.punch = punch;
	}
	public boolean isSnatchable() {
		return snatchable;
	}
	public void setSnatchable(boolean snatchable) {
		this.snatchable = snatchable;
	}
	public boolean isGravity() {
		return gravity;
	}
	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}
	public boolean isDefrosts() {
		return defrosts;
	}
	public void setDefrosts(boolean defrosts) {
		this.defrosts = defrosts;
	}
	public boolean isHitsOpponentTriples() {
		return hitsOpponentTriples;
	}
	public void setHitsOpponentTriples(boolean hitsOpponentTriples) {
		this.hitsOpponentTriples = hitsOpponentTriples;
	}
	public boolean isReflectable() {
		return reflectable;
	}
	public void setReflectable(boolean reflectable) {
		this.reflectable = reflectable;
	}
	public boolean isBlockable() {
		return blockable;
	}
	public void setBlockable(boolean blockable) {
		this.blockable = blockable;
	}
	public boolean isMirrorable() {
		return mirrorable;
	}
	public void setMirrorable(boolean mirrorable) {
		this.mirrorable = mirrorable;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
