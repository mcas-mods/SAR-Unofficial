package xyz.brassgoggledcoders.steamagerevolution.multiblocks.distiller;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerDistiller extends SARMultiblockInventory<InventoryRecipe> {

	public static int tankCapacity = Fluid.BUCKET_VOLUME * 8;

	public ControllerDistiller(World world) {
		super(world);
		setInventory(new InventoryRecipe().addFluidInput("fluidInput", 41, 9, new FluidTankSmart(tankCapacity, this))
				.addItemOutput("itemOutput", new int[] { 149 }, new int[] { 32 },
						new ItemStackHandlerSmart(1, this, IOType.INPUT))
				.addFluidOutput("itemOutput", 97, 9, new FluidTankSmart(tankCapacity, this))
				.setSteamTank(10, 9, Fluid.BUCKET_VOLUME * 16, this)
				.setProgressBar(new InventoryPieceProgressBar(67, 32)));
	}

	@Override
	public String getName() {
		return "Distiller";
	}

	@Override
	protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
		BlockPos first = getMinimumCoord();
		BlockPos second = new BlockPos(getMaximumCoord().getX(), getMinimumCoord().getY(), getMaximumCoord().getZ());

		// Get all blocks in bottom layer of machine & check they're radiators
		for(BlockPos pos : BlockPos.getAllInBox(first, second)) {
			if(WORLD.getBlockState(pos).getBlock() != SARObjectHolder.distiller_radiator) {
				validatorCallback.setLastError(
						new ValidationError("steamagerevolution.multiblock.validation.distiller_radiator"));
				return false;
			}
		}
		// Same for second layer, check they're hotplates
		for(BlockPos pos : BlockPos.getAllInBox(first.up(), second.up())) {
			if(WORLD.getBlockState(pos).getBlock() != SARObjectHolder.distiller_hotplate) {
				validatorCallback.setLastError(
						new ValidationError("steamagerevolution.multiblock.validation.distiller_hotplate"));
				return false;
			}
		}

		return super.isMachineWhole(validatorCallback);
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 34;
	}

	@Override
	public int getMinimumXSize() {
		return 3;
	}

	@Override
	public int getMinimumYSize() {
		return 5;
	}

	@Override
	public int getMinimumZSize() {
		return 3;
	}

	@Override
	public int getMaximumXSize() {
		return 10;
	}

	@Override
	public int getMaximumZSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getMaximumYSize() {
		// TODO Auto-generated method stub
		return 6;
	}

}
