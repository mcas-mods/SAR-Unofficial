package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.registries.BlockRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ConfigRegistry;
import xyz.brassgoggledcoders.boilerplate.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltDummy;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltEnd;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockGearbox;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockCartBooster;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockChute;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockDropHammer;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockDropHammerAnvil;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockFan;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockFurnaceHeater;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.generators.BlockInfiniteSpinSource;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.generators.BlockWaterTurbine;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.items.ItemBelt;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.items.ItemSpinReader;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.DropHammerRecipes;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleMechanical extends ModuleBase {

	public static Block infSpinSource;
	public static Block gearbox, ironBeltEnd, brassBeltEnd, steelBeltEnd, leatherBeltDummy, rubberBeltDummy;
	public static Block waterTurbine;
	public static Block furnaceHeater, chute, dropHammer, dropHammerAnvil, cartBooster, fan;
	public static Item leatherBelt, rubberBelt;
	public static Item spinReader;

	@Override
	public String getName() {
		return "Mechanical";
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		spinReader = new ItemSpinReader("spin_reader");
		itemRegistry.registerItem(spinReader);
		leatherBelt = new ItemBelt("leather_belt", 6);
		itemRegistry.registerItem(leatherBelt);
		rubberBelt = new ItemBelt("rubber_belt", 12);
		itemRegistry.registerItem(rubberBelt);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		ironBeltEnd = new BlockBeltEnd(Material.IRON, "iron_belt_end", 0.5F);
		blockRegistry.registerBlock(ironBeltEnd);
		brassBeltEnd = new BlockBeltEnd(Material.IRON, "brass_belt_end", 0.75F);
		blockRegistry.registerBlock(brassBeltEnd);
		steelBeltEnd = new BlockBeltEnd(Material.IRON, "steel_belt_end", 0.89F);
		blockRegistry.registerBlock(steelBeltEnd);
		leatherBeltDummy = new BlockBeltDummy("leather");
		blockRegistry.registerBlock(leatherBeltDummy);
		rubberBeltDummy = new BlockBeltDummy("rubber");
		blockRegistry.registerBlock(rubberBeltDummy);
		gearbox = new BlockGearbox(Material.IRON, "gearbox");
		blockRegistry.registerBlock(gearbox);

		infSpinSource = new BlockInfiniteSpinSource(Material.CIRCUITS, "infinite_spin_source");
		blockRegistry.registerBlock(infSpinSource);
		waterTurbine = new BlockWaterTurbine(Material.IRON, "water_turbine");
		blockRegistry.registerBlock(waterTurbine);

		furnaceHeater = new BlockFurnaceHeater(Material.IRON, "furnace_heater");
		blockRegistry.registerBlock(furnaceHeater);
		chute = new BlockChute(Material.ANVIL, "chute");
		blockRegistry.registerBlock(chute);
		dropHammer = new BlockDropHammer(Material.IRON, "drop_hammer");
		blockRegistry.registerBlock(dropHammer);
		// TODO
		DropHammerRecipes.registerRecipes();
		dropHammerAnvil = new BlockDropHammerAnvil(Material.IRON, "drop_hammer_anvil");
		blockRegistry.registerBlock(dropHammerAnvil);
		cartBooster = new BlockCartBooster(Material.IRON, "cart_booster");
		blockRegistry.registerBlock(cartBooster);
		fan = new BlockFan();
		blockRegistry.registerBlock(fan);
	}
}
