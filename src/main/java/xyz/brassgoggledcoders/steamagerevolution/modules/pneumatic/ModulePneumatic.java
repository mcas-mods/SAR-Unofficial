package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registry.BlockRegistry;
import com.teamacronymcoders.base.registry.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockPneumaticTube;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockRouter;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockSender;

@Module(value = SteamAgeRevolution.MODID)
public class ModulePneumatic extends ModuleBase {

	public static Block pneumaticTube, sender, router;

	@Override
	public String getName() {
		return "Pneumatic";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		pneumaticTube = new BlockPneumaticTube(Material.IRON, "pneumatic_tube");
		blockRegistry.register(pneumaticTube);
		sender = new BlockSender(Material.IRON, "sender");
		blockRegistry.register(sender);
		router = new BlockRouter(Material.IRON, "router");
		blockRegistry.register(router);
	}
}
