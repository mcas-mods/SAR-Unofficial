package xyz.brassgoggledcoders.steamagerevolution.modules.transport.pipenetwork;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ControllerFluidPipeNetwork extends PipeNetworkBase {

	protected ControllerFluidPipeNetwork(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttachedPartWithMultiblockData(IPipe part, NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockAdded(IPipe newPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockRemoved(IPipe oldPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineAssembled() {
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
	protected void onAssimilate(PipeNetworkBase assimilated) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilated(PipeNetworkBase assimilator) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean updateServer() {
		// TODO Auto-generated method stub
		return false;
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
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

}
