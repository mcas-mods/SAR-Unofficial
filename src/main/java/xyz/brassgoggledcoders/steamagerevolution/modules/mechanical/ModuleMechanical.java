package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltDummy;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltEnd;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockFurnaceHeater;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockGearbox;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockInfiniteSpinDrain;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockInfiniteSpinSource;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockWaterTurbine;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleMechanical extends ModuleBase {

	public static Block inf_spin_source, inf_spin_drain;
	public static Block gearbox, iron_belt_end, brass_belt_end, steel_belt_end, belt_dummy;
	public static Block water_turbine;
	public static Block furnace_heater;
	public static Item belt;

	@Override
	public String getName() {
		return "Mechanical";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		iron_belt_end = new BlockBeltEnd(Material.IRON, "iron_belt_end", 0.5F);
		this.getBlockRegistry().registerBlock(iron_belt_end);
		brass_belt_end = new BlockBeltEnd(Material.IRON, "brass_belt_end", 0.75F);
		this.getBlockRegistry().registerBlock(brass_belt_end);
		steel_belt_end = new BlockBeltEnd(Material.IRON, "steel_belt_end", 0.89F);
		this.getBlockRegistry().registerBlock(steel_belt_end);
		belt_dummy = new BlockBeltDummy(Material.CLOTH, "belt_dummy");
		this.getBlockRegistry().registerBlock(belt_dummy);
		gearbox = new BlockGearbox(Material.IRON, "gearbox");
		this.getBlockRegistry().registerBlock(gearbox);

		belt = new ItemBelt("belt");
		this.getItemRegistry().registerItem(belt);

		inf_spin_source = new BlockInfiniteSpinSource(Material.CIRCUITS, "infinite_spin_source");
		this.getBlockRegistry().registerBlock(inf_spin_source);
		inf_spin_drain = new BlockInfiniteSpinDrain(Material.CIRCUITS, "infinite_spin_drain");
		this.getBlockRegistry().registerBlock(inf_spin_drain);

		water_turbine = new BlockWaterTurbine(Material.IRON, "water_turbine");
		this.getBlockRegistry().registerBlock(water_turbine);

		furnace_heater = new BlockFurnaceHeater(Material.IRON, "furnace_heater");
		this.getBlockRegistry().registerBlock(furnace_heater);

		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBeltEnd.class, new TileEntityBeltRenderer());
	}
}
