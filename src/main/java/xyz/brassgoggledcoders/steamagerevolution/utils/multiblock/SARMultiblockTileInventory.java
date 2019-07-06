package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.SARMultiblockRecipe;

public abstract class SARMultiblockTileInventory<T extends SARMultiblockRecipe> extends SARMultiblockTileBase<T> {
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		if (isConnected()) {
			SARMultiblockRecipe controller = getMultiblockController();
			nbt.setTag("inventory", controller.inventory.serializeNBT());
			nbt.setInteger("currentTicks", controller.currentTicks);
			int maxTicks = 0;
			if (controller.currentRecipe != null) {
				maxTicks = controller.currentRecipe.getTicksPerOperation();
			}
			nbt.setInteger("currentMaxTicks", maxTicks);
		}
		return new SPacketUpdateTileEntity(pos, 3, nbt);
	}

	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
		if (isConnected()) {
			SARMultiblockRecipe controller = getMultiblockController();
			nbt.setTag("inventory", controller.inventory.serializeNBT());
			nbt.setInteger("currentTicks", controller.currentTicks);
			int maxTicks = 0;
			if (controller.currentRecipe != null) {
				maxTicks = controller.currentRecipe.getTicksPerOperation();
			}
			nbt.setInteger("currentMaxTicks", maxTicks);
		}
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		// SteamAgeRevolution.instance.getLogger().devInfo("On Data Packet");
		if (isConnected()) {
			SARMultiblockRecipe controller = getMultiblockController();
			NBTTagCompound nbt = pkt.getNbtCompound();
			controller.inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
			controller.currentTicks = nbt.getInteger("currentTicks");
			controller.currentMaxTicks = nbt.getInteger("currentMaxTicks");
		}
	}
}
