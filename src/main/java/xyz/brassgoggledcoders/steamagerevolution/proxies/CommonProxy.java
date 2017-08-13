package xyz.brassgoggledcoders.steamagerevolution.proxies;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;

public class CommonProxy {

	public void registerModels() {}

	public void spawnSmoke(BlockPos at) {}

	public void spawnSteamJet(BlockPos at, EnumFacing f) {}

	public void spawnFX(EnumParticleTypes type, BlockPos pos) {}

	public void spawnMultiblockAssemblyFX(BlockPos min, BlockPos max) {}

}
