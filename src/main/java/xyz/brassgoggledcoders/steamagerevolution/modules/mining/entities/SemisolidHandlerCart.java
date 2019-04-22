package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventorySemisolid;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketSemisolidCart;

public class SemisolidHandlerCart extends SemisolidHandler {

	EntityMinecartInventory<InventorySemisolid> cart;

	public SemisolidHandlerCart(EntityMinecartInventory<InventorySemisolid> cart, SemisolidHolder semisolidHolder) {
		super(semisolidHolder);
		this.cart = cart;
	}

	//TODO USe DataManager so this is autosynced
	@Override
	public void onContentsChanged() {
		if (!this.cart.getEntityWorld().isRemote) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketSemisolidCart(cart.getEntityId(), cart.getInventory().ore.getHandler().serializeNBT()),
					cart);
		}
	}

}
