package xyz.brassgoggledcoders.steamagerevolution.modules.mining.items;

import com.teamacronymcoders.base.entities.EntityMinecartBase;
import com.teamacronymcoders.base.items.minecart.ItemMinecartBase;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartSemisolid;

public class ItemMinecartSemisolid extends ItemMinecartBase {

	public ItemMinecartSemisolid() {
		super("ore_carrier");
	}

	@Override
	public EntityMinecartBase getEntityFromItem(World world, ItemStack itemStack) {
		return new EntityMinecartSemisolid(world);
	}

}
