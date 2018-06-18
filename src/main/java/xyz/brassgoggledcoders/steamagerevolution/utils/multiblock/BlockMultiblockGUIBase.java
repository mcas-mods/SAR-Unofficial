package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import com.teamacronymcoders.base.guisystem.GuiOpener;
import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockMultiblockGUIBase<T extends MultiblockTileEntityBase<?>> extends BlockMultiblockBase<T> {

	public BlockMultiblockGUIBase(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = getTileEntity(worldIn, pos).get();
		if(te != null && !playerIn.isSneaking()) {
			if(te instanceof MultiblockTileEntityBase) {
				MultiblockTileEntityBase<?> multiblockTile = (MultiblockTileEntityBase<?>) te;
				if(multiblockTile.isConnected() && multiblockTile.getMultiblockController().isAssembled()) {
					GuiOpener.openMultiblockGui(getMod(), playerIn, worldIn, pos);
					return true;
				}
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
}
