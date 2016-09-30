package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.registries.BlockRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ConfigRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockPneumaticTube;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockRouter;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockSender;

@Module(mod = SteamAgeRevolution.MODID)
public class ModulePneumatic extends ModuleBase {

	public static Block pneumaticTube, sender, router;

	@Override
	public String getName() {
		return "Pneumatic";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		pneumaticTube = new BlockPneumaticTube(Material.IRON, "pneumatic_tube");
		blockRegistry.registerBlock(pneumaticTube);
		sender = new BlockSender(Material.IRON, "sender");
		blockRegistry.registerBlock(sender);
		router = new BlockRouter(Material.IRON, "router");
		blockRegistry.registerBlock(router);
	}
}
