package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import com.teamacronymcoders.base.multiblock.rectangular.PartPosition;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMultiblockPositional<C extends SARRectangularMultiblockControllerBase>
		extends BlockMultiblockBase<C> {

	protected static final PropertyEnum<PartPosition> position = PartPosition.createProperty("position");

	public BlockMultiblockPositional(Class<? extends TileEntityMultiblockBase> tileClass, Material material,
			String name, boolean[] validPositions, String tankToWrap, String inventoryToWrap, boolean isTransparent) {
		super(tileClass, material, name, validPositions, tankToWrap, inventoryToWrap, isTransparent);
		this.setDefaultState(this.blockState.getBaseState().withProperty(position, PartPosition.UNKNOWN));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, position);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(position, this.getTileEntity(worldIn, pos).getPartPosition());

	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

}
