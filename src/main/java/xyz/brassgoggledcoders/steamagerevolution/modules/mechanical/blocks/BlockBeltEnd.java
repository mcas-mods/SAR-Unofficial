package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.api.BoilerplateAPI;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.boilerplate.blocks.SideType;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntityBeltEnd;

public class BlockBeltEnd extends BlockTEBase {
	public BlockBeltEnd(Material mat, String name) {
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityBeltEnd.class;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBeltEnd();
	}

	// TODO Handling for unpairing when blocks are broken

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(heldItem != null && heldItem.hasCapability(BoilerplateAPI.WRENCH_CAPABILITY, side)) {
			if(worldIn.getTileEntity(pos) instanceof TileEntityBeltEnd) {
				TileEntityBeltEnd belt = (TileEntityBeltEnd) worldIn.getTileEntity(pos);
				if(playerIn.isSneaking()) {
					if(belt.isTilePaired()) {
						belt.unpair();
						return true;
					}
				}
				else {
					// If there's already an output side, exit.
					for(int i = 0; i < EnumFacing.VALUES.length; i++) {
						if(belt.getSideValue(i) == SideType.OUTPUT)
							return false;
					}

					// If not, set clicked side to output.
					belt.setSideConfig(side.ordinal(), SideType.OUTPUT);
				}
			}
		}
		return false;
	}
}