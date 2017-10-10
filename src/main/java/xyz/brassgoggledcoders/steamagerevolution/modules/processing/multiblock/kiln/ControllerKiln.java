package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.kiln;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.materials.ModuleMaterials;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.SARRectangularMultiblockControllerBase;

public class ControllerKiln extends SARRectangularMultiblockControllerBase {

	public BlockPos minimumInteriorPos;
	public BlockPos maximumInteriorPos;
	public FluidTank steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);

	public static final int steamPerOperation = Fluid.BUCKET_VOLUME;
	// public static final int workingTime = 2400;
	// public int currentTime = 0;

	public ControllerKiln(World world) {
		super(world);
	}

	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions =
				PositionUtils.shrinkPositionCubeBy(this.getMinimumCoord(), this.getMaximumCoord(), 1);
		this.minimumInteriorPos = interiorPositions.getLeft();
		this.maximumInteriorPos = interiorPositions.getRight();
		super.onMachineAssembled();
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;
		// if(currentTime == workingTime) {
		if(steamTank.getFluidAmount() >= steamPerOperation) {
			for(BlockPos pos : BlockPos.getAllInBox(minimumInteriorPos, maximumInteriorPos)) {
				if(!WORLD.isAirBlock(pos)) {
					WORLD.setBlockState(pos, ModuleMaterials.charcoal_block.getDefaultState());
					flag = true;
					continue;
				}
			}
		}
		if(flag) {
			steamTank.drain(steamPerOperation, true);
		}
		// currentTime = 0;
		// }
		// else {
		// currentTime++;
		// }
		return flag;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 1;
	}

	@Override
	public int getMinimumXSize() {
		return 6;
	}

	@Override
	public int getMinimumZSize() {
		return 6;
	}

	@Override
	public int getMinimumYSize() {
		return 6;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		steamTank.readFromNBT(data.getCompoundTag("steam"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("steam", steamTank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
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
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Kiln";
	}

	@Override
	public int getMaximumXSize() {
		return this.getMinimumXSize();
	}

	@Override
	public int getMaximumZSize() {
		return this.getMinimumZSize();
	}

	@Override
	public int getMaximumYSize() {
		return this.getMinimumYSize();
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		return world.isAirBlock(new BlockPos(x, y, z))
				// || OreDictionary.getOres("logWood", false).contains(new ItemStack(Item.getItemFromBlock(block)));
				// TODO
				|| block == Blocks.LOG || block == ModuleMaterials.charcoal_block;
	}
}
