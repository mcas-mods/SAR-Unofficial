package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import java.util.LinkedHashMap;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public class TileEntityWaterTurbine extends TileEntitySpinMachine implements IDebuggable {

	protected ISpinHandler handler = new SpinHandler();

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capObject, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY)
			return SARAPI.SPIN_HANDLER_CAPABILITY.cast(handler);

		return super.getCapability(capObject, side);
	}

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		if(getWorld().getBlockState(getPos().up()).getMaterial() == Material.WATER)
			this.handler.setSpeed(100);
		else // Spin down
		if(handler.getSpeed() > 0)
			this.handler.decrSpeed();
	}

	@Override
	public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
		debugStrings.put("speed", "" + this.handler.getSpeed());
		return debugStrings;
	}
}
