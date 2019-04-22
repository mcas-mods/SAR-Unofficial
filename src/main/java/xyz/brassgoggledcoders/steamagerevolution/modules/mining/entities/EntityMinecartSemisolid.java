package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemMinecart;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.GuiSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventoryPieceSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventorySemisolid;

public class EntityMinecartSemisolid extends EntityMinecartInventory<InventorySemisolid> {

	@ObjectHolder(SteamAgeRevolution.MODID + ":minecart_ore_carrier")
	private static final ItemMinecart cartItem = null;
	
	public EntityMinecartSemisolid(World world) {
		super(world);
		this.setInventory(new InventorySemisolid(
				new InventoryPieceSemisolid(new SemisolidHandlerCart(this, new SemisolidHolder(15)), 83, 16)));
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == SARCapabilities.SEMISOLID_HANDLER || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == SARCapabilities.SEMISOLID_HANDLER) {
			return SARCapabilities.SEMISOLID_HANDLER.cast(inventory.ore.getHandler());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiSemisolid(entityPlayer, this, "crushed_single");
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return null;
	}

	@Override
	public ItemMinecart getItem() {
		return cartItem;
	}
}
