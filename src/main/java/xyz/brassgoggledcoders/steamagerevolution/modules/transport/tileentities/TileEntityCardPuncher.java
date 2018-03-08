package xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityInventoryBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCardPuncher extends TileEntityInventoryBase implements IHasGui {

	public TileEntityCardPuncher() {
		super(13);
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiCardPuncher((TileEntityCardPuncher) world.getTileEntity(blockPos), entityPlayer.inventory);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerCardPuncher((TileEntityCardPuncher) world.getTileEntity(blockPos), entityPlayer.inventory);
	}

}
