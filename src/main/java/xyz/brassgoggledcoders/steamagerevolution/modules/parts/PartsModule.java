package xyz.brassgoggledcoders.steamagerevolution.modules.parts;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.items.ItemSubBase;
import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

@Module(mod = SteamAgeRevolution.MODID)
public class PartsModule extends ModuleBase {
	public static ItemSubBase bearing, bolt, gear, nail, nut, screw, spring, sprocket, thread, washer;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		this.registerItems();
	}

	@Override
	public String getName() {
		return "Parts";
	}

	public void registerItems() {
		bearing = new ItemSubBase("parts", "bearing", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(bearing);
		bolt = new ItemSubBase("parts", "bolt", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(bolt);
		gear = new ItemSubBase("parts", "gear", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(gear);
		nail = new ItemSubBase("parts", "nail", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(nail);
		nut = new ItemSubBase("parts", "nut", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(nut);
		screw = new ItemSubBase("parts", "screw", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(screw);
		spring = new ItemSubBase("parts", "spring", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(spring);
		sprocket = new ItemSubBase("parts", "sprocket", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(sprocket);
		thread = new ItemSubBase("parts", "thread", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(thread);
		washer = new ItemSubBase("parts", "washer", ItemTypes.metalsParts);
		this.getItemRegistry().registerItem(washer);
	}
}
