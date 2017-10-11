package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.Platform;

import net.minecraft.block.material.Material;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.VatRecipe.VatRecipeBuilder;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
public class ModuleAlchemical extends ModuleBase {

	// TODO Don't bypass armour, deal extra damage to it
	public static DamageSource damageSourceGas =
			new DamageSource("gas").setDifficultyScaled().setDamageBypassesArmor().setDamageIsAbsolute();
	public static DamageSource damageSourceAcid =
			new DamageSource("acid").setDifficultyScaled().setDamageBypassesArmor().setDamageIsAbsolute();

	@Override
	public boolean getActiveDefault() {
		return Platform.isDevEnv();
	}

	@Override
	public String getName() {
		return "Alchemical";
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.ClientProxy";
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		new VatRecipeBuilder().setOutput(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME))
				.setFluids(FluidRegistry.getFluidStack("sulphur_dioxide", Fluid.BUCKET_VOLUME),
						FluidRegistry.getFluidStack("water", Fluid.BUCKET_VOLUME))
				.build();
		// TODO Proper oredict support
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		blockRegistry.register(new BlockVatFrame(Material.IRON, "vat_frame"));
		blockRegistry.register(new BlockVatFluidInput(Material.IRON, "vat_fluid_input"));
		blockRegistry.register(new BlockVatOutput(Material.IRON, "vat_output"));

		blockRegistry.register(new BlockFumeCollector(Material.IRON, "fume_collector"));

		Fluid sulphur_dioxide =
				new Fluid("sulphur_dioxide", new ResourceLocation(SteamAgeRevolution.MODID, "blocks/sulphur_dioxide"),
						new ResourceLocation(SteamAgeRevolution.MODID, "blocks/sulphur_dioxide_flow")).setViscosity(250)
								.setGaseous(true);
		FluidRegistry.registerFluid(sulphur_dioxide);
		FluidRegistry.addBucketForFluid(sulphur_dioxide);

		blockRegistry.register(new BlockDamagingFluid("sulphur_dioxide", FluidRegistry.getFluid("sulphur_dioxide"),
				Material.WATER, damageSourceGas, 2));
		Fluid sulphuric_acid =
				new Fluid("sulphuric_acid", new ResourceLocation(SteamAgeRevolution.MODID, "blocks/sulphuric_acid"),
						new ResourceLocation(SteamAgeRevolution.MODID, "blocks/sulphuric_acid_flow")).setViscosity(500);
		FluidRegistry.registerFluid(sulphuric_acid);
		FluidRegistry.addBucketForFluid(sulphuric_acid);

		blockRegistry.register(new BlockDamagingFluid("sulphuric_acid", FluidRegistry.getFluid("sulphuric_acid"),
				Material.WATER, damageSourceAcid, 4));
	}

}
