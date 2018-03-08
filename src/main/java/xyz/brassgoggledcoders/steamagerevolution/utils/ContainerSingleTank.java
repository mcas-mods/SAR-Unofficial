package xyz.brassgoggledcoders.steamagerevolution.utils;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerSingleTank extends ContainerBase {
	public ContainerSingleTank(EntityPlayer player, TileEntity tile) {
		this.createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
