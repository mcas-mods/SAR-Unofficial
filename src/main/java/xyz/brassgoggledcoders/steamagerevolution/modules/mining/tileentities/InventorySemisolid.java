package xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;

public class InventorySemisolid extends InventoryMachine {

	public InventoryPieceSemisolid ore;

	public InventorySemisolid(InventoryPieceSemisolid ore) {
		this(null, null, ore, null);
	}

	public InventorySemisolid(InventoryPieceItem itemInput, InventoryPieceItem itemOutput, InventoryPieceSemisolid ore,
			InventoryPieceFluid steamTank) {
		super(itemInput, null, itemOutput, null, steamTank);
		this.ore = ore;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = super.serializeNBT();
		tag.setTag("ore", ore.handler.serializeNBT());
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		super.deserializeNBT(tag);
		ore.handler.deserializeNBT(tag.getCompoundTag("ore"));
	}
}
