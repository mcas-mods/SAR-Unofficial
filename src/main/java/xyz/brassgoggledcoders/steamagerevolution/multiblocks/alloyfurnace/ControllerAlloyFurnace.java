package xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class ControllerAlloyFurnace extends MultiblockCraftingMachine<InventoryCraftingMachine> {

	public static final String uid = "alloy_furnace";
	public static int inputCapacity = RecipeUtil.VALUE_BLOCK * 8;
	public static int outputCapacity = Fluid.BUCKET_VOLUME * 8;

	static {
		IMachine.referenceMachinesList.put(uid, new ControllerAlloyFurnace(null));
	}

	public ControllerAlloyFurnace(World world) {
		super(world);
		// FIXME
		setInventory(new InventoryCraftingMachine(this).addFluidHandler("one", IOType.INPUT, 22, 11, inputCapacity)
				.addFluidHandler("two", IOType.INPUT, 78, 11, inputCapacity)
				.addFluidHandler("output", IOType.OUTPUT, 134, 17, outputCapacity));
	}

	// TODO
	// @Override
	// protected boolean canRun() {
	// return
	// this.getInventory().fluidInputPieces.get(0).getHandler().getFluidAmount() > 0
	// && this.getInventory().fluidInputPieces.get(1).getHandler().getFluidAmount()
	// > 0 && super.canRun();
	// }

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 1;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		if(block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
			return true;
		}
		else {
			validatorCallback
					.setLastError(new ValidationError("steamagerevolution.multiblock.validation.alloyforgeinterior"));
			return false;
		}
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
		return 6;
	}

	@Override
	public int getMaximumXSize() {
		return 5;
	}

	@Override
	public int getMaximumZSize() {
		return 5;
	}

	@Override
	public int getMaximumYSize() {
		return 6;
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public ItemStack getCatalyst() {
		return new ItemStack(SARObjectHolder.alloy_furnace_frame);
	}
}
