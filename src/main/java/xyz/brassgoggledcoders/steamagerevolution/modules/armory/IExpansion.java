package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IExpansion {
	ResourceLocation getIdentifier();

	ExpansionType getExpansionType();

	boolean canApplyTo(ItemStack targetPiece);
}
