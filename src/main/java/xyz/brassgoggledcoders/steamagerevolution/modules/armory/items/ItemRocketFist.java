package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRocketFist extends ItemBase {

	public static final int steamUsePerBlock = 10;
	int capacity = 3000;

	public ItemRocketFist() {
		super("rocket_fist");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
				.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		FluidStack fluid = internal.getFluid();
		if(fluid == null) {
			tooltip.add("0mB/" + capacity + "mB");
		}
		else {
			tooltip.add(fluid.getLocalizedName());
			tooltip.add(fluid.amount + "mB/" + capacity + "mB");
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
				.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if(internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock) {
			playerIn.setVelocity(playerIn.motionX + (playerIn.getLookVec().x * 3),
					playerIn.motionY + (playerIn.getLookVec().y * 3), playerIn.motionZ + (playerIn.getLookVec().z * 3));
			playerIn.playSound(SoundEvents.E_PARROT_IM_CREEPER, 10, 1);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
				.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if(internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock) {
			target.world.createExplosion(null, target.posX, target.posY, target.posZ, 0.5F, false);
			internal.drain(steamUsePerBlock * 10, true);
			stack.damageItem(1, attacker);
			return true;
		}
		return false;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FluidHandlerItemStack(stack, capacity) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return FluidRegistry.getFluidName(fluid).equals("steam");
			}
		};
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
				.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		return 1.0D - ((double) internal.getFluid().amount / capacity);

	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
				.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		return internal.getFluid() != null;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void getSubItems(@Nullable CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if(tab != null && tab == getCreativeTab() || tab == CreativeTabs.SEARCH) {
			subItems.addAll(getAllSubItems(Lists.newArrayList()));
		}
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		itemStacks.add(new ItemStack(this, 1));
		return itemStacks;
	}
}
