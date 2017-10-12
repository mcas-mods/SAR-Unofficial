package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

public class BlockEvaporator extends BlockTEBase<TileEntityEvaporator> {
	public BlockEvaporator(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityEvaporator.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityEvaporator();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityEvaporator te = getTileEntity(worldIn, pos);
		if(!worldIn.isRemote && te != null) {
			if(playerIn.isSneaking()) {
				playerIn.sendStatusMessage(TextUtils.representTankContents(te.input), true);
				playerIn.sendStatusMessage(TextUtils.representTankContents(te.output), true);
				return true;
			}
			else {
				return FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing);
			}
		}
		return false;
	}
}
