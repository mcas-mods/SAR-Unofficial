package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IGunPart {

	public GunPartType getPartType();

	public String getPartName();

	default void onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn, ItemStack held) {

	}

	public static enum GunPartType {
		BARREL, CHAMBER, MECHANISM, STOCK
	}
}
