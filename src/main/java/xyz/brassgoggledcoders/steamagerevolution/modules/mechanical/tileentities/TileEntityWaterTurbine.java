package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import net.minecraft.block.material.Material;

public class TileEntityWaterTurbine extends TileEntitySpinMachine {

	public TileEntityWaterTurbine() {
		super();
	}

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		if(getWorld().getBlockState(getPos().up()).getMaterial() == Material.WATER)
			this.handler.setSpeed(100);
		else // Spin down
		if(this.handler.getSpeed() > 0)
			this.handler.decrSpeed();
	}
}
