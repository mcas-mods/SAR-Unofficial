package xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks;

import com.teamacronymcoders.base.blocks.BlockSidedBase;
import com.teamacronymcoders.base.guisystem.GuiOpener;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.TileEntitySemisolidLoader;

public class BlockSemisolidLoader extends BlockSidedBase<TileEntitySemisolidLoader> {

	public BlockSemisolidLoader() {
		super(Material.IRON, "crushed_loader");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySemisolidLoader.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySemisolidLoader();
	}

	@Override
	protected boolean handleAdditionalTileActions(World worldIn, TileEntitySemisolidLoader tileEntity,
			EntityPlayer playerIn, EnumHand hand, ItemStack currentItem) {
		BlockPos pos = tileEntity.getPos();
		if (!playerIn.isSneaking()) {
			GuiOpener.openTileEntityGui(getMod(), playerIn, worldIn, pos);
			return true;
		}
		return false;
	}
}
