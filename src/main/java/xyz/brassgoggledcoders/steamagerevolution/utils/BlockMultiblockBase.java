package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.util.List;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockTEBase;
import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockMultiblockBase<T extends MultiblockTileEntityBase> extends BlockTEBase<T> {

	public BlockMultiblockBase(Material material, String name) {
		super(material, name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		IMultiblockTileInfo tile = (IMultiblockTileInfo) this.createTileEntity(player, null);
		IMultiblockControllerInfo controller = tile.getControllerInfo();

		// TODO Localisation
		tooltip.add("Multiblock: " + controller.getName());
		if(controller.getMaxXSize() != -1) {
			tooltip.add("Maximum Size (XYZ): " + controller.getMaxXSize() + "x" + controller.getMaxYSize() + "x"
					+ controller.getMaxZSize());
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
