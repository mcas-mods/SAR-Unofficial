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
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		if(isConnected()) {
			SARMultiblockInventory controller = getMultiblockController();
			nbttagcompound.setTag("steam", controller.steamTank.writeToNBT(new NBTTagCompound()));
			nbttagcompound.setTag("input", controller.getFluidInputs().writeToNBT(new NBTTagCompound()));
			nbttagcompound.setTag("output", controller.getFluidOutputs().writeToNBT(new NBTTagCompound()));
		}
		return new SPacketUpdateTileEntity(pos, 3, nbttagcompound);
	}

	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
		if(isConnected()) {
			SARMultiblockInventory controller = getMultiblockController();
			nbt.setTag("steam", controller.steamTank.writeToNBT(new NBTTagCompound()));
			nbt.setTag("input", controller.getFluidInputs().writeToNBT(new NBTTagCompound()));
			nbt.setTag("output", controller.getFluidOutputs().writeToNBT(new NBTTagCompound()));
		}
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		if(isConnected()) {
			SARMultiblockInventory controller = getMultiblockController();
			controller.steamTank.readFromNBT(pkt.getNbtCompound().getCompoundTag("steam"));
			controller.getFluidInputs().readFromNBT(pkt.getNbtCompound().getCompoundTag("input"));
			controller.getFluidOutputs().readFromNBT(pkt.getNbtCompound().getCompoundTag("output"));
		}
	}
}
