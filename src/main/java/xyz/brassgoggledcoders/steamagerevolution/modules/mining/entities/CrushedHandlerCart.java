package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketSemisolidCart;

public class CrushedHandlerCart extends SemisolidHandler {

	EntityMinecartSemisolid cart;

	public CrushedHandlerCart(EntityMinecartSemisolid carrier, ISemisolidHolder... holders) {
		super(holders);
		this.cart = carrier;
	}

	@Override
	public void onContentsChanged() {
		if (!this.cart.getEntityWorld().isRemote) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketSemisolidCart(cart.getEntityId(), cart.getInventory().ore.getHandler().serializeNBT()),
					cart);
		}
	}

}
