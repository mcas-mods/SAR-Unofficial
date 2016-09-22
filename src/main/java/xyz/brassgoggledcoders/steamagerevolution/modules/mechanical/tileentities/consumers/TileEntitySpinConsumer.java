package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers;

import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntitySpinMachine;

public abstract class TileEntitySpinConsumer extends TileEntitySpinMachine {

	private int workingSpeed;
	private int dangerSpeed;

	public TileEntitySpinConsumer() {
		this(100);
	}

	public TileEntitySpinConsumer(int workingSpeed) {
		this(workingSpeed, 0);
	}

	public TileEntitySpinConsumer(int workingSpeed, int dangerSpeed) {
		super();
		this.workingSpeed = workingSpeed;
		this.dangerSpeed = dangerSpeed;
	}

	@Override
	public void updateTile() {
		if(this.getWorld().isRemote)
			return;
		// TODO
		if(dangerSpeed > 0 && handler.getSpeed() >= dangerSpeed) {
			tickAtDangerSpeed();
		}
		else if(handler.getSpeed() >= workingSpeed) {
			tickAtWorkSpeed();
		}
		// Spin down
		if(this.handler.getSpeed() > 0)
			this.handler.decrSpeed();
	}

	public abstract void tickAtWorkSpeed();

	public abstract void tickAtDangerSpeed();

}
