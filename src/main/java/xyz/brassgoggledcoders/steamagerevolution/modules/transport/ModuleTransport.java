package xyz.brassgoggledcoders.steamagerevolution.modules.transport;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.blocks.*;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleTransport extends ModuleBase {

	public static Block pneumaticTube, pneumaticSender, pneumaticRouter;
	public static Block hydraulicTube, hydraulicSender, hydraulicRouter;
	public static Block sorterInputBuffer, sorterOutputBuffer;

	@Override
	public String getName() {
		return "Transport";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		pneumaticTube = new BlockPneumaticTube(Material.IRON, "pneumatic_tube");
		blockRegistry.register(pneumaticTube);
		pneumaticSender = new BlockPneumaticSender(Material.IRON, "pneumatic_sender");
		blockRegistry.register(pneumaticSender);
		pneumaticRouter = new BlockPneumaticRouter(Material.IRON, "pneumatic_router");
		blockRegistry.register(pneumaticRouter);

		hydraulicTube = new BlockHydraulicTube(Material.IRON, "hydraulic_tube");
		// blockRegistry.register(hydraulicTube);
		hydraulicSender = new BlockHydraulicSender(Material.IRON, "hydraulic_sender");
		// blockRegistry.register(hydraulicSender);
		hydraulicRouter = new BlockHydraulicRouter(Material.IRON, "hydraulic_router");
		// blockRegistry.register(hydraulicRouter);

		// blockRegistry.register(new BlockSorterInput(Material.IRON, "sorter_input"));
		// blockRegistry.register(new BlockSorterOutput(Material.IRON,
		// "sorter_output"));
		// blockRegistry.register(new BlockSorterFrame(Material.IRON, "sorter_frame"));
		// blockRegistry.register(new BlockSorterBrain(Material.IRON, "sorter_brain"));
		//
		// blockRegistry.register(new BlockCardPuncher(Material.IRON, "card_puncher"));

	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		// itemRegistry.register(new ItemPunchcard());
	}

}
