package xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks;

import java.util.List;
import java.util.function.Predicate;

import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.blocks.IAmBlock;
import com.teamacronymcoders.base.blocks.IHasBlockStateMapper;
import com.teamacronymcoders.base.blocks.IHasItemBlock;
import com.teamacronymcoders.base.client.models.IHasModel;
import com.teamacronymcoders.base.items.itemblocks.ItemBlockModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartSemisolid;

public class BlockRailDumping extends BlockRailBase
		implements IHasBlockStateMapper, IHasItemBlock, IHasModel, IAmBlock {
	private IBaseMod mod;
	private ItemBlock itemBlock;
	private String name = "dump_rail";

	public static final PropertyBool NORTH_WEST = PropertyBool.create("north_west");
	public static final PropertyEnum<EnumRailDirection> SHAPE = PropertyEnum.create("shape", EnumRailDirection.class,
			RailPredicates.FLAT_STRAIGHT::test);
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockRailDumping() {
		super(false);
		this.itemBlock = new ItemBlockModel<>(this);
		this.setTranslationKey("dump_rail");
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(POWERED, false)
				.withProperty(SHAPE, EnumRailDirection.NORTH_SOUTH).withProperty(NORTH_WEST, true));
	}

	@Override
	public IBaseMod getMod() {
		return mod;
	}

	@Override
	public void setMod(IBaseMod mod) {
		this.mod = mod;
	}

	@Override
	public ItemBlock getItemBlock() {
		return itemBlock;
	}

	@Override
	public boolean isFlexibleRail(IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public Block getBlock() {
		return this;
	}

	@Override
	public List<String> getModelNames(List<String> modelNames) {
		modelNames.add(name);
		return modelNames;
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (state.getValue(POWERED)) {
			TileEntity te = world.getTileEntity(pos.down());
			if (cart instanceof EntityMinecartSemisolid && te != null
					&& te.hasCapability(SARCapabilities.SEMISOLID_HANDLER, EnumFacing.UP)) {
				EntityMinecartSemisolid carrier = (EntityMinecartSemisolid) cart;
				ISemisolidHolder cartHolder = carrier.getInventory().ore.getHandler().getHolders()[0];
				if (cartHolder.getAmount() > 0) {
					ISemisolidHandler handler = te.getCapability(SARCapabilities.SEMISOLID_HANDLER, EnumFacing.UP);
					if ((handler.getHolders()[0].getCrushed() == null || cartHolder.getCrushed()
							.getMaterial() == handler.getHolders()[0].getCrushed().getMaterial())
							&& cartHolder.getAmount() <= (handler.getHolders()[0].getHolderCapacity()
									- handler.getHolders()[0].getAmount())) {
						handler.fill(cartHolder.getCrushed());
						cartHolder.drain(cartHolder.getCrushed().getMaterial(), cartHolder.getAmount());
					}
				}
			}
		}
		super.onMinecartPass(world, cart, pos);
	}

	@Override
	protected void updateState(IBlockState state, World world, BlockPos pos, Block block) {
		boolean isStatePowered = state.getValue(POWERED);
		boolean isWorldPowered = world.isBlockPowered(pos);

		if (isWorldPowered != isStatePowered) {
			world.setBlockState(pos, state.withProperty(POWERED, isWorldPowered), 3);
			world.notifyNeighborsOfStateChange(pos.down(), this, true);
		}
		super.updateState(state, world, pos, block);
	}

	@Override
	public boolean canMakeSlopes(IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE, POWERED, NORTH_WEST);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		IBlockState blockState = this.getDefaultState();
		if (meta > 7) {
			meta = meta - 8;
			blockState = blockState.withProperty(POWERED, true);
		}
		if (meta % 2 != 0) {
			blockState = blockState.withProperty(NORTH_WEST, false);
		}
		if (meta > 1) {
			blockState = blockState.withProperty(SHAPE, EnumRailDirection.EAST_WEST);
		}

		return blockState;
	}

	@Override
	public int getMetaFromState(IBlockState blockState) {
		int facing = blockState.getValue(SHAPE) == EnumRailDirection.NORTH_SOUTH ? 0 : 2;
		facing += blockState.getValue(NORTH_WEST) ? 0 : 1;
		int powered = blockState.getValue(POWERED) ? 8 : 0;
		return facing + powered;
	}

	@Override
	public String getVariant(IBlockState blockState) {
		int facing = blockState.getValue(SHAPE) == EnumRailDirection.NORTH_SOUTH ? 0 : 2;
		facing += blockState.getValue(NORTH_WEST) ? 0 : 1;
		return "facing=" + EnumFacing.VALUES[facing + 2] + ",powered=" + blockState.getValue(POWERED).toString();
	}

	@Override
	public IProperty<EnumRailDirection> getShapeProperty() {
		return SHAPE;
	}

	public static class RailPredicates {
		public static final Predicate<EnumRailDirection> FLAT_STRAIGHT = enumRailDirection -> enumRailDirection == EnumRailDirection.EAST_WEST
				|| enumRailDirection == EnumRailDirection.NORTH_SOUTH;

		public static final Predicate<EnumRailDirection> ALL_CURVES = enumRailDirection -> enumRailDirection == EnumRailDirection.NORTH_WEST
				|| enumRailDirection == EnumRailDirection.SOUTH_WEST
				|| enumRailDirection == EnumRailDirection.NORTH_EAST
				|| enumRailDirection == EnumRailDirection.SOUTH_EAST;
		public static final Predicate<EnumRailDirection> ALL_FLAT = enumRailDirection -> ALL_CURVES.or(FLAT_STRAIGHT)
				.test(enumRailDirection);
	}
}