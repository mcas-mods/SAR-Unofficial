package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.utils.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.InventoryMachine.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.InventoryMachine.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerSteelworks extends SARMultiblockInventory {

	// public static final int workingPoolLevel = ModuleMetalworking.VALUE_BLOCK *
	// 9;
	public static final int conversionPerOperation = ModuleMetalworking.VALUE_NUGGET;
	public static final int steamUsePerOperation = Fluid.BUCKET_VOLUME / 10;
	public static final int carbonPerOperation = TileEntityFurnace.getItemBurnTime(new ItemStack(Items.COAL)) / 2;

	public ControllerSteelworks(World world) {
		super(world);
		this.setInventory(new InventoryMachine(new InventoryPieceItem(new ItemStackHandlerFuel(1), 0, 0),
				new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME * 16, this, 0), 0, 0), null,
				new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME * 16, this, 1), 0, 0),
				new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this), 0, 0)));
	}

	// @Override
	// protected FluidTank getTank(String toWrap) {
	// if(toWrap.equals("iron")) {
	// return ironTank;
	// }
	// else if(toWrap.equals("steel")) {
	// return outputTank;
	// }
	// return getSteamTank();
	// }
	//
	// @Override
	// public ItemStackHandler getInventory(String toWrap) {
	// return inputSolid;
	// }

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 1;
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
