package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.tileentities;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.ContainerFurnaceMonitor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.GuiFurnaceMonitor;

public class TileEntityFurnaceMonitor extends TileEntityFurnacePart implements IHasGui {
	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiFurnaceMonitor(new ContainerFurnaceMonitor(entityPlayer, this), this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerFurnaceMonitor(entityPlayer, this);
	}
}
