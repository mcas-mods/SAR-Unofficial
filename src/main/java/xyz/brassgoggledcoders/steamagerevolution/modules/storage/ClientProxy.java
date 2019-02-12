package xyz.brassgoggledcoders.steamagerevolution.modules.storage;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.MultiblockTankRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.tileentities.TileEntityTankCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.renderers.TileEntityFluidIORenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.renderers.TileEntityTrunkRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityFluidIO;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityTrunk;

public class ClientProxy implements IModuleProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @SuppressWarnings("deprecation")
    @Override
    public void init(FMLInitializationEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrunk.class, new TileEntityTrunkRenderer());
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(ModuleStorage.trunk), 0, TileEntityTrunk.class);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidIO.class, new TileEntityFluidIORenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTankCasing.class, new MultiblockTankRenderer());

        // ModelLoaderRegistry.registerLoader(ModelCanister.LoaderDynBucketz.instance);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }
}
