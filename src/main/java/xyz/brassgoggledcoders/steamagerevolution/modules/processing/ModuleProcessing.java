package xyz.brassgoggledcoders.steamagerevolution.modules.processing;

import static xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.MultiblockBuilder.allButInterior;
import static xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.MultiblockBuilder.allFaces;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.ControllerSteamFurnace;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.MultiblockBuilder;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleProcessing extends ModuleBase {

	@Override
	public String getName() {
		return "Processing";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		// blockRegistry.register(new BlockKilnFrame(Material.ROCK, "kiln_frame"));
		// blockRegistry.register(new BlockKilnSteamInput(Material.ROCK, "kiln_steam_input"));
		// blockRegistry.register(new BlockKilnWindow(Material.GLASS, "kiln_window"));
		// blockRegistry.register(new BlockKilnDoor(Material.IRON, "kiln_door"));

		new MultiblockBuilder<ControllerSteamFurnace>(blockRegistry, ControllerSteamFurnace.class,
				ControllerSteamFurnace::new, Material.ANVIL).addNewPart("furnace_casing", allButInterior)
						.addNewItemWrapperPart("furnace_item_input", allFaces, "input")
						.addNewItemWrapperPart("furnace_item_output", allFaces, "output")
						.addNewFluidWrapperPart("furnace_steam_input", allFaces, "steam").build();
	}

}
