package xyz.brassgoggledcoders.steamagerevolution.modules.vanity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.registries.BlockRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ConfigRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.vanity.blocks.BlockTrunk;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleVanity extends ModuleBase {
	public static ItemWatch watch;
	public static ItemTeacup teacup;

	public static Block trunk;

	@Override
	public String getName() {
		return "Vanity";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		trunk = new BlockTrunk(Material.WOOD, "trunk");
		blockRegistry.registerBlock(trunk);
	}

}
