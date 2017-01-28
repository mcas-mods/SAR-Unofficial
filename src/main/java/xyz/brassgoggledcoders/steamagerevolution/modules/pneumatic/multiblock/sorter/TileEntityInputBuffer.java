package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.multiblock.sorter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.guisystem.IHasGui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

// TODO NBT
public class TileEntityInputBuffer extends TileEntitySorterPart implements IHasGui {
	public ItemStackHandler code = new ItemStackHandler(3);
	public ItemStackHandler inventory = new ItemStackHandler(6);

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.isConnected())
			return true;
		return super.hasCapability(capObject, side);
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, NBTTagCompound context) {
		return new GuiCodeSelector(new ContainerCodeSelector(code, entityPlayer.inventory));
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, NBTTagCompound context) {
		return new ContainerCodeSelector(code, entityPlayer.inventory);
	}
}
