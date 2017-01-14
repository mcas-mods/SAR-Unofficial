package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.ModuleMechanical;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockMechanicalTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers.TileEntitySaw;

public class BlockSaw extends BlockMechanicalTEBase<TileEntitySaw> {

	public BlockSaw() {
		super(Material.ANVIL, "saw");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySaw.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySaw();
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.setBlockState(pos.up(), ModuleMechanical.sawBlade.getDefaultState());
		world.setBlockState(pos.up().north(), ModuleMechanical.sawBlade.getDefaultState());
		world.setBlockState(pos.up().south(), ModuleMechanical.sawBlade.getDefaultState());
		world.setBlockState(pos.south(), ModuleMechanical.sawBlade.getDefaultState());
		world.setBlockState(pos.north(), ModuleMechanical.sawBlade.getDefaultState());
		world.setBlockState(pos.down(), ModuleMechanical.sawBlade.getDefaultState());
		world.setBlockState(pos.down().north(), ModuleMechanical.sawBlade.getDefaultState());
		world.setBlockState(pos.down().south(), ModuleMechanical.sawBlade.getDefaultState());
	}

}
