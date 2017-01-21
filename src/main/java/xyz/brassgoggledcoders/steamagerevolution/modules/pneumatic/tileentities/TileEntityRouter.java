package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.tileentities;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.IOnSlotChanged;
import com.teamacronymcoders.base.tileentities.TileEntityInventoryBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityRouter extends TileEntityInventoryBase implements IHasGui, IOnSlotChanged {

	public TileEntityRouter() {
		super(1);
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, NBTTagCompound context) {
		return new GuiRouter(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, NBTTagCompound context) {
		return new ContainerRouter(entityPlayer, this);
	}

	@Override
	public void onSlotChanged(Slot slot) {}

}
