package xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.base.blocks.*;
import com.teamacronymcoders.base.client.models.generator.IHasGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.*;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.util.OreDictUtils;
import com.teamacronymcoders.base.util.files.templates.TemplateFile;
import com.teamacronymcoders.base.util.files.templates.TemplateManager;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemBlockHeavyOre;

public class BlockHeavyOre extends BlockBase implements IHasGeneratedModel, IHasBlockStateMapper, IHasBlockColor {

	public static final PropertyInteger CHUNKS = PropertyInteger.create("chunks", 1, 8);
	ItemStack drop;
	public String type;
	public MaterialPart materialPart;

	public BlockHeavyOre(MaterialPart part, String type) {
		super(Material.ROCK, "heavy_ore_" + type.toLowerCase());
		this.materialPart = part;
		this.type = type;
		this.drop = OreDictUtils.getPreferredItemStack("rock" + type);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHUNKS, 8));
		this.setHardness(75F);
		this.setItemBlock(new ItemBlockHeavyOre(this));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(CHUNKS, meta + 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(CHUNKS).intValue() - 1;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { CHUNKS });
	}

	@Override
	public void onPlayerDestroy(World world, BlockPos pos, IBlockState state) {
		int chunks = state.getValue(BlockHeavyOre.CHUNKS).intValue();
		if(!world.isRemote && chunks > 1) {
			EntityItem itemE = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(),
					((BlockHeavyOre) state.getBlock()).drop);
			world.spawnEntity(itemE);
			world.setBlockState(pos, state.withProperty(BlockHeavyOre.CHUNKS, chunks - 1), 2);
		}
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		drops.add(drop);
	}

	@Override
	public List<String> getModelNames(List<String> modelNames) {
		modelNames.add("materials/" + this.getName());
		return modelNames;
	}

	@Override
	public ResourceLocation getResourceLocation(IBlockState blockState) {
		return new ResourceLocation(SteamAgeRevolution.MODID, "materials/" + this.getName());
	}

	@Override
	public List<IGeneratedModel> getGeneratedModels() {
		List<IGeneratedModel> models = Lists.newArrayList();
		this.getResourceLocations(Lists.newArrayList()).forEach(resourceLocation -> {
			TemplateFile templateFile = TemplateManager
					.getTemplateFile(new ResourceLocation(SteamAgeRevolution.MODID, "heavy_ore"));
			Map<String, String> replacements = Maps.newHashMap();

			replacements.put("texture",
					new ResourceLocation(SteamAgeRevolution.instance.getID(), "blocks/heavy_ore").toString());
			templateFile.replaceContents(replacements);
			models.add(new GeneratedModel("materials/" + this.getName(), ModelType.BLOCKSTATE,
					templateFile.getFileContents()));
		});

		return models;
	}

	@Override
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
		return materialPart.getColor();
	}
}
