package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockHydraulicRouter;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockHydraulicSender;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockHydraulicTube;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockPneumaticRouter;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockPneumaticSender;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockPneumaticTube;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.multiblock.sorter.BlockInputBuffer;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.multiblock.sorter.BlockOutputBuffer;

@Module(value = SteamAgeRevolution.MODID)
public class ModulePneumatic extends ModuleBase {

	public static Block pneumaticTube, pneumaticSender, pneumaticRouter;
	public static Block hydraulicTube, hydraulicSender, hydraulicRouter;
	public static Block sorterInputBuffer, sorterOutputBuffer;

	@Override
	public String getName() {
		return "Pneumatic";
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
		blockRegistry.register(hydraulicTube);
		hydraulicSender = new BlockHydraulicSender(Material.IRON, "hydraulic_sender");
		blockRegistry.register(hydraulicSender);
		hydraulicRouter = new BlockHydraulicRouter(Material.IRON, "hydraulic_router");
		blockRegistry.register(hydraulicRouter);

		sorterInputBuffer = new BlockInputBuffer(Material.IRON, "sorter_inputbuffer");
		// blockRegistry.register(sorterInputBuffer);
		sorterOutputBuffer = new BlockOutputBuffer(Material.IRON, "sorter_outputbuffer");
		// blockRegistry.register(sorterOutputBuffer);
	}
}
