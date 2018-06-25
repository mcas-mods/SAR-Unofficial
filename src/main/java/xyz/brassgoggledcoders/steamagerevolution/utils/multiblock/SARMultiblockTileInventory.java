package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class SARMultiblockTileInventory<T extends SARMultiblockInventory> extends SARMultiblockTileBase<T> {
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		if(isConnected()) {
			SARMultiblockInventory controller = getMultiblockController();
			nbt.setTag("inventory", controller.inventory.serializeNBT());
		}
		return new SPacketUpdateTileEntity(pos, 3, nbt);
	}

	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
		if(isConnected()) {
			SARMultiblockInventory controller = getMultiblockController();
			nbt.setTag("inventory", controller.inventory.serializeNBT());
		}
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		if(isConnected()) {
			SARMultiblockInventory controller = getMultiblockController();
			controller.inventory.deserializeNBT(pkt.getNbtCompound().getCompoundTag("inventory"));
		}
	}
}
