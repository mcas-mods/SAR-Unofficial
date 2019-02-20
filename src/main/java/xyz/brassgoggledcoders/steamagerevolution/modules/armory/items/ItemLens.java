package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import java.util.List;

import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ILens;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ModuleArmory;

public class ItemLens extends ItemBase implements IHasSubItems {

	public ItemLens() {
		super("lens");
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public List<String> getModelNames(List<String> modelNames) {
		modelNames.add(name);
		return modelNames;
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		// TODO This is likely a fragile way of storing, but it feels unnecessary to use
		// a hashmap
		int i = 0;
		for(ILens lens : ModuleArmory.lenseTypes) {
			itemStacks.add(new ItemStack(this, 1, i++));
		}
		return itemStacks;
	}

	public static class LensTintHandler implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if(tintIndex == 1) {
				return ModuleArmory.lenseTypes.get(stack.getMetadata()).getColor();
			}
			return -1;
		}
	}
}
