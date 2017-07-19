package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.alloyfurnace.ControllerAlloyFurnace;

public class TileEntityAlloyFurnaceItemInput extends TileEntityAlloyFurnacePart {
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.isConnected())
				|| super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
					.cast(((ControllerAlloyFurnace) this.getMultiblockController()).inputSolid);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}
}
