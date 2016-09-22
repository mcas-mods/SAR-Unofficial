package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityCartBooster extends TileEntitySpinConsumer {
	public TileEntityCartBooster() {
		super(30, 100);
	}

	@Override
	public void tickAtWorkSpeed() {
		List<EntityMinecart> carts = this.getWorld().getEntitiesWithinAABB(EntityMinecart.class,
				new AxisAlignedBB(this.getPos()).expand(0, 1, 0));

		for(EntityMinecart cart : carts) {
			cart.setCurrentCartSpeedCapOnRail(cart.getMaxCartSpeedOnRail());
			cart.motionX *= this.handler.getSpeed();
			cart.motionZ *= this.handler.getSpeed();
		}
	}

	@Override
	public void tickAtDangerSpeed() {
		List<EntityMinecart> carts = this.getWorld().getEntitiesWithinAABB(EntityMinecart.class,
				new AxisAlignedBB(this.getPos()).expand(0, 1, 0));

		for(EntityMinecart cart : carts) {
			// TODO Make fire time and explosion strength proportional to speed.
			if(!cart.getPassengers().isEmpty()) {
				Entity entity = cart.getPassengers().get(0);
				if(entity != null)
					entity.setFire(25);
			}
			// Kaboom.
			cart.getEntityWorld().createExplosion(cart, cart.posX, cart.posY, cart.posZ, 10, false);
			cart.onKillCommand();
		}
	}
}
