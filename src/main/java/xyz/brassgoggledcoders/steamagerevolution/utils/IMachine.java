package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMachine {
	public World getMachineWorld();

	public BlockPos getMachinePos();
}
