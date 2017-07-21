package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.PositionUtils;

public class ControllerCrucible extends RectangularMultiblockControllerBase implements ISmartTankCallback {

	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;
	public ItemStackHandler solid = new ItemStackHandler();
	public FluidTankSmart tank = new FluidTankSmart(0, this);
	public int stateChangeTime = 2400;

	public ControllerCrucible(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean updateServer() {
		ItemStack stack = this.solid.getStackInSlot(0);

		// Melting Logic TODO Cache this check
		// if(TileEntityCastingBench.getWorld().getBlockState(getPos().down()).getMaterial() == Material.LAVA) {
		if(!stack.isEmpty()) {
			String[] splitName = null;
			// TODO Caching. This *should* never change at runtime.
			for(int oreId : OreDictionary.getOreIDs(stack)) {
				splitName = OreDictionary.getOreName(oreId).split("(?=[A-Z])");
				if(splitName.length != 2)
					return false;
				if(FluidRegistry.isFluidRegistered(splitName[1].toLowerCase())) {
					break;
				}
			}
			if(splitName != null) {
				if(stateChangeTime == 0) {
					Fluid fluid = FluidRegistry.getFluid(splitName[1].toLowerCase());
					int value = TileEntityCastingBench.getValueFromName(splitName[0].toLowerCase()) * stack.getCount();
					if(value != 0) {
						FluidStack toInsert = new FluidStack(fluid, value);
						if(tank.fill(toInsert, false) == value) {
							tank.fill(toInsert, true);
							stack.shrink(1);
							return true;
						}
					}
				}
				else {
					stateChangeTime--;
					return true;
				}
			}
		}
		// Cooling Logic
		// else {
		// if(this.tank.getFluid() != null && this.tank.drain(TileEntityCastingBenchVALUE_BLOCK, false).amount ==
		// TileEntityCastingBenchVALUE_BLOCK
		// && stack.isEmpty()) {
		// String oreName = "block" + StringUtils.capitalize(FluidRegistry.getFluidName(this.tank.getFluid()));
		// if(OreDictionary.doesOreNameExist(oreName)) {
		// if(stateChangeTime == 0) {
		// this.tank.drain(TileEntityCastingBenchVALUE_BLOCK, true);
		// ItemStack toInsert = OreDictionary.getOres(oreName).get(0);
		// toInsert.setCount(1);
		// this.internal.insertItem(0, toInsert, false);
		// stateChangeTime = 2400;
		// }
		// else {
		// stateChangeTime--;
		// }
		// }
		// }
		// }
		return false;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		// TODO Auto-generated method stub

	}

	// FIXME Caching
	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions =
				PositionUtils.shrinkPositionCubeBy(this.getMinimumCoord(), this.getMaximumCoord(), 1);
		this.minimumInteriorPos = interiorPositions.getLeft();
		this.maximumInteriorPos = interiorPositions.getRight();

		int blocksInside = 0;
		// TODO Expensive for loop just to increment an integer
		for(BlockPos pos : BlockPos.getAllInBox(minimumInteriorPos, maximumInteriorPos)) {
			blocksInside++;
		}
		// Size internal tank accordingly
		tank = new FluidTankSmart(tank.getFluid(), blocksInside * Fluid.BUCKET_VOLUME * 16, this);
	}

	@Override
	protected void onMachineRestored() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachinePaused() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineDisassembled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 17;
	}

	@Override
	protected int getMaximumXSize() {
		return 5;
	}

	@Override
	protected int getMaximumZSize() {
		return 5;
	}

	@Override
	protected int getMaximumYSize() {
		return 5;
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateClient() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFluid(FluidStack fluid) {
		// TODO Auto-generated method stub

	}

}
