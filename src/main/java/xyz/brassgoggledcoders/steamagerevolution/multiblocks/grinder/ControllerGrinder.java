package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMachine;

public class ControllerGrinder extends MultiblockCraftingMachine<InventoryCraftingMachine> {

	public static final String uid = "grinder";

	static {
		IMachine.referenceMachinesList.put(uid, new ControllerGrinder(null));
	}

	public ControllerGrinder(World world) {
		super(world);
		this.setInventory(new InventoryCraftingMachine(this)
				.addItemHandler("itemInput", IOType.INPUT, new int[] { 58 }, new int[] { 32 })
				.addItemHandler("itemOutput", IOType.OUTPUT, new int[] { 121 }, new int[] { 32 }).setSteamTank(10, 9)
				.setProgressBar(87, 33));
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
