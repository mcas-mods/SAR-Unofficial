package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import com.teamacronymcoders.base.entities.EntityMinecartBase;
import com.teamacronymcoders.base.items.minecart.ItemMinecartBase;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMinecartOreCarrier extends ItemMinecartBase {

	public ItemMinecartOreCarrier() {
		super("ore_carrier");
	}

	@Override
	public EntityMinecartBase getEntityFromItem(World world, ItemStack itemStack) {
		return new EntityMinecartOreCarrier(world);
	}

}
