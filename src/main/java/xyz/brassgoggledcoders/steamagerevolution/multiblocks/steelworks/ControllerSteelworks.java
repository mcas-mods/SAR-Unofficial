package xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;

public class ControllerSteelworks extends MultiblockCraftingMachine<InventoryCraftingMachine> {

	public static final String uid = "steelworks";

	public ControllerSteelworks(World world) {
		super(world);
		setInventory(new InventoryBuilder<>(new InventoryCraftingMachine(this))
				.addPiece("itemInput", new InventoryPieceItemHandler(IOType.INPUT, 83, 31))
				.addPiece("ironTank", new InventoryPieceFluidTank(IOType.INPUT, 41, 9, Fluid.BUCKET_VOLUME * 16))
				.addPiece("steelTank", new InventoryPieceFluidTank(IOType.OUTPUT, 141, 9, Fluid.BUCKET_VOLUME * 16))
				.addSteamTank(10, 9).setProgressBar(110, 31).build());
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 97;
	}

	@Override
	public int getMinimumXSize() {
		return 5;
	}

	@Override
	public int getMinimumZSize() {
		return 5;
	}

	@Override
	public int getMinimumYSize() {
		return 9;
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public int getMaximumXSize() {
		return getMinimumXSize();
	}

	@Override
	public int getMaximumZSize() {
		return getMinimumZSize();
	}

	@Override
	public int getMaximumYSize() {
		return getMinimumYSize();
	}

	@Override
	public ItemStack getCatalyst() {
		return new ItemStack(SARObjectHolder.steelworks_frame);
	}
}
