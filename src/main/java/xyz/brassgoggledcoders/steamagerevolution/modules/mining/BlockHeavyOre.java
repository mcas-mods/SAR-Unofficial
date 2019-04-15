package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BlockHeavyOre extends BlockBase {
	
	public static final PropertyInteger CHUNKS = PropertyInteger.create("chunks", 1, 8);
	ItemStack drop;

	public BlockHeavyOre(String type) {
		super(Material.ROCK, "heavy_ore_" + type);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHUNKS, 8));
		drop = OreDictUtils.getPreferredItemStack("ingot" + type);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(CHUNKS, meta + 1);
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
        return state.getValue(CHUNKS).intValue() - 1;
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CHUNKS});
    }

}
