package xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler;

import com.teamacroynmcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerSingleTank extends ContainerBase {
	public ContainerSingleTank(EntityPlayer player, TileEntity tile) {

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
