package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(mod = SteamAgeRevolution.MODID)
public class MechanicalModule extends ModuleBase {
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
	}

}
