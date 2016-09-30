package xyz.brassgoggledcoders.steamagerevolution.modules.management;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.registries.BlockRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ConfigRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.management.blocks.BlockPneumaticTube;
import xyz.brassgoggledcoders.steamagerevolution.modules.management.blocks.BlockRouter;
import xyz.brassgoggledcoders.steamagerevolution.modules.management.blocks.BlockSender;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleManagement extends ModuleBase {

	public static Block pneumatic_tube, sender, router;

	@Override
	public String getName() {
		return "Management";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		pneumatic_tube = new BlockPneumaticTube(Material.IRON, "pneumatic_tube");
		blockRegistry.registerBlock(pneumatic_tube);
		sender = new BlockSender(Material.IRON, "sender");
		blockRegistry.registerBlock(sender);
		router = new BlockRouter(Material.IRON, "router");
		blockRegistry.registerBlock(router);
	}
}
