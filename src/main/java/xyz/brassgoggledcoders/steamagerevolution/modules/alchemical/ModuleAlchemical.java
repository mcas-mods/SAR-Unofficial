package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Random;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.blocks.BlockFluidBase;
import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.IRegistryDelegate;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.blocks.BlockFumeCollector;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.items.ItemFlask;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities.FumeCollectorRecipe;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockDamagingFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeRegistry;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe.MachineRecipeBuilder;

@Module(value = SteamAgeRevolution.MODID)
@EventBusSubscriber(modid = SteamAgeRevolution.MODID)
@ObjectHolder(SteamAgeRevolution.MODID)
public class ModuleAlchemical extends ModuleBase {

	public static final Block distiller_radiator = null;
	public static final Block distiller_hotplate = null;
	public static final Block incense = null; // Refers to fluid block not solid
	public static final Block incense_block = null;
	public static final Block incense_spray = null;
	public static final Block incense_burner = null;
	// public static final Item plant_ash = null;

	// TODO Don't bypass armour, deal extra damage to it
	public static DamageSource damageSourceGas = new DamageSource("gas").setDifficultyScaled().setDamageBypassesArmor()
			.setDamageIsAbsolute();
	public static DamageSource damageSourceAcid = new DamageSource("acid").setDifficultyScaled()
			.setDamageBypassesArmor().setDamageIsAbsolute();

	public static int VALUE_BOTTLE = 250;

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		new MachineRecipeBuilder("vat")
				.setFluidOutputs(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME))
				.setFluidInputs(FluidRegistry.getFluidStack("sulphur_dioxide", Fluid.BUCKET_VOLUME),
						FluidRegistry.getFluidStack("water", Fluid.BUCKET_VOLUME))
				.build();

		new MachineRecipeBuilder("vat")
				.setFluidOutputs(FluidRegistry.getFluidStack("liquid_glowstone", Fluid.BUCKET_VOLUME))
				.setFluidInputs(FluidRegistry.getFluidStack("lava", Fluid.BUCKET_VOLUME))
				.setItemInputs(new ItemStack(Items.REDSTONE, 4)).build();
		FumeCollectorRecipe.addRecipe(MaterialSystem.getMaterialPart("sulphur_crystal").getItemStack(),
				FluidRegistry.getFluidStack("sulphur_dioxide", Fluid.BUCKET_VOLUME), 0.1f);
		RecipeRegistry.addRecipe("casting_block",
				new MachineRecipeBuilder("casting_block")
						.setFluidInputs(FluidRegistry.getFluidStack("liquid_glowstone", Fluid.BUCKET_VOLUME))
						.setItemOutputs(new ItemStack(Blocks.GLOWSTONE)).setCraftTime(2400).build());
		// TODO Proper oredict support

		// TODO Tooltip/Color for fluid (when IE not present) & potion deriving name
		// from vanilla
		new MachineRecipeBuilder("vat")
				.setFluidOutputs(getPotionFluidStack(PotionTypes.AWKWARD.getRegistryName().getPath(), VALUE_BOTTLE))
				.setFluidInputs(FluidRegistry.getFluidStack("water", VALUE_BOTTLE))
				.setItemInputs(new ItemStack(Items.NETHER_WART)).build();
		// Stolen from ImmersiveEngineering (ta Malte!)
		try {
			// Blame Forge for this mess. They stopped ATs from working on MixPredicate and
			// its fields by modifying them with patches
			// without providing a usable way to look up the vanilla potion recipes
			String mixPredicateName = "net.minecraft.potion.PotionHelper$MixPredicate";
			Class<?> mixPredicateClass = Class.forName(mixPredicateName);
			Field output = ReflectionHelper.findField(mixPredicateClass,
					ObfuscationReflectionHelper.remapFieldNames(mixPredicateName, "field_185200_c"));
			Field reagent = ReflectionHelper.findField(mixPredicateClass,
					ObfuscationReflectionHelper.remapFieldNames(mixPredicateName, "field_185199_b"));
			Field input = ReflectionHelper.findField(mixPredicateClass,
					ObfuscationReflectionHelper.remapFieldNames(mixPredicateName, "field_185198_a"));
			output.setAccessible(true);
			reagent.setAccessible(true);
			input.setAccessible(true);
			for(Object mixPredicate : PotionHelper.POTION_TYPE_CONVERSIONS) {
				PotionType outputObj = ((IRegistryDelegate<PotionType>) output.get(mixPredicate)).get();
				new MachineRecipeBuilder("vat")
						.setFluidOutputs(getPotionFluidStack(outputObj.getRegistryName().getPath(), VALUE_BOTTLE))
						.setFluidInputs(getPotionFluidStack(((IRegistryDelegate<PotionType>) input.get(mixPredicate))
								.get().getRegistryName().getPath(), VALUE_BOTTLE))
						.setItemInputs(((Ingredient) reagent.get(reagent)).getMatchingStacks()[0]).build();
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
		// End stealing

		// new VatRecipeBuilder().setFluids(FluidRegistry.getFluidStack("water",
		// Fluid.BUCKET_VOLUME))
		// .setItems(new ItemStack(plant_ash), new ItemStack(Items.COAL, 1, 1),
		// OreDictUtils.getPreferredItemStack("crystalSulphur"))
		// .setOutput(FluidRegistry.getFluidStack("liquid_explosive",
		// Fluid.BUCKET_VOLUME)).build();
		// DistillerRecipe.addRecipe(FluidRegistry.getFluidStack("liquid_explosive",
		// Fluid.BUCKET_VOLUME), null,
		// new ItemStack(Items.GUNPOWDER), 200);

		new MachineRecipeBuilder("vat").setFluidInputs(FluidRegistry.getFluidStack("water", Fluid.BUCKET_VOLUME))
				.setItemInputs(new ItemStack(Blocks.DIRT), new ItemStack(Items.ROTTEN_FLESH),
						new ItemStack(Items.SUGAR))
				.setFluidOutputs(FluidRegistry.getFluidStack("slime", Fluid.BUCKET_VOLUME)).build();
		new MachineRecipeBuilder("vat")
				.setFluidInputs(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME))
				.setItemInputs(new ItemStack(Items.REDSTONE))
				.setFluidOutputs(FluidRegistry.getFluidStack("lava", Fluid.BUCKET_VOLUME))
				.setItemOutputs(new ItemStack(Items.GLOWSTONE_DUST)).build();
		new MachineRecipeBuilder("distiller").setFluidInputs(FluidRegistry.getFluidStack("slime", Fluid.BUCKET_VOLUME))
				.setItemOutputs(new ItemStack(Item.getItemFromBlock(Blocks.SLIME_BLOCK))).setCraftTime(20).build();
	}

	public static FluidStack getPotionFluidStack(String potionType, int amount) {
		FluidStack potion = FluidRegistry.getFluidStack("potion", amount);
		potion.tag = new NBTTagCompound();
		potion.tag.setString("Potion", potionType);
		return potion;
	}

	@Override
	public String getName() {
		return "Alchemical";
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.ClientProxy";
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry registry) {
		registry.register(new ItemFlask("flask", Fluid.BUCKET_VOLUME * 3));
		// registry.register(new ItemBase("plant_ash"));
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
		blockRegistry.register(new BlockDistillerItemOutput(Material.IRON, "distiller_item_output"));

		Fluid sulphur_dioxide = new Fluid("sulphur_dioxide",
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphur_dioxide"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphur_dioxide_flow")).setViscosity(250)
						.setGaseous(true);
		FluidRegistry.registerFluid(sulphur_dioxide);
		FluidRegistry.addBucketForFluid(sulphur_dioxide);

		blockRegistry.register(new BlockDamagingFluid("sulphur_dioxide", FluidRegistry.getFluid("sulphur_dioxide"),
				Material.WATER, damageSourceGas, 2));

		Fluid sulphuric_acid = new Fluid("sulphuric_acid",
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphuric_acid"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/sulphuric_acid_flow")).setViscosity(500);
		FluidRegistry.registerFluid(sulphuric_acid);
		FluidRegistry.addBucketForFluid(sulphuric_acid);

		blockRegistry.register(new BlockDamagingFluid("sulphuric_acid", FluidRegistry.getFluid("sulphuric_acid"),
				Material.WATER, damageSourceAcid, 4) {
			@Override
			public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state,
					@Nonnull Random rand) {
				if(!world.isRemote) {
					for(EnumFacing facing : EnumFacing.VALUES) {
						if(rand.nextInt(10) == 0) {
							BlockPos other = pos.offset(facing);
							Material mat = world.getBlockState(other).getMaterial();
							if(Material.GROUND.equals(mat) || Material.GRASS.equals(mat) || Material.ROCK.equals(mat)) {
								world.setBlockToAir(other);
							}
						}
					}
				}
				super.updateTick(world, pos, state, rand);
			}
		});

		// TODO TE compat?
		Fluid liquid_glowstone = new Fluid("liquid_glowstone",
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/liquid_glowstone"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/liquid_glowstone_flow")).setViscosity(2000)
						.setGaseous(true);
		FluidRegistry.registerFluid(liquid_glowstone);
		FluidRegistry.addBucketForFluid(liquid_glowstone);

		blockRegistry.register(
				new BlockFluidBase("liquid_glowstone", FluidRegistry.getFluid("liquid_glowstone"), Material.LAVA) {
					@Override
					public ResourceLocation getResourceLocation(IBlockState blockState) {
						return new ResourceLocation(SteamAgeRevolution.MODID, "liquid_glowstone");
					}

					@Override
					public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
						if(entityIn instanceof EntityLiving) {
							EntityLiving living = (EntityLiving) entityIn;
							living.addPotionEffect(new PotionEffect(
									Potion.getPotionFromResourceLocation("minecraft:glowing"), 20, 1, true, false));
						}
					}
				}.setLightLevel(0.8F));

		Fluid potion = new Fluid("potion", new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution"),
				new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution_flowing")) {
			@SuppressWarnings("deprecation")
			@Override
			public String getLocalizedName(FluidStack stack) {
				return I18n.translateToLocal(
						PotionUtils.getPotionTypeFromNBT(stack.tag).getNamePrefixed("potion.effect."));
			}

			@Override
			public int getColor(FluidStack stack) {
				return PotionUtils.getPotionColorFromEffectList(PotionUtils.getEffectsFromTag(stack.tag));
			}
		};
		FluidRegistry.registerFluid(potion);
		FluidRegistry.addBucketForFluid(potion);

		// Fluid liquid_explosive =
		// new Fluid("liquid_explosive", new ResourceLocation(SteamAgeRevolution.MODID,
		// "fluids/liquid_explosive"),
		// new ResourceLocation(SteamAgeRevolution.MODID, "fluids/liquid_explosive"));
		// FluidRegistry.registerFluid(liquid_explosive);

		FluidRegistry
				.registerFluid(new Fluid("slime", new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution"),
						new ResourceLocation(SteamAgeRevolution.MODID, "fluids/solution")) {
					@Override
					public int getColor() {
						return Color.GREEN.getRGB();
					}
				});

		/*
		 * Fluid incense = new Fluid("incense", new
		 * ResourceLocation(SteamAgeRevolution.MODID, "fluids/incense"), new
		 * ResourceLocation(SteamAgeRevolution.MODID,
		 * "fluids/incense_flow")).setGaseous(true).setDensity(-100)
		 * .setViscosity(-100);
		 *
		 * if(!(FluidRegistry.isFluidRegistered(incense))) { // Soft registration
		 * FluidRegistry.registerFluid(incense);
		 * FluidRegistry.addBucketForFluid(incense); } blockRegistry.register(new
		 * BlockIncenseFluid("incense", FluidRegistry.getFluid("incense"),
		 * Material.WATER) {
		 *
		 * @Override public ResourceLocation getResourceLocation(IBlockState blockState)
		 * { return new ResourceLocation(SteamAgeRevolution.MODID, "incense"); } });
		 * blockRegistry.register(new BlockIncense()); blockRegistry.register(new
		 * BlockIncenseSpray()); blockRegistry.register(new BlockIncenseBurner());
		 */
	}

	// Lifted from IE
	// public static DataSerializer<Optional<FluidStack>> OPTIONAL_FLUID_STACK =
	// new DataSerializer<Optional<FluidStack>>() {
	// @Override
	// public void write(PacketBuffer buf, Optional<FluidStack> value) {
	// buf.writeBoolean(value.isPresent());
	// FluidStack fs = value.orNull();
	// if(fs != null)
	// buf.writeCompoundTag(fs.writeToNBT(new NBTTagCompound()));
	// }
	//
	// @Override
	// public Optional<FluidStack> read(PacketBuffer buf) throws IOException {
	// FluidStack fs = !buf.readBoolean() ? null :
	// FluidStack.loadFluidStackFromNBT(buf.readCompoundTag());
	// return Optional.fromNullable(fs);
	// }
	//
	// @Override
	// public DataParameter<Optional<FluidStack>> createKey(int id) {
	// return new DataParameter<Optional<FluidStack>>(id, this);
	// }
	//
	// @Override
	// public Optional<FluidStack> copyValue(Optional<FluidStack> value) {
	// return value.isPresent() ? Optional.of(value.get().copy()) :
	// Optional.absent();
	// }
	// };
}
