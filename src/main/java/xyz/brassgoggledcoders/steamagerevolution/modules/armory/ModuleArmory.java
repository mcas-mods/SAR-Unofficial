package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleArmory extends ModuleBase {

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {
		CapabilityManager.INSTANCE.register(IExpansionContainer.class, new Capability.IStorage<IExpansionContainer>() {
			@Override
			public NBTBase writeNBT(Capability<IExpansionContainer> capability, IExpansionContainer instance,
					EnumFacing side) {
				return new NBTTagCompound();
			}

			@Override
			public void readNBT(Capability<IExpansionContainer> capability, IExpansionContainer instance,
					EnumFacing side, NBTBase nbt) {

			}
		}, ExpansionContainerHandler.class);

		CapabilityManager.INSTANCE.register(IExpansion.class, new Capability.IStorage<IExpansion>() {
			@Override
			public NBTBase writeNBT(Capability<IExpansion> capability, IExpansion instance, EnumFacing side) {
				return new NBTTagCompound();
			}

			@Override
			public void readNBT(Capability<IExpansion> capability, IExpansion instance, EnumFacing side, NBTBase nbt) {

			}
		}, ExpansionHandler.class);
		super.preInit(fmlPreInitializationEvent);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		blockRegistry.register(new BlockExpansionEditor());
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		itemRegistry.register(new ItemExpandableArmor(ArmorMaterial.CHAIN, EntityEquipmentSlot.HEAD, "brass_helmet"));
	}

	@Override
	public String getName() {
		return "Armoury";
	}
}
