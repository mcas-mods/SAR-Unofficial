package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
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
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntityBeltEnd;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntityPaired;

public class BlockBeltEnd extends BlockTEBase {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	private float slipFactor;

	public BlockBeltEnd(Material mat, String name, float slipFactor) {
		super(mat, name);
		this.slipFactor = slipFactor;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityBeltEnd.class;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBeltEnd(slipFactor);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.UP), 2);
		super.onBlockAdded(worldIn, pos, state);
	}

	@Override
	public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		if(worldIn.getTileEntity(pos) instanceof TileEntityBeltEnd) {
			TileEntityBeltEnd belt_end = (TileEntityBeltEnd) worldIn.getTileEntity(pos);
			if(belt_end.isTilePaired()) {
				TileEntityPaired.unpair(belt_end);
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(worldIn.getTileEntity(pos) instanceof TileEntityBeltEnd) {
			TileEntityBeltEnd belt = (TileEntityBeltEnd) worldIn.getTileEntity(pos);
			// TODO Changing output sides
			if(!ItemStackUtils.isItemNonNull(heldItem)) {
				// If there's already an output side, exit.
				for(int i = 0; i < EnumFacing.VALUES.length; i++) {
					if(belt.getSideValue(i) == SideType.OUTPUT)
						return false;
				}

				// If not, set clicked side to output.
				belt.setSideConfig(side.ordinal(), SideType.OUTPUT);
			}
			else if(heldItem.hasCapability(BoilerplateAPI.TOOL_CAPABILITY, side)) {
				if(belt.isTilePaired()) {
					SteamAgeRevolution.instance.getLogger().devInfo("Attempted unpairing");
					TileEntityBeltEnd.unpair(belt);
					return true;
				}

			}
		}
		return false;

	}

	public float getSlipFactor() {
		return slipFactor;
	}
}