package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.OreDictUtils;
import com.teamacronymcoders.base.util.Platform;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.blocks.BlockFumeCollector;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.VatRecipe.VatRecipeBuilder;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.FumeCollectorRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.CastingBlockRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockDamagingFluid;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
@ObjectHolder(SteamAgeRevolution.MODID)
public class ModuleAlchemical extends ModuleBase {

	public static final Block distiller_radiator = null;
	public static final Block distiller_hotplate = null;

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

		new VatRecipeBuilder()
				.setOutput(FluidRegistry.getFluidStack("coppersolution", ModuleMetalworking.VALUE_NUGGET * 4))
				.setFluids(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME / 4))
				.setItems(OreDictUtils.getPreferredItemStack("crushedOreCopper")).build();
		DistillerRecipe.addRecipe(FluidRegistry.getFluidStack("coppersolution", ModuleMetalworking.VALUE_NUGGET * 4),
				FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME / 6),
				OreDictUtils.getPreferredItemStack("crystalCopper"), 20);

		new VatRecipeBuilder().setOutput(FluidRegistry.getFluidStack("liquid_glowstone", Fluid.BUCKET_VOLUME))
				.setFluids(FluidRegistry.getFluidStack("lava", Fluid.BUCKET_VOLUME))
				.setItems(new ItemStack(Items.REDSTONE, 4)).build();
		FumeCollectorRecipe.addRecipe(MaterialSystem.getMaterialPart("sulphur_crystal").getItemStack(),
				FluidRegistry.getFluidStack("sulphur_dioxide", Fluid.BUCKET_VOLUME), 0.1f);
		CastingBlockRecipe.addRecipe(FluidRegistry.getFluidStack("liquid_glowstone", ModuleMetalworking.VALUE_BLOCK),
				new ItemStack(Blocks.GLOWSTONE));
		// TODO Proper oredict support
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		blockRegistry.register(new BlockVatFrame(Material.IRON, "vat_frame"));
		blockRegistry.register(new BlockVatFluidInput(Material.IRON, "vat_fluid_input"));
		blockRegistry.register(new BlockVatOutput(Material.IRON, "vat_output"));

		blockRegistry.register(new BlockFumeCollector(Material.IRON, "fume_collector"));

		blockRegistry.register(new BlockDistillerFluidInput(Material.IRON, "distiller_fluid_input"));
		blockRegistry.register(new BlockDistillerFluidOutput(Material.IRON, "distiller_fluid_output"));
		blockRegistry.register(new BlockDistillerFrame(Material.IRON, "distiller_frame"));
		blockRegistry.register(new BlockDistillerHotplate(Material.IRON, "distiller_hotplate"));
		blockRegistry.register(new BlockDistillerRadiator(Material.IRON, "distiller_radiator"));

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

		// TODO TE compat?
		Fluid liquid_glowstone =
				new Fluid("liquid_glowstone", new ResourceLocation(SteamAgeRevolution.MODID, "blocks/liquid_glowstone"),
						new ResourceLocation(SteamAgeRevolution.MODID, "blocks/liquid_glowstone_flow"))
								.setViscosity(2000).setGaseous(true);
		FluidRegistry.registerFluid(liquid_glowstone);
		FluidRegistry.addBucketForFluid(liquid_glowstone);

		blockRegistry.register(
				new BlockFluidBase("liquid_glowstone", FluidRegistry.getFluid("liquid_glowstone"), Material.LAVA) {
					@Override
					public ResourceLocation getResourceLocation(IBlockState blockState) {
						return new ResourceLocation(SteamAgeRevolution.MODID, "liquid_glowstone");
					}

					@Override
					public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state,
							Entity entityIn) {
						if(entityIn instanceof EntityLiving) {
							EntityLiving living = (EntityLiving) entityIn;
							living.addPotionEffect(new PotionEffect(
									Potion.getPotionFromResourceLocation("minecraft:glowing"), 20, 1, true, false));
						}
					}
				}.setLightLevel(0.8F));
	}

}
