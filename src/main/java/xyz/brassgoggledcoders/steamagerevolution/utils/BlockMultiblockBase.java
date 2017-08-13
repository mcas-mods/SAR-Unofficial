package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.util.List;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockTEBase;
import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockMultiblockBase<T extends MultiblockTileEntityBase> extends BlockTEBase<T> {

	public BlockMultiblockBase(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(playerIn.isSneaking()) {
			MultiblockTileEntityBase tile = getTileEntity(worldIn, pos);
			if(tile.isConnected() && tile.getMultiblockController().getLastError() != null) {
				playerIn.sendStatusMessage(tile.getMultiblockController().getLastError().getChatMessage(), true);
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		IMultiblockTileInfo tile = (IMultiblockTileInfo) this.createTileEntity(player, null);
		IMultiblockControllerInfo controller = tile.getControllerInfo();

		// TODO Localisation
		tooltip.add("Multiblock: " + controller.getName());
		if(controller.getMinimumYSize() > 1) {
			tooltip.add("Minimum Size (XYZ): " + controller.getMinimumXSize() + "x" + controller.getMinimumYSize() + "x"
					+ controller.getMinimumZSize());
		}
		if(controller.getMaximumXSize() != -1) { // TODO
			tooltip.add("Maximum Size (XYZ): " + controller.getMaximumXSize() + "x" + controller.getMaximumYSize() + "x"
					+ controller.getMaximumZSize());
		}
		if(tile.getPartFunction() != null) {
			tooltip.add("Part function: " + tile.getPartFunction());
		}
		String[] positions = new String[] {"Frame", "Sides", "Top", "Bottom", "Interior"};
		String valid = "Valid part positions: ";
		for(int possiblePositions = 0; possiblePositions < 5; possiblePositions++) {
			if(tile.getValidPositions()[possiblePositions])
				valid += positions[possiblePositions] + ",";
		}
		tooltip.add(valid.substring(0, valid.length() - 1));
		super.addInformation(stack, player, tooltip, advanced);
	}
}
