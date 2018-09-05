package xyz.brassgoggledcoders.steamagerevolution.modules.transport.blocks;

import com.teamacronymcoders.base.Capabilities;
import com.teamacronymcoders.base.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.ModuleTransport;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities.TileEntityPneumaticSender;

public class BlockPneumaticTube extends BlockBase {

    public static final PropertyEnum<Axis> AXIS = PropertyEnum.<Axis>create("axis", Axis.class);
    public static final AxisAlignedBB X_TUBE_AABB = new AxisAlignedBB(0.0D, 0.2D, 0.2D, 1.0D, 0.8D, 0.8D);
    public static final AxisAlignedBB Y_TUBE_AABB = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 1.0D, 0.8D);
    public static final AxisAlignedBB Z_TUBE_AABB = new AxisAlignedBB(0.2D, 0.2D, 0.0D, 0.8D, 0.8D, 1.0D);

    public BlockPneumaticTube(Material mat, String name) {
        super(mat, name);
        setDefaultState(blockState.getBaseState().withProperty(AXIS, Axis.X));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(AXIS)) {
            case Y:
                return Y_TUBE_AABB;
            case Z:
                return Z_TUBE_AABB;
            default:
                return X_TUBE_AABB;
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!playerIn.getHeldItem(hand).isEmpty()
                && playerIn.getHeldItem(hand).hasCapability(Capabilities.TOOL, facing)) {
            state.cycleProperty(AXIS);
            return true;
        }

        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(meta).withProperty(AXIS, facing.getOpposite().getAxis());
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        switch (meta) {
            case 0:
                return getDefaultState().withProperty(AXIS, Axis.X);
            case 1:
                return getDefaultState().withProperty(AXIS, Axis.Z);
            case 2:
                return getDefaultState().withProperty(AXIS, Axis.Y);
        }
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        Axis axis = state.getValue(AXIS);
        if (axis == Axis.Z) {
            return 1;
        } else if (axis == Axis.Y) {
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{AXIS});
    }

    // TODO Cascade if possible
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        findAndNotifySenderToRecalcCache(worldIn, pos, state);
    }

    // See above
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack) {
        findAndNotifySenderToRecalcCache(worldIn, pos, state);
    }

    private void findAndNotifySenderToRecalcCache(World worldIn, BlockPos pos, IBlockState state) {
        for (int i = 1; i < TileEntityPneumaticSender.maxDistance; i++) {
            BlockPos checkPos = pos.offset(EnumFacing.getFacingFromAxis(AxisDirection.POSITIVE, state.getValue(AXIS)),
                    i);
            if (worldIn.getBlockState(checkPos).getBlock() == ModuleTransport.pneumaticSender) {
                ((TileEntityPneumaticSender) worldIn.getTileEntity(checkPos)).recalculateCache(worldIn, checkPos);
                return;
            }
            BlockPos checkNeg = pos.offset(EnumFacing.getFacingFromAxis(AxisDirection.NEGATIVE, state.getValue(AXIS)),
                    i);
            if (worldIn.getBlockState(checkNeg).getBlock() == ModuleTransport.pneumaticSender) {
                ((TileEntityPneumaticSender) worldIn.getTileEntity(checkNeg)).recalculateCache(worldIn, checkNeg);
                return;
            }
        }
    }
}
