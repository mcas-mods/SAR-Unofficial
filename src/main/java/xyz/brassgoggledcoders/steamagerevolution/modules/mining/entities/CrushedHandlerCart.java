package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.ICrushedHolder;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketCrushedCartSync;

public class CrushedHandlerCart extends CrushedHandler {
	
	EntityMinecartOreCarrier cart;
	
	public CrushedHandlerCart(EntityMinecartOreCarrier carrier, ICrushedHolder... holders) {
		super(holders);
		this.cart = carrier;
	}
	
	@Override
	public void onContentsChanged() {
		if(!this.cart.getEntityWorld().isRemote) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(new PacketCrushedCartSync(cart.getEntityId(), cart.getInventory().ore.getHandler().serializeNBT()), cart);
		}
	}

}
