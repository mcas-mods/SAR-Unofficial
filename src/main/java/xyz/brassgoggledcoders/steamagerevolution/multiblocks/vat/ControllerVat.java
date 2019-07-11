package xyz.brassgoggledcoders.steamagerevolution.multiblocks.vat;

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
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockRecipe;

public class ControllerVat extends MultiblockRecipe<InventoryRecipe> {

	public static int outputCapacity = Fluid.BUCKET_VOLUME * 8;
	public static int inputCapacity = outputCapacity * 3;
	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;
	AxisAlignedBB bounds;

	public ControllerVat(World world) {
		super(world);
		setInventory(new InventoryRecipe(this)
				.addItemHandler("input", IOType.INPUT, new int[] { 88, 88, 88 }, new int[] { 11, 32, 53 })
				// FIXME
				.addFluidHandler("tank1", IOType.INPUT, 12, 9, inputCapacity)
				.addFluidHandler("tank2", IOType.INPUT, 37, 9, inputCapacity)
				.addFluidHandler("tank3", IOType.INPUT, 62, 9, inputCapacity)
				.addFluidHandler("outputTank", IOType.OUTPUT, 143, 9, outputCapacity));
	}

	@Override
	protected void onTick() {
		for(Entity entity : WORLD.getEntitiesWithinAABB(Entity.class, bounds)) {
			if(entity instanceof EntityItem) {
				EntityItem item = (EntityItem) entity;
				if(ItemHandlerHelper
						.insertItem(this.getInventory().getItemPiece("input").getHandler(), item.getItem(), true)
						.isEmpty()) {
					ItemHandlerHelper.insertItem(this.getInventory().getItemPiece("input").getHandler(), item.getItem(),
							false);
					item.setDead();
				}
			}
			// Simulate contact with fluid in vat when an entity falls in.
			// TODO change bounds based on fluid fill level
			FluidStack fluid = null;
			for(FluidTankSync tank : this.getInventory().getFluidHandlers()) {
				if(tank.getFluidAmount() > 0) {
					fluid = tank.getFluid();
					break;
				}
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
