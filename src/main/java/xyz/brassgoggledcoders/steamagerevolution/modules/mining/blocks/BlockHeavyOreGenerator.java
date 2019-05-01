package xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks;

import com.teamacronymcoders.base.blocks.BlockTEBase;
import com.teamacronymcoders.base.items.IIsHidden;
import com.teamacronymcoders.base.items.itemblocks.ItemBlockGeneric;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.TileEntityHeavyOreGenerator;

public class BlockHeavyOreGenerator extends BlockTEBase<TileEntityHeavyOreGenerator> {

	String type;
	
	public BlockHeavyOreGenerator(String type) {
		super(Material.ROCK, "heavy_ore_generator_" + type);
		this.type = type;
		 this.setItemBlock(new ItemBlockHeavyOreGenerator(this));
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityHeavyOreGenerator.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityHeavyOreGenerator();
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String getTileName(ResourceLocation blockName) {
        return "heavy_ore_generator";
	}
	
	public static class ItemBlockHeavyOreGenerator extends ItemBlockGeneric<BlockHeavyOreGenerator> {//implements IIsHidden {

		public ItemBlockHeavyOreGenerator(BlockHeavyOreGenerator block) {
			super(block);
		}
		
	}

}
