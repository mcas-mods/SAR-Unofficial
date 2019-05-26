package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.sawmill;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySawmillAxle extends TileEntitySawmillPart {

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox() {
		if(isConnected()) {
			return new AxisAlignedBB(getMultiblockController().getMinimumCoord(),
					getMultiblockController().getMaximumCoord());
		}
		else {
			return super.getRenderBoundingBox();
		}
	}
}
