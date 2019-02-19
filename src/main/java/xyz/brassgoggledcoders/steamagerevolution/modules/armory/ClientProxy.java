package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.ItemLens;

public class ClientProxy implements IModuleProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {

	}

	@Override
	public void init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemLens.LensTintHandler(),
				ModuleArmory.lens);
	}

}
