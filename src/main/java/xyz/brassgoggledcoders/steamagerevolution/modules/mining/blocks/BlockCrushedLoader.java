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
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.TileEntityCrushedLoader;

public class BlockCrushedLoader extends BlockSidedBase<TileEntityCrushedLoader> {

	public BlockCrushedLoader() {
		super(Material.IRON, "crushed_loader");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityCrushedLoader.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityCrushedLoader();
	}

	@Override
	protected boolean handleAdditionalTileActions(World worldIn, TileEntityCrushedLoader tileEntity,
			EntityPlayer playerIn, EnumHand hand, ItemStack currentItem) {
		BlockPos pos = tileEntity.getPos();
		if (!playerIn.isSneaking()) {
			GuiOpener.openTileEntityGui(getMod(), playerIn, worldIn, pos);
			return true;
		}
		return false;
	}
}
