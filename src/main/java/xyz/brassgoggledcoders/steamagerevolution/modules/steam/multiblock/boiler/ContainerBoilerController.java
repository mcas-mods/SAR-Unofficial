package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerBoilerController extends ContainerBase {
	public ContainerBoilerController(EntityPlayer player, TileEntityBoilerController tileEntityBoilerController) {

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}