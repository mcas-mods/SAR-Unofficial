package xyz.brassgoggledcoders.steamagerevolution;

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
import xyz.brassgoggledcoders.steamagerevolution.api.fumeproducer.EmptyFumeProducer;
import xyz.brassgoggledcoders.steamagerevolution.api.fumeproducer.FurnaceFumeProducer;
import xyz.brassgoggledcoders.steamagerevolution.api.fumeproducer.IFumeProducer;
import xyz.brassgoggledcoders.steamagerevolution.api.mechanical.IMechanicalPower;
import xyz.brassgoggledcoders.steamagerevolution.api.mechanical.MechanicalPower;

import javax.annotation.Nullable;

@EventBusSubscriber
public class SARCapabilities {
    @CapabilityInject(IFumeProducer.class)
    public static Capability<IFumeProducer> FUME_PRODUCER;
    @CapabilityInject(IMechanicalPower.class)
    public static Capability<IMechanicalPower> MECHANICAL_POWER;

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
        }, EmptyFumeProducer::new);

        CapabilityManager.INSTANCE.register(IMechanicalPower.class, new Capability.IStorage<IMechanicalPower>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IMechanicalPower> capability, IMechanicalPower instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<IMechanicalPower> capability, IMechanicalPower instance, EnumFacing side, NBTBase nbt) {

            }
        }, MechanicalPower::new);
    }

	@SubscribeEvent
	public static void attachTileCapabilities(AttachCapabilitiesEvent<TileEntity> event) {
		if(event.getObject() instanceof TileEntityFurnace) {
			event.addCapability(new ResourceLocation(SteamAgeRevolution.MODID, "fume_collector"),
					new FurnaceCapabilityProvider((TileEntityFurnace) event.getObject()));
		}
	}
}
