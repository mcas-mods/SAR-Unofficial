package xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHeavyOre extends BlockBase {

	public static final PropertyInteger CHUNKS = PropertyInteger.create("chunks", 1, 8);
	ItemStack drop;
	public String type;

	public BlockHeavyOre(String type) {
		super(Material.ROCK, "heavy_ore_" + type.toLowerCase());
		this.type = type;
		this.drop = OreDictUtils.getPreferredItemStack("rock" + type);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHUNKS, 8));
		this.setHardness(75F);
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
		if (!world.isRemote && chunks > 1) {
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
}
