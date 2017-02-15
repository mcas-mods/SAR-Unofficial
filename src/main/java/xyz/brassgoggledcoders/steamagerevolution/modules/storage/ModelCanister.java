package xyz.brassgoggledcoders.steamagerevolution.modules.storage;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelCustomData;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ItemTextureQuadConverter;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@SideOnly(Side.CLIENT)
public final class ModelCanister implements IModel, IModelCustomData, IRetexturableModel {
	public static final ModelResourceLocation LOCATION =
			new ModelResourceLocation(new ResourceLocation(SteamAgeRevolution.MODID, "canister"), "inventory");

	// minimal Z offset to prevent depth-fighting
	private static final float NORTH_Z_BASE = 7.496f / 16f;
	private static final float SOUTH_Z_BASE = 8.504f / 16f;
	private static final float NORTH_Z_FLUID = 7.498f / 16f;
	private static final float SOUTH_Z_FLUID = 8.502f / 16f;

	public static final IModel MODEL = new ModelCanister();

	private final ResourceLocation baseLocation;
	private final ResourceLocation liquidLocation;

	private final Fluid fluid;

	public ModelCanister() {
		this(null, null, null);
	}

	public ModelCanister(ResourceLocation baseLocation, ResourceLocation liquidLocation, Fluid fluid) {
		this.baseLocation = baseLocation;
		this.liquidLocation = liquidLocation;
		this.fluid = fluid;
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();
		if(baseLocation != null)
			builder.add(baseLocation);
		if(liquidLocation != null)
			builder.add(liquidLocation);

		return builder.build();
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

		ImmutableMap<TransformType, TRSRTransformation> transformMap =
				IPerspectiveAwareModel.MapWrapper.getTransforms(state);

		TRSRTransformation transform = state.apply(Optional.<IModelPart> absent()).or(TRSRTransformation.identity());
		TextureAtlasSprite fluidSprite = null;
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		if(fluid != null) {
			fluidSprite = bakedTextureGetter.apply(fluid.getStill());
		}

		if(baseLocation != null) {
			// build base (insidest)
			IBakedModel model =
					(new ItemLayerModel(ImmutableList.of(baseLocation))).bake(state, format, bakedTextureGetter);
			builder.addAll(model.getQuads(null, null, 0));
		}
		if(liquidLocation != null && fluidSprite != null) {
			TextureAtlasSprite liquid = bakedTextureGetter.apply(liquidLocation);
			// build liquid layer (inside)
			builder.addAll(ItemTextureQuadConverter.convertTexture(format, transform, liquid, fluidSprite,
					NORTH_Z_FLUID, EnumFacing.NORTH, fluid.getColor()));
			builder.addAll(ItemTextureQuadConverter.convertTexture(format, transform, liquid, fluidSprite,
					SOUTH_Z_FLUID, EnumFacing.SOUTH, fluid.getColor()));
		}

		return new BakedDynBucketz(this, builder.build(), fluidSprite, format, Maps.immutableEnumMap(transformMap),
				Maps.<String, IBakedModel> newHashMap());
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	/**
	 * Sets the liquid in the model.
	 * fluid - Name of the fluid in the FluidRegistry
	 * flipGas - If "true" the model will be flipped upside down if the liquid is a gas. If "false" it wont
	 * <p/>
	 * If the fluid can't be found, water is used
	 */
	@Override
	public ModelCanister process(ImmutableMap<String, String> customData) {
		String fluidName = customData.get("fluid");
		Fluid fluid = FluidRegistry.getFluid(fluidName);

		if(fluid == null)
			fluid = this.fluid;

		// create new model with correct liquid
		return new ModelCanister(baseLocation, liquidLocation, fluid);
	}

	/**
	 * Allows to use different textures for the model.
	 * There are 3 layers:
	 * base - The empty bucket/container
	 * fluid - A texture representing the liquid portion. Non-transparent = liquid
	 * cover - An overlay that's put over the liquid (optional)
	 * <p/>
	 * If no liquid is given a hardcoded variant for the bucket is used.
	 */
	@Override
	public ModelCanister retexture(ImmutableMap<String, String> textures) {

		ResourceLocation base = baseLocation;
		ResourceLocation liquid = liquidLocation;

		if(textures.containsKey("base"))
			base = new ResourceLocation(textures.get("base"));
		if(textures.containsKey("fluid"))
			liquid = new ResourceLocation(textures.get("fluid"));

		return new ModelCanister(base, liquid, fluid);
	}

	public enum LoaderDynBucketz implements ICustomModelLoader {
		instance;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getResourceDomain().equals("extrafood")
					&& modelLocation.getResourcePath().contains("glassbottles");
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) {
			return MODEL;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {
			// no need to clear cache since we create a new model instance
		}
	}

	private static final class BakedDynBucketOverrideHandlerz extends ItemOverrideList {
		public static final BakedDynBucketOverrideHandlerz INSTANCE = new BakedDynBucketOverrideHandlerz();

		private BakedDynBucketOverrideHandlerz() {
			super(ImmutableList.<ItemOverride> of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world,
				EntityLivingBase entity) {
			FluidStack fluidStack =
					stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).getTankProperties()[0]
							.getContents();

			// not a fluid item apparently
			if(fluidStack == null) {
				// empty bucket
				return originalModel;
			}

			BakedDynBucketz model = (BakedDynBucketz) originalModel;

			Fluid fluid = fluidStack.getFluid();
			String name = fluid.getName();

			if(!model.cache.containsKey(name)) {
				IModel parent = model.parent.process(ImmutableMap.of("fluid", name));
				Function<ResourceLocation, TextureAtlasSprite> textureGetter;
				textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
					public TextureAtlasSprite apply(ResourceLocation location) {
						return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
					}
				};

				IBakedModel bakedModel =
						parent.bake(new SimpleModelState(model.transforms), model.format, textureGetter);
				model.cache.put(name, bakedModel);
				return bakedModel;
			}

			return model.cache.get(name);
		}
	}

	// the dynamic bucket is based on the empty bucket
	private static final class BakedDynBucketz implements IPerspectiveAwareModel {

		private final ModelCanister parent;
		// FIXME: guava cache?
		private final Map<String, IBakedModel> cache; // contains all the baked models since they'll never change
		private final ImmutableMap<TransformType, TRSRTransformation> transforms;
		private final ImmutableList<BakedQuad> quads;
		private final TextureAtlasSprite particle;
		private final VertexFormat format;

		public BakedDynBucketz(ModelCanister parent, ImmutableList<BakedQuad> quads, TextureAtlasSprite particle,
				VertexFormat format, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
				Map<String, IBakedModel> cache) {
			this.quads = quads;
			this.particle = particle;
			this.format = format;
			this.parent = parent;
			this.transforms = transforms;
			this.cache = cache;
		}

		@Override
		public ItemOverrideList getOverrides() {
			return BakedDynBucketOverrideHandlerz.INSTANCE;
		}

		@Override
		public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
			return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, transforms, cameraTransformType);
		}

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
			if(side == null)
				return quads;
			return ImmutableList.of();
		}

		public boolean isAmbientOcclusion() {
			return true;
		}

		public boolean isGui3d() {
			return false;
		}

		public boolean isBuiltInRenderer() {
			return false;
		}

		public TextureAtlasSprite getParticleTexture() {
			return particle;
		}

		public ItemCameraTransforms getItemCameraTransforms() {
			return ItemCameraTransforms.DEFAULT;
		}
	}
}