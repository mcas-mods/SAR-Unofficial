package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks;

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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler.TileEntityWaterInput;

public class BlockWaterInput extends BlockTEBase<TileEntityWaterInput> {

	public BlockWaterInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityWaterInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityWaterInput();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(worldIn.getTileEntity(pos) instanceof TileEntityWaterInput) {
			TileEntityWaterInput port = (TileEntityWaterInput) worldIn.getTileEntity(pos);
			port.buffer.fill(new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME), true);
			return true;
		}
		return false;
	}

}
