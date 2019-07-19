package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;

public class FluidTankSync extends FluidTank implements INBTSerializable<NBTTagCompound> {

	InventoryPieceFluidTank enclosingIPiece;

	public FluidTankSync(int capacity) {
		super(capacity);
	}

	@Override
	public void onContentsChanged() {
		if(!enclosingIPiece.enclosingInv.enclosingMachine.getMachineWorld().isRemote) {
			SteamAgeRevolution.instance.getLogger().devInfo("Fluid update sent");
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(enclosingIPiece.enclosingInv.enclosingMachine.getMachinePos(), getFluid(),
							enclosingIPiece.getName()),
					enclosingIPiece.enclosingInv.enclosingMachine.getMachinePos(),
					enclosingIPiece.enclosingInv.enclosingMachine.getMachineWorld().provider.getDimension());
			enclosingIPiece.enclosingInv.enclosingMachine.markMachineDirty();
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

	// Set from the InventoryPiece constructor
	public void setEnclosing(InventoryPieceFluidTank inventoryPieceFluidTank) {
		this.enclosingIPiece = inventoryPieceFluidTank;
		if(enclosingIPiece.enclosingInv != null
				&& enclosingIPiece.enclosingInv.enclosingMachine instanceof TileEntity) {
			this.setTileEntity((TileEntity) enclosingIPiece.enclosingInv.enclosingMachine);
		}
	}
}
