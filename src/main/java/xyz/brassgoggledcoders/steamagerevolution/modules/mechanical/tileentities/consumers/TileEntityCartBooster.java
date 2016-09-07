package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityCartBooster extends TileEntitySpinConsumer {
	@Override
	public void updateTile() {

		List<EntityMinecart> carts = this.getWorld().getEntitiesWithinAABB(EntityMinecart.class,
				new AxisAlignedBB(this.getPos()).expandXyz(3));

		for(EntityMinecart cart : carts) {
			// if(cart.canUseRail()) {
			cart.setVelocity(cart.motionX * 3, cart.motionY, cart.motionZ * 3);
			if(this.handler.getSpeed() > 100) {
				// TODO Make fire time and explosion strength proportional to speed.
				if(!cart.getPassengers().isEmpty()) {
					Entity entity = cart.getPassengers().get(0);
					if(entity != null)
						entity.setFire(25);
				}
				cart.onKillCommand();
				cart.getEntityWorld().createExplosion(cart, cart.posX, cart.posY, cart.posZ, 10, false);
			}
			// }
		}

		super.updateTile();
	}
}
