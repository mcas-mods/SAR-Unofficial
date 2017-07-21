package xyz.brassgoggledcoders.steamagerevolution.modules.storage;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.MultiblockTankRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.TileEntityTankCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.renderers.TileEntityBasicFluidTankRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.renderers.TileEntityTrunkRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityBasicFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityTrunk;

public class ClientProxy implements IModuleProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@SuppressWarnings("deprecation")
	@Override
	public void init(FMLInitializationEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrunk.class, new TileEntityTrunkRenderer());
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(ModuleStorage.trunk), 0,
				xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityTrunk.class);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModuleStorage.trunk), 0,
				new ModelResourceLocation("steamagerevolution:trunk", "normal"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicFluidTank.class,
				new TileEntityBasicFluidTankRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTankCasing.class, new MultiblockTankRenderer());

		// ModelLoaderRegistry.registerLoader(ModelCanister.LoaderDynBucketz.instance);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}
