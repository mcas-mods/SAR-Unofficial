package xyz.brassgoggledcoders.steamagerevolution.modules.mining.grinder;

import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventorySemisolid;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerGrinder extends SARMultiblockInventory<InventorySemisolid> {

	protected ControllerGrinder(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Grinder";
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumXSize() {
		return 3;
	}

	@Override
	public int getMaximumZSize() {
		return 3;
	}

	@Override
	public int getMaximumYSize() {
		return 3;
	}

}
