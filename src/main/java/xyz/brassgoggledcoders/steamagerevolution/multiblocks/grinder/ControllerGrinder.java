package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.*;

public class ControllerGrinder extends MultiblockCraftingMachine<InventoryCraftingMachine> {

	public static final String uid = "grinder";

	public ControllerGrinder(World world) {
		super(world);
		this.setInventory(new InventoryBuilder<>(new InventoryCraftingMachine(this))
				.addPiece("itemInput", new InventoryPieceItemHandler(IOType.INPUT, 58, 32))
				.addPiece("itemOutput", new InventoryPieceItemHandler(IOType.OUTPUT, 121, 32)).addSteamTank(10, 9)
				.addPiece("progress", new InventoryPieceProgressBar(87, 33)).build());
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
	}

	@Override
	public int getMaximumXSize() {
		return 3;
	}

	@Override
	public int getMaximumZSize() {
		return 3;
	}

	@Override
	public int getMaximumYSize() {
		return 3;
	}

	@Override
	public ItemStack getCatalyst() {
		return new ItemStack(SARObjectHolder.grinder_frame);
	}

}
