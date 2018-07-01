package xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockBase;

public class ControllerTank extends SARMultiblockBase implements ISmartTankCallback {

	public BlockPos minimumInteriorPos;
	public BlockPos maximumInteriorPos;
	public FluidTankSmart tank;

	public ControllerTank(World world) {
		super(world);
		tank = new FluidTankSmart(0, this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		tank.readFromNBT(data);
	}

	// TODO Caching
	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = PositionUtils.shrinkPositionCubeBy(getMinimumCoord(),
				getMaximumCoord(), 1);
		minimumInteriorPos = interiorPositions.getLeft();
		maximumInteriorPos = interiorPositions.getRight();

		int blocksInside = 0;
		// TODO Expensive for loop just to increment an integer
		for(BlockPos pos : BlockPos.getAllInBox(minimumInteriorPos, maximumInteriorPos)) {
			blocksInside++;
		}
		// Size internal tank accordingly
		tank = new FluidTankSmart(tank.getFluid(), blocksInside * Fluid.BUCKET_VOLUME * 16, this);
		// FMLLog.warning("" + tank.getCapacity());
		super.onMachineAssembled();
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
	}

	@Override
	public int getMaximumXSize() {
		// TODO Auto-generated method stub
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
		return 10;
	}

	@Override
	protected boolean updateServer() {
		return false;
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		tank.writeToNBT(data);
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		tank.setFluid(message.fluid);
	}

	@Override
	public String getName() {
		return "Tank";
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank) {
		if(tank instanceof MultiFluidTank) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketMultiFluidUpdate(getReferenceCoord(), ((MultiFluidTank) tank), tank.getId()),
					getReferenceCoord(), WORLD.provider.getDimension());
		}
		else {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(getReferenceCoord(), tank.getFluid(), tank.getId()), getReferenceCoord(),
					WORLD.provider.getDimension());
		}
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiSingleTank(entityPlayer, tank);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerSingleTank(entityPlayer, null);
	}
}
