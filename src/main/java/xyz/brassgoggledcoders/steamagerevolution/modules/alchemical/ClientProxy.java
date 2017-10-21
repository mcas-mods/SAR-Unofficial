package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraftforge.fml.common.event.*;

public class ClientProxy implements IModuleProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {
		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVatFrame.class, new MultiblockVatTankRenderer());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}
