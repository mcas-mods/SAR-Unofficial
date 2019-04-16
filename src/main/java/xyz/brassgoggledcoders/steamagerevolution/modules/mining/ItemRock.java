package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemRock extends ItemBase {

	public ItemRock(String type) {
		super("rock_" + type.toLowerCase());
		this.setMaxStackSize(1);
	}

}
