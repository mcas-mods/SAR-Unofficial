package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registry.BlockRegistry;
import com.teamacronymcoders.base.registry.ItemRegistry;
import com.teamacronymcoders.base.registry.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltDummy;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltEnd;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockGearbox;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockSawblade;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockCartBooster;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockChute;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockFan;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockSaw;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.generators.BlockInfiniteSpinSource;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.generators.BlockWaterTurbine;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.items.ItemBelt;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.items.ItemSpinReader;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockDropHammer;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockDropHammerAnvil;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.DropHammerRecipes;

// @Module(value = SteamAgeRevolution.MODID)
public class ModuleMechanical extends ModuleBase {

	public static Block infSpinSource;
	public static Block gearbox, ironBeltEnd, brassBeltEnd, steelBeltEnd, leatherBeltDummy, rubberBeltDummy;
	public static Block waterTurbine;
	public static Block furnaceHeater, chute, dropHammer, dropHammerAnvil, cartBooster, fan;
	public static Block saw, sawBlade;
	public static Item leatherBelt, rubberBelt;
	public static Item spinReader;

	@Override
	public String getName() {
		return "Mechanical";
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		spinReader = new ItemSpinReader("spin_reader");
		itemRegistry.register(spinReader);
		leatherBelt = new ItemBelt("leather_belt", 6);
		itemRegistry.register(leatherBelt);
		rubberBelt = new ItemBelt("rubber_belt", 12);
		itemRegistry.register(rubberBelt);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		ironBeltEnd = new BlockBeltEnd(Material.IRON, "iron_belt_end", 0.5F);
		blockRegistry.register(ironBeltEnd);
		brassBeltEnd = new BlockBeltEnd(Material.IRON, "brass_belt_end", 0.75F);
		blockRegistry.register(brassBeltEnd);
		steelBeltEnd = new BlockBeltEnd(Material.IRON, "steel_belt_end", 0.89F);
		blockRegistry.register(steelBeltEnd);
		leatherBeltDummy = new BlockBeltDummy("leather");
		blockRegistry.register(leatherBeltDummy);
		rubberBeltDummy = new BlockBeltDummy("rubber");
		blockRegistry.register(rubberBeltDummy);
		gearbox = new BlockGearbox(Material.IRON, "gearbox");
		blockRegistry.register(gearbox);

		infSpinSource = new BlockInfiniteSpinSource(Material.CIRCUITS, "infinite_spin_source");
		blockRegistry.register(infSpinSource);
		waterTurbine = new BlockWaterTurbine(Material.IRON, "water_turbine");
		blockRegistry.register(waterTurbine);

		chute = new BlockChute(Material.ANVIL, "chute");
		blockRegistry.register(chute);
		dropHammer = new BlockDropHammer(Material.IRON, "drop_hammer");
		blockRegistry.register(dropHammer);
		// TODO
		DropHammerRecipes.registerRecipes();
		dropHammerAnvil = new BlockDropHammerAnvil(Material.IRON, "drop_hammer_anvil");
		blockRegistry.register(dropHammerAnvil);
		cartBooster = new BlockCartBooster(Material.IRON, "cart_booster");
		blockRegistry.register(cartBooster);
		fan = new BlockFan();
		blockRegistry.register(fan);
		saw = new BlockSaw();
		blockRegistry.register(saw);
		sawBlade = new BlockSawblade();
		blockRegistry.register(sawBlade);
	}
}
