package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.multiblock.boiler;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockTEBase;
import com.teamacronymcoders.base.guisystem.target.GuiBlockTarget;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler.TileEntityWaterInput;

public class BlockWaterInput extends BlockTEBase<TileEntityWaterInput> {

	public BlockWaterInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityWaterInput tile = getTileEntity(world, pos);
		if(tile != null && !player.isSneaking()) {
			// TODO Tmp
			TileEntityWaterInput port = (TileEntityWaterInput) world.getTileEntity(pos);
			port.buffer.fill(new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME), true);
			this.getMod().getGuiHandler().openGui(new GuiBlockTarget(this, pos), new NBTTagCompound(), true, player,
					world);
			return true;
		}
		return false;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityWaterInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityWaterInput();
	}

}
