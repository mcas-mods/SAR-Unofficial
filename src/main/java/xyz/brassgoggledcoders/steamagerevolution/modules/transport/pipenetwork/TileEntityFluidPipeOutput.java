package xyz.brassgoggledcoders.steamagerevolution.modules.transport.pipenetwork;

public class TileEntityFluidPipeOutput extends PipeTileEntityBase {

	@Override
	public Class<? extends PipeNetworkBase> getMultiblockControllerType() {
		return PipeNetworkBase.class;
	}

	@Override
	public void onMachineAssembled(PipeNetworkBase multiblockControllerBase) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMachineBroken() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMachineActivated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMachineDeactivated() {
		// TODO Auto-generated method stub

	}

	@Override
	public PipeNetworkBase createNewMultiblock() {
		return new ControllerFluidPipeNetwork(getWorld());
	}

}
