package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.turbine;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockTEBase;

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
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class BlockSteamInput extends BlockTEBase<TileEntitySteamInput> {

	public BlockSteamInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntitySteamInput tile = getTileEntity(world, pos);
		if(tile != null && !player.isSneaking()) {
			TileEntitySteamInput port = (TileEntitySteamInput) world.getTileEntity(pos);
			port.buffer.fill(new FluidStack(FluidRegistry.getFluid("steam"), Fluid.BUCKET_VOLUME), true);
			player.openGui(SteamAgeRevolution.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		return false;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteamInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteamInput();
	}

}
