package xyz.brassgoggledcoders.steamagerevolution.modules.steam.items;

import java.util.List;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.items.itemblocks.ItemBlockModel;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockPortableBoiler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityPortableBoiler;

public class ItemBlockPortableBoiler extends ItemBlockModel<BlockPortableBoiler> {

	public ItemBlockPortableBoiler(Block block) {
		super((BlockPortableBoiler) block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound()) {
			// TODO Work on NBT directly instead.
			TileEntityPortableBoiler dummy = new TileEntityPortableBoiler();
			NBTTagCompound tileData = stack.getTagCompound().getCompoundTag("teData");
			dummy.deserializeNBT(tileData);
			tooltip.add(com.teamacronymcoders.base.util.TextUtils
					.representInventoryContents(dummy.inventory.getInputHandler()).getUnformattedText());
			tooltip.add(com.teamacronymcoders.base.util.TextUtils.representTankContents(dummy.inventory.getInputTank())
					.getUnformattedText());
			tooltip.add(com.teamacronymcoders.base.util.TextUtils.representTankContents(dummy.inventory.getSteamTank())
					.getUnformattedText());
			tooltip.add("Burn time: " + dummy.currentTicks);
		} else {
			tooltip.add("No inventory data");
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.hasTagCompound()) {
			// FIXME DO NOT do this every tick. Work on NBT directly instead.
			TileEntityPortableBoiler dummy = new TileEntityPortableBoiler();
			NBTTagCompound tileData = stack.getTagCompound().getCompoundTag("teData");
			dummy.deserializeNBT(tileData);
			if (dummy.currentTicks == 0) {
				if (!dummy.inventory.getInputHandler().getStackInSlot(0).isEmpty()) {
					dummy.currentTicks = TileEntityFurnace
							.getItemBurnTime(dummy.inventory.getInputHandler().getStackInSlot(0));
					dummy.inventory.getInputHandler().extractItem(0, 1, false);
				}
			} else {
				int fluidAmount = Fluid.BUCKET_VOLUME / 20;
				dummy.inventory.getInputTank().drain(fluidAmount, true);
				dummy.inventory.getSteamTank().fill(FluidRegistry.getFluidStack("steam", fluidAmount), true);
				dummy.currentTicks--;
			}
			stack.setTagInfo("teData", dummy.serializeNBT());
		}

	}

}
