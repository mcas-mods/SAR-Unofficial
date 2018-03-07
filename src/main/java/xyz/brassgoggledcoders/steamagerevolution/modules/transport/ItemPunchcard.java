package xyz.brassgoggledcoders.steamagerevolution.modules.transport;

import java.util.List;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPunchcard extends ItemBase {

	public ItemPunchcard() {
		super("punchcard");
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
			tooltip.add("Code: " + tag.getInteger("code"));
		}
		else {
			tooltip.add("Not programmed");
		}
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

			tag.setInteger("code", EnumDyeColor.WHITE.getColorValue());

			stack.setTagCompound(tag);
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

}
