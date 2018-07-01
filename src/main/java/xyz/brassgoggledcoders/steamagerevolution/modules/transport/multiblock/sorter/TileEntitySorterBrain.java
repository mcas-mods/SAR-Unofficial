package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.guisystem.IHasGui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.MultiblockInventoryWrapper;

public class TileEntitySorterBrain extends TileEntitySorterPart implements IHasGui {

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new MultiblockInventoryWrapper(this, false));
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && isConnected()) {
			return true;
		}
		return super.hasCapability(capObject, side);
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiSorterBrain(new ContainerSorterBrain(getMultiblockController().cards, entityPlayer.inventory));
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerSorterBrain(getMultiblockController().cards, entityPlayer.inventory);
	}
}
