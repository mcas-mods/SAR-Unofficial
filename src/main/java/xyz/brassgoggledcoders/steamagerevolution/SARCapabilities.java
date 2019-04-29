package xyz.brassgoggledcoders.steamagerevolution;

import com.teamacronymcoders.base.capability.NBTCapStorage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.steamagerevolution.api.FurnaceFumeProducer;
import xyz.brassgoggledcoders.steamagerevolution.api.IFumeProducer;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHandler;

@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class SARCapabilities {
	@CapabilityInject(IFumeProducer.class)
	public static Capability<IFumeProducer> FUME_PRODUCER;
	@CapabilityInject(ISemisolidHandler.class)
	public static Capability<ISemisolidHandler> SEMISOLID_HANDLER;

	@SuppressWarnings("deprecation")
	public static void register() {
		CapabilityManager.INSTANCE.register(IFumeProducer.class, new Capability.IStorage<IFumeProducer>() {
			@Override
			public NBTTagCompound writeNBT(Capability<IFumeProducer> capability, IFumeProducer instance,
					EnumFacing side) {
				return new NBTTagCompound();
			}

			@Override
			public void readNBT(Capability<IFumeProducer> capability, IFumeProducer instance, EnumFacing side,
					NBTBase nbt) {

			}
		}, FurnaceFumeProducer.class);
		CapabilityManager.INSTANCE.register(ISemisolidHandler.class, new NBTCapStorage<ISemisolidHandler>(),
				SemisolidHandler::new);
	}

	@SubscribeEvent
	public static void attachTileCapabilities(AttachCapabilitiesEvent<TileEntity> event) {
		if (event.getObject() instanceof TileEntityFurnace) {
			event.addCapability(new ResourceLocation(SteamAgeRevolution.MODID, "fume_collector"),
					new FurnaceCapabilityProvider((TileEntityFurnace) event.getObject()));
		}
	}
}
