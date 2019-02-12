package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IGunPart {

	public GunPartType getPartType();

	public String getPartName();

	default void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {

	}

	default void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {

	}

	public static enum GunPartType {
		BARREL, CHAMBER, MECHANISM, STOCK
	}
}
