package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityCastingBench;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.PositionUtils;

public class ControllerCrucible extends RectangularMultiblockControllerBase
		implements ISmartTankCallback, IMultiblockControllerInfo {

	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;
	public ItemStackHandler solid = new ItemStackHandler();
	public FluidTankSmart tank = new FluidTankSmart(0, this);
	public FluidTankSingleSmart steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME, "steam", this);

	public static int steamPerOperation = Fluid.BUCKET_VOLUME / 10;

	public ControllerCrucible(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		if(steamTank.getFluidAmount() >= steamPerOperation) {
			if(TileEntityCastingBench.meltMetal(solid, tank)) {
				steamTank.drain(steamPerOperation, true);
				return true;
			}
		}
		return false;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		solid.deserializeNBT(data.getCompoundTag("inventory"));
		tank.readFromNBT(data.getCompoundTag("tank"));
		steamTank.readFromNBT(data.getCompoundTag("steamTank"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("inventory", solid.serializeNBT());
		data.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		data.setTag("steamTank", steamTank.writeToNBT(new NBTTagCompound()));
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
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
				new PacketFluidUpdate(this.getReferenceCoord(), tank.getFluid()), this.getReferenceCoord(),
				WORLD.provider.getDimension());
	}

	@Override
	public void updateFluid(FluidStack fluid) {
		if(fluid.getFluid().equals(FluidRegistry.getFluid("steam")))
			steamTank.setFluid(fluid);
		else
			tank.setFluid(fluid);
	}

	@Override
	public String getName() {
		return "Crucible";
	}

	@Override
	public int getMaxXSize() {
		return this.getMaximumXSize();
	}

	@Override
	public int getMaxYSize() {
		return this.getMaximumYSize();
	}

	@Override
	public int getMaxZSize() {
		return this.getMaximumZSize();
	}

}
