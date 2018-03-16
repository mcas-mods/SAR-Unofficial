package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.utils.ContainerSimpleSlots;
import xyz.brassgoggledcoders.steamagerevolution.utils.GuiSimpleSlots;

public class TileEntityVatFrame extends TileEntityVatPart implements IHasGui {

	@SideOnly(Side.CLIENT)
	@Override
	public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox() {
		if(this.isConnected()) {
			return new AxisAlignedBB(this.getMultiblockController().getMinimumCoord(),
					this.getMultiblockController().getMaximumCoord());
		}
		else
			return super.getRenderBoundingBox();
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return new GuiSimpleSlots(entityPlayer, this, 3);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return new ContainerSimpleSlots(entityPlayer, this, 3);
	}
}
