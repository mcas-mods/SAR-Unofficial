package xyz.brassgoggledcoders.steamagerevolution.modules.armory.tileenties;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ModuleArmory;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.GunUtils;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IGunPart;

public class TileEntityGunsmithingBench extends TileEntityBase implements IHasGui {

	ItemStackHandler inventory = new ItemStackHandler(10);

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return new GuiGunsmithingBench(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerGunsmithingBench(entityPlayer, this);
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub
		return null;
	}

	public void attemptCraft() {
		if(inventory.getStackInSlot(0).isEmpty()) {
			ItemStack gunStack = new ItemStack(ModuleArmory.gun);
			NBTTagCompound tag = GunUtils.getOrCreateTagCompound(gunStack);
			for(int i = 1; /* Ignore output slot */ i < this.inventory.getSlots(); i++) {
				ItemStack partStack = this.inventory.getStackInSlot(i);
				if(!partStack.isEmpty() && partStack.getItem() instanceof IGunPart) {
					IGunPart part = (IGunPart) partStack.getItem();
					// TODO Doesn't yet allow for duplicate parts (e.g. double barrel)
					tag.setString(part.getPartType().toString(), part.getPartName());
					partStack.shrink(1);
				}
			}
			inventory.setStackInSlot(0, gunStack);
		}
	}

}
