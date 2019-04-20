package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.parts;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IGunPart {

	public GunPartType getPartType();

	public String getPartName();

	default ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	default void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {

	}

	@Deprecated
	default void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {

	}

	public static enum GunPartType {
		BARREL, CHAMBER, MECHANISM, STOCK
	}
}
