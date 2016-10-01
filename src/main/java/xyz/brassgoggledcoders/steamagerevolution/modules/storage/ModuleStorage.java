package xyz.brassgoggledcoders.steamagerevolution.modules.storage;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.registries.BlockRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ConfigRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks.BlockFluidIO;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks.BlockTrunk;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.items.ItemCanister;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleStorage extends ModuleBase {

	public static Block trunk, fluidIO;
	public static Item canister;

	@Override
	public String getName() {
		return "Storage";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		trunk = new BlockTrunk(Material.WOOD, "trunk");
		blockRegistry.registerBlock(trunk);
		fluidIO = new BlockFluidIO();
		blockRegistry.registerBlock(fluidIO);
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		canister = new ItemCanister("canister", 10000);
		itemRegistry.registerItem(canister);
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.storage.ClientProxy";
	}
}
