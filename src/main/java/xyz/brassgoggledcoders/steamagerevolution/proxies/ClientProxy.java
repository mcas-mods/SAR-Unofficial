package xyz.brassgoggledcoders.steamagerevolution.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
	@Override
	public void spawnSmoke(BlockPos at) {
		World world = Minecraft.getMinecraft().theWorld;
		if(world == null)
			return;
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, at.getX() + 0.5F, at.getY(), at.getZ() + 0.5F, 0, 0, 0);
		for(int i = 0; i < 8; i++) {
			if(world.rand.nextBoolean())
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, at.getX() + 0.5F, at.getY(), at.getZ() + 0.5F, 0, 0,
						0);
		}
	}
}
