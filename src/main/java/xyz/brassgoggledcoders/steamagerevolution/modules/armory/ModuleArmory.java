package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleArmory extends ModuleBase {

	public static final ToolMaterial STEAM = EnumHelper.addToolMaterial("TOOL_STEAM", 2, -1, 12.0F, 3.0F, 0);

	@Override
	public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {
		/*
		 * CapabilityManager.INSTANCE.register(IExpansionContainer.class, new Capability.IStorage<IExpansionContainer>()
		 * {
		 * @Override
		 * public NBTBase writeNBT(Capability<IExpansionContainer> capability, IExpansionContainer instance,
		 * EnumFacing side) {
		 * return new NBTTagCompound();
		 * }
		 * @Override
		 * public void readNBT(Capability<IExpansionContainer> capability, IExpansionContainer instance,
		 * EnumFacing side, NBTBase nbt) {
		 * }
		 * }, ExpansionContainerHandler.class);
		 * CapabilityManager.INSTANCE.register(IExpansion.class, new Capability.IStorage<IExpansion>() {
		 * @Override
		 * public NBTBase writeNBT(Capability<IExpansion> capability, IExpansion instance, EnumFacing side) {
		 * return new NBTTagCompound();
		 * }
		 * @Override
		 * public void readNBT(Capability<IExpansion> capability, IExpansion instance, EnumFacing side, NBTBase nbt) {
		 * }
		 * }, ExpansionHandler.class);
		 */
		super.preInit(fmlPreInitializationEvent);
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		// blockRegistry.register(new BlockExpansionEditor());
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		// itemRegistry.register(new ItemExpandableArmor(ArmorMaterial.CHAIN, EntityEquipmentSlot.HEAD,
		// "brass_helmet"));
		itemRegistry.register(new ItemClockworkWings());

		itemRegistry.register(new ItemSteamPickaxe("steam_pickaxe", 1000));
		itemRegistry.register(new ItemSteamAxe("steam_axe", 1000));
		itemRegistry.register(new ItemSteamShovel("steam_shovel", 1000));
		itemRegistry.register(new ItemSteamHoe("steam_hoe", 1000));
		itemRegistry.register(new ItemSteamSword("steam_sword", 1000));
	}

	@Override
	public String getName() {
		return "Armoury";
	}
}
