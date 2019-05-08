package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import com.teamacronymcoders.base.Base;
import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.MultiblockVatTankRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.tileentities.TileEntityVatFrame;

public class ClientProxy implements IModuleProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVatFrame.class, new MultiblockVatTankRenderer());
		Base.instance.getLibProxy().registerFluidModel(FluidRegistry.getFluid("sulphur_dioxide").getBlock(),
				new ResourceLocation(SteamAgeRevolution.MODID, "sulphur_dioxide"));
		Base.instance.getLibProxy().registerFluidModel(FluidRegistry.getFluid("sulphuric_acid").getBlock(),
				new ResourceLocation(SteamAgeRevolution.MODID, "sulphuric_acid"));
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
}
