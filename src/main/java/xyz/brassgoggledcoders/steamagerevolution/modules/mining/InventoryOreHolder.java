package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;

public class InventoryOreHolder extends InventoryMachine {

	public InventoryPieceOre ore;
	
	public InventoryOreHolder(InventoryPieceItem itemInput,
			InventoryPieceItem itemOutput, InventoryPieceOre ore, InventoryPieceFluid steamTank) {
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
