package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityBoilerController extends TileEntityBoilerPart implements IHasGui {

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, NBTTagCompound context) {
		return new GuiBoilerController(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, NBTTagCompound context) {
		return new ContainerBoilerController(entityPlayer, this);
	}

}
