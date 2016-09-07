package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;

public class TileEntityCartBooster extends TileEntitySpinConsumer {
	@Override
	public void updateTile() {

		List<EntityMinecart> carts =
				this.getWorld().getEntitiesWithinAABB(EntityMinecart.class, Block.FULL_BLOCK_AABB.expandXyz(3));

		for(EntityMinecart cart : carts) {
			if(cart.canUseRail()) {
				cart.setVelocity(cart.motionX * 3, cart.motionY, cart.motionZ * 3);
				if(this.handler.getSpeed() > 100)
					cart.setFire(1000);
			}
		}

		super.updateTile();
	}
}
