package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltEnd;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockGearbox;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockInfiniteSpinDrain;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockInfiniteSpinSource;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockWaterTurbine;

@Module(mod = SteamAgeRevolution.MODID)
public class ModuleMechanical extends ModuleBase {

	public Block water_turbine;
	public Block inf_spin_source, inf_spin_drain;
	public Block gearbox, belt_end;
	public Item belt;

	@Override
	public String getName() {
		return "Mechanical";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		belt_end = new BlockBeltEnd(Material.IRON, "belt_end");
		this.getBlockRegistry().registerBlock(belt_end);
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

		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBeltEnd.class, new TileEntityBeltRenderer());
	}
}
