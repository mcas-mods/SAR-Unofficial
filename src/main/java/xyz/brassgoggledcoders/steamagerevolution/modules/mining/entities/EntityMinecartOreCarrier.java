package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.entities.EntityMinecartBase;
import com.teamacronymcoders.base.guisystem.GuiOpener;
import com.teamacronymcoders.base.guisystem.IHasGui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemMinecart;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedHolder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.GuiOreMinecart;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartOreCarrier;

public class EntityMinecartOreCarrier extends EntityMinecartBase implements IHasGui {

	@ObjectHolder(SteamAgeRevolution.MODID + ":minecart_ore_carrier")
	private static ItemMinecartOreCarrier itemMinecartOreCarrier;
	
	CrushedHandler handler = new CrushedHandler(new CrushedHolder(15));
	
	public EntityMinecartOreCarrier(World world) {
		super(world);
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == SARCapabilities.CRUSHED_HANDLER || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == SARCapabilities.CRUSHED_HANDLER) {
			return SARCapabilities.CRUSHED_HANDLER.cast(handler);
		}
		return super.getCapability(capability, facing);
	}
	
    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
        	GuiOpener.openEntityGui(SteamAgeRevolution.instance, this, player, world);
        	return true;
        }
        else {
        	return super.processInitialInteract(player, hand);
        }
    }

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiOreMinecart(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemMinecart getItem() {
		return itemMinecartOreCarrier;
	}

}
