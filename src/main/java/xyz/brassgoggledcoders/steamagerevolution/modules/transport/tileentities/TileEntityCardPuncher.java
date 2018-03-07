package xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCardPuncher extends TileEntityBase implements IHasGui {

	ItemStackHandler inventory = new ItemStackHandler(3);

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		inventory.deserializeNBT(data.getCompoundTag("inventory"));
	}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		data.setTag("inventory", inventory.serializeNBT());
		return data;
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiCardPuncher(new ContainerCardPuncher(inventory, entityPlayer.inventory));
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerCardPuncher(inventory, entityPlayer.inventory);
	}

}
