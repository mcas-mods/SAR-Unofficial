package xyz.brassgoggledcoders.steamagerevolution.multiblock.vat;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerVat extends SARMultiblockInventory<InventoryBasic> {

	public static int outputCapacity = Fluid.BUCKET_VOLUME * 8;
	public static int inputCapacity = outputCapacity * 3;
	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;
	AxisAlignedBB bounds;

	public ControllerVat(World world) {
		super(world);
		setInventory(new InventoryBasic()
				.setItemInput(new int[] { 88, 88, 88 }, new int[] { 11, 32, 53 },
						new ItemStackHandlerExtractSpecific(3))
				.setFluidInputs(new int[] { 12, 37, 62 }, new int[] { 9, 9, 9 },
						new MultiFluidTank(inputCapacity, this, 3))
				.setFluidOutput(143, 9, new FluidTankSmart(outputCapacity, this)));
	}

	@Override
	protected void onTick() {
		for(Entity entity : WORLD.getEntitiesWithinAABB(Entity.class, bounds)) {
			if(entity instanceof EntityItem) {
				EntityItem item = (EntityItem) entity;
				if(ItemHandlerHelper.insertItem(inventory.getInputItemHandler(), item.getItem(), true).isEmpty()) {
					ItemHandlerHelper.insertItem(inventory.getInputItemHandler(), item.getItem(), false);
					item.setDead();
				}
			}
			// Simulate contact with fluid in vat when an entity falls in.
			// TODO change bounds based on fluid fill level
			FluidStack fluid = null;
			if(inventory.getOutputFluidHandler().getFluid() != null) {
				fluid = inventory.getOutputFluidHandler().getFluid();
			}
			else if(inventory.getInputFluidHandler().getFluid() != null) {
				fluid = inventory.getInputFluidHandler().getFluid();
			}
			if(fluid != null && fluid.getFluid() != null && fluid.getFluid().getBlock() != null) {
				if(fluid.getFluid().getTemperature() >= FluidRegistry.LAVA.getTemperature()) {
					entity.setFire(5);
				}
				Block fluidBlock = fluid.getFluid().getBlock();
				fluidBlock.onEntityCollision(WORLD, getReferenceCoord(), fluidBlock.getDefaultState(), entity);

			}
		}
	}

	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils
				.shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
		minimumInteriorPos = interiorPositions.getLeft();
		maximumInteriorPos = interiorPositions.getRight();
		bounds = new AxisAlignedBB(getMinimumCoord(), getMaximumCoord());
		super.onMachineAssembled();
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 40;
	}

	@Override
	public int getMinimumXSize() {
		return 4;
	}

	@Override
	public int getMinimumYSize() {
		return 3;
	}

	@Override
	public int getMinimumZSize() {
		return 4;
	}

	@Override
	public int getMaximumXSize() {
		return 10;
	}

	@Override
	public int getMaximumZSize() {
		return 10;
	}

	@Override
	public int getMaximumYSize() {
		return 10;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return WORLD.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public String getName() {
		return "Vat";
	}
}
