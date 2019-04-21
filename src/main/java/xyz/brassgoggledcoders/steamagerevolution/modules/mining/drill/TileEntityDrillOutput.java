package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedStack;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.ICrushedMaterial;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.MultiblockOreWrapper;

public class TileEntityDrillOutput extends TileEntityDrillPart implements ITickable {

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == SARCapabilities.CRUSHED_HANDLER || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == SARCapabilities.CRUSHED_HANDLER) {
			return SARCapabilities.CRUSHED_HANDLER.cast(new MultiblockOreWrapper(this));
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void update() {
		TileEntity tileEntity = this.getWorld().getTileEntity(getPos().down());
		if (this.isConnected() && tileEntity != null
				&& tileEntity.hasCapability(SARCapabilities.CRUSHED_HANDLER, EnumFacing.UP)) {
			if (this.getMultiblockController().inventory.ore.getHandler().getHolders()[0].getCrushed() != null) {
				ICrushedMaterial material = this.getMultiblockController().inventory.ore.getHandler().getHolders()[0]
						.getCrushed().getMaterial();
				int amount = this.getMultiblockController().inventory.ore.getHandler().getHolders()[0].getAmount();
				this.getMultiblockController().inventory.ore.getHandler().drain(material, amount);
				tileEntity.getCapability(SARCapabilities.CRUSHED_HANDLER, EnumFacing.UP)
						.fill(new CrushedStack(material, amount));
			}
		}
	}
}
