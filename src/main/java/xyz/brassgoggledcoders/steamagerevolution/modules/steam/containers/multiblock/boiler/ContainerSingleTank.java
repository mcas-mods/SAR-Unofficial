package xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import xyz.brassgoggledcoders.boilerplate.containers.ContainerBase;

public class ContainerSingleTank extends ContainerBase {
	public ContainerSingleTank(EntityPlayer player, TileEntity tile) {

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
