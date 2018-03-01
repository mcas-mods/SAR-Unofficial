package xyz.brassgoggledcoders.steamagerevolution.modules.processing;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.SteamFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.sawmill.*;

@Module(value = SteamAgeRevolution.MODID)
@ObjectHolder(SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class ModuleProcessing extends ModuleBase {

	public static final Item charcoal_powder = null;

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

		blockRegistry.register(new BlockFurnaceCasing(Material.IRON, "furnace_casing"));
		blockRegistry.register(new BlockFurnaceItemInput(Material.IRON, "furnace_item_input"));
		blockRegistry.register(new BlockFurnaceItemOutput(Material.IRON, "furnace_item_output"));
		blockRegistry.register(new BlockFurnaceSteamInput(Material.IRON, "furnace_steam_input"));
		blockRegistry.register(new BlockFurnaceModeToggle(Material.IRON, "furnace_mode_toggle"));

		blockRegistry.register(new BlockSawmillCasing(Material.IRON, "sawmill_casing"));
		blockRegistry.register(new BlockSawmillAxle(Material.IRON, "sawmill_axle"));
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

		for(ItemStack cooked : OreDictionary.getOres("listAllmeatcooked")) {
			SteamFurnaceRecipe.addSteamFurnaceRecipe(cooked, new ItemStack(charcoal_powder));
		}

		// TODO Oredict
		SawmillRecipe.addSawmillRecipe(new ItemStack(Blocks.LOG), new ItemStack(Blocks.PLANKS, 6));
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.processing.ClientProxy";
	}
}
