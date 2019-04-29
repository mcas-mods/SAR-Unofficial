package xyz.brassgoggledcoders.steamagerevolution.proxies;

import com.teamacronymcoders.base.util.RenderingUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
	@Override
	public void spawnSmoke(BlockPos at) {
		World world = Minecraft.getMinecraft().world;
		if (world == null) {
			return;
		}
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, at.getX() + 0.5F, at.getY(), at.getZ() + 0.5F, 0, 0, 0);
		for (int i = 0; i < 8; i++) {
			if (world.rand.nextBoolean()) {
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, at.getX() + 0.5F, at.getY(), at.getZ() + 0.5F, 0, 0,
						0);
			}
		}
	}

	@Override
	public void spawnSteamJet(BlockPos at, EnumFacing f) {
		World world = Minecraft.getMinecraft().world;
		if (world == null) {
			return;
		}
		for (int i = 0; i < 20; i++) {
			// TODO Random offsets
			float[] v = RenderingUtils.directionalVelocitiesOfMagnitude(f.getDirectionVec(), 0.1F);
			world.spawnParticle(EnumParticleTypes.CLOUD, at.getX() + 0.5F, at.getY() + 0.5F, at.getZ() + 0.5F, v[0],
					v[1], v[2]);
		}
	}

	@Override
	public void spawnFX(EnumParticleTypes type, BlockPos pos) {
		World world = Minecraft.getMinecraft().world;
		if (type == EnumParticleTypes.PORTAL) {
			for (int j = 0; j < 70; ++j) {
				world.spawnParticle(type, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), 0, 0, 0);
			}
		} else if (type == EnumParticleTypes.FLAME) {
			for (int j = 0; j < 5; ++j) {
				world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), world.rand.nextGaussian(),
						world.rand.nextGaussian(), world.rand.nextGaussian());
			}
			for (int j = 0; j < 5; ++j) {
				world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + (-0.2 + world.rand.nextDouble()), pos.getY(),
						pos.getZ() + (-0.2 + world.rand.nextDouble()), -world.rand.nextGaussian(),
						-world.rand.nextGaussian(), -world.rand.nextGaussian());
			}
		}
	}

	@Override
	public void spawnMultiblockAssemblyFX(BlockPos min, BlockPos max) {
		World world = Minecraft.getMinecraft().world;
		if (world != null) {
			for (BlockPos pos : BlockPos.getAllInBox(min, max)) {
				for (int i = 0; i < 7; i++) {
					world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5F + world.rand.nextGaussian(),
							pos.getY() + 0.5F, pos.getZ() + 0.5F + world.rand.nextGaussian(), 0, 0, 0);
				}
			}
		}
	}
}
