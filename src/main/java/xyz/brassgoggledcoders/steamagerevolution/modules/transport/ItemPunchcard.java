package xyz.brassgoggledcoders.steamagerevolution.modules.transport;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemPunchcard extends ItemBase {

	public ItemPunchcard() {
		super("punchcard");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

		if(!stack.isEmpty()) {
			NBTTagCompound tag;
			if(stack.hasTagCompound()) {
				tag = stack.getTagCompound();
			}
			else {
				tag = new NBTTagCompound();
			}

			tag.setInteger("from", EnumDyeColor.WHITE.getColorValue());
			tag.setInteger("to", EnumDyeColor.BLACK.getColorValue());

			stack.setTagCompound(tag);
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

}
