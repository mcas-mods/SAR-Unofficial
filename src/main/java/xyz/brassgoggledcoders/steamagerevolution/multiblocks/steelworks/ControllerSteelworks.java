package xyz.brassgoggledcoders.steamagerevolution.multiblocks.steelworks;

import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;

public class ControllerSteelworks extends MultiblockCraftingMachine<InventoryCraftingMachine> {

	public ControllerSteelworks(World world) {
		super(world);
		setInventory(
				new InventoryCraftingMachine(this).addItemHandler("itemInput", IOType.INPUT, new int[] { 83 }, new int[] { 31 })
						.addFluidHandler("ironTank", IOType.INPUT, 41, 9, Fluid.BUCKET_VOLUME * 16)
						.addFluidHandler("steelTank", IOType.OUTPUT, 141, 9, Fluid.BUCKET_VOLUME * 16)
						.setSteamTank(10, 9).setProgressBar(110, 31));
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
	public String getName() {
		return "Steelworks";
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
}
