package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;

public class FluidTankSync extends FluidTank implements INBTSerializable<NBTTagCompound> {

	// Cannot be parented by an InventoryPiece because the handlers are initialised
	// in the InventoryPiece constructor call
	final IHasInventory<?> container;
	final String name;

	public FluidTankSync(String name, int capacity, IHasInventory<?> container) {
		super(capacity);
		this.container = container;
		this.name = name;
		if(container instanceof TileEntity) {
			setTileEntity((TileEntity) container);
		}
	}

	@Override
	public void onContentsChanged() {
		if(!container.getMachineWorld().isRemote) {
			SteamAgeRevolution.instance.getLogger().devInfo("Fluid update sent");
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(container.getMachinePos(), getFluid(), name), container.getMachinePos(),
					container.getMachineWorld().provider.getDimension());
			container.markMachineDirty();
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.readFromNBT(nbt);
	}
}
