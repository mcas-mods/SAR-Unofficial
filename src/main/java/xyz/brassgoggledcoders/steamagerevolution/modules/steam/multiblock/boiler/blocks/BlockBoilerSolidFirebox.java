package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerPart;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

public class BlockBoilerSolidFirebox extends BlockMultiblockBase<TileEntityBoilerSolidFirebox> {

	public BlockBoilerSolidFirebox(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityBoilerPart te = getTileEntity(worldIn, pos);
		if(te != null && te.isConnected()) {
			playerIn.sendStatusMessage(
					TextUtils.representInventoryContents(te.getMultiblockController().solidFuelInventory), true);
			return true;
		}
		return false;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityBoilerSolidFirebox.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityBoilerSolidFirebox();
	}

}
