package xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.base.Reference;
import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.blocks.IHasBlockColor;
import com.teamacronymcoders.base.blocks.IHasBlockStateMapper;
import com.teamacronymcoders.base.client.models.generator.IHasGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.GeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.IGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.ModelType;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.util.files.templates.TemplateFile;
import com.teamacronymcoders.base.util.files.templates.TemplateManager;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.MiningUtils;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemBlockHeavyOre;

public class BlockHeavyOreIndicator extends BlockBase implements IHasGeneratedModel, IHasBlockStateMapper, IHasBlockColor {

	BlockHeavyOre type;
	
	public BlockHeavyOreIndicator(BlockHeavyOre ore, MaterialPart part) {
		super(Material.ROCK, ore.type.toLowerCase() + "_heavy_ore_indicator");
		this.setItemBlock(new ItemBlockHeavyOre<>(this, "heavy_ore_indicator", part.getMaterial()));
		this.type = ore;
	}
	
	public BlockHeavyOre getOre() {
		return type;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.COBBLESTONE);
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(playerIn.getHeldItem(hand).getItem() == ModuleMetalworking.hammer) {
			if(facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN)) {
				facing = EnumFacing.byHorizontalIndex(worldIn.rand.nextInt(3));
			}
			MiningUtils.generateOreSeam(worldIn, pos, facing.getOpposite(), 10 + worldIn.rand.nextInt(10), 4 + worldIn.rand.nextInt(2), 3 + worldIn.rand.nextInt(3));
			return true;
    	}
        return false;
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
					.getTemplateFile(new ResourceLocation(Reference.MODID, "ore_block_state"));
			Map<String, String> replacements = Maps.newHashMap();
			replacements.put("texture",
					new ResourceLocation("minecraft:blocks/stone").toString());
			replacements.put("particle",
					new ResourceLocation("minecraft:blocks/stone").toString());
			replacements.put("ore_shadow",
					new ResourceLocation(Reference.MODID, "blocks/ore_shadow").toString());
			replacements.put("ore",
					new ResourceLocation(Reference.MODID, "blocks/poor_ore").toString());
			templateFile.replaceContents(replacements);
			models.add(new GeneratedModel("materials/" + this.getName(), ModelType.BLOCKSTATE,
					templateFile.getFileContents()));
		});

		return models;
	}
	
	@Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

	@Override
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
		return type.materialPart.getColor();
	}

}
