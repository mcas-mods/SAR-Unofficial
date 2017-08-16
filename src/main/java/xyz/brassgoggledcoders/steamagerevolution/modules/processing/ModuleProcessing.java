package xyz.brassgoggledcoders.steamagerevolution.modules.processing;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.blocks.BlockFurnaceCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.blocks.BlockFurnaceItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.blocks.BlockFurnaceItemOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.blocks.BlockFurnaceModeToggle;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.blocks.BlockFurnaceSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.kiln.BlockKilnDoor;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.kiln.BlockKilnFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.kiln.BlockKilnSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.kiln.BlockKilnWindow;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleProcessing extends ModuleBase {

	@Override
	public String getName() {
		return "Processing";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		blockRegistry.register(new BlockKilnFrame(Material.ROCK, "kiln_frame"));
		blockRegistry.register(new BlockKilnSteamInput(Material.ROCK, "kiln_steam_input"));
		blockRegistry.register(new BlockKilnWindow(Material.GLASS, "kiln_window"));
		blockRegistry.register(new BlockKilnDoor(Material.IRON, "kiln_door"));

		blockRegistry.register(new BlockFurnaceCasing(Material.IRON, "furnace_casing"));
		blockRegistry.register(new BlockFurnaceItemInput(Material.IRON, "furnace_iteminput"));
		blockRegistry.register(new BlockFurnaceItemOutput(Material.IRON, "furnace_itemoutput"));
		blockRegistry.register(new BlockFurnaceSteamInput(Material.IRON, "furnace_steaminput"));
		blockRegistry.register(new BlockFurnaceModeToggle(Material.IRON, "furnace_monitor"));
	}

}
