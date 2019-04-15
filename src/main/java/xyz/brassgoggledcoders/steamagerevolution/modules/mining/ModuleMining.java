package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.util.List;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModuleDependency;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class ModuleMining extends ModuleBase {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override
    public List<IDependency> getDependencies(List<IDependency> dependencies) {
		dependencies.add(new ModuleDependency("Metalworking"));
        return dependencies;
    }
	
	@Override
	public String getName() {
		return "Mining";
	}
	
	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		//for(String type : ModuleMetalworking.knownMetalTypes) {
			blockRegistry.register(new BlockHeavyOre("Iron"));
		//}
	}
	
	@SubscribeEvent
	public static void onBlockBroken(BlockEvent.BreakEvent event) {
		if(event.getState().getBlock() instanceof BlockHeavyOre) {
			int chunks = event.getState().getValue(BlockHeavyOre.CHUNKS).intValue();
			if(chunks > 1) {
				EntityItem itemE = new EntityItem(event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), ((BlockHeavyOre)event.getState().getBlock()).drop);
				event.getWorld().spawnEntity(itemE);
				event.getWorld().setBlockState(event.getPos(), event.getState().withProperty(BlockHeavyOre.CHUNKS, chunks - 1), 2);
				event.setCanceled(true);
			}
			else {
				//TODO
			}
		}
	}

}
