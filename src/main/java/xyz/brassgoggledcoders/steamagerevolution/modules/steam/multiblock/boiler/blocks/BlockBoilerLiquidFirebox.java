package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerLiquidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerPart;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockBoilerLiquidFirebox extends BlockMultiblockBase<TileEntityBoilerLiquidFirebox> {

	public BlockBoilerLiquidFirebox(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityBoilerPart te = getTileEntity(worldIn, pos).get();
		if(te != null && te.isConnected()) {
			playerIn.sendStatusMessage(TextUtils.representTankContents(te.getMultiblockController().liquidFuelTank),
					true);
			return true;
		}
		return false;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityBoilerLiquidFirebox.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityBoilerLiquidFirebox();
	}

}
