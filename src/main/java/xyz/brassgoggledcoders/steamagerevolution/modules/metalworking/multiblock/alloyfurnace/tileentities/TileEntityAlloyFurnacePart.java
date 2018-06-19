package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.tileentities;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.ControllerAlloyFurnace;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.IMultiblockTileInfo;

public abstract class TileEntityAlloyFurnacePart extends RectangularMultiblockTileEntityBase<ControllerAlloyFurnace>
		implements IMultiblockTileInfo {

	@Override
	public Class<ControllerAlloyFurnace> getMultiblockControllerType() {
		return ControllerAlloyFurnace.class;
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return new ControllerAlloyFurnace(null);
	}

	@Override
	public boolean[] getValidPositions() {
		return new boolean[] {isGoodForFrame(null), isGoodForSides(null), isGoodForTop(null), isGoodForBottom(null),
				isGoodForInterior(null)};
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerAlloyFurnace(getWorld());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		if(this.isConnected()) {
			ControllerAlloyFurnace distiller = this.getMultiblockController();
			nbttagcompound.setTag("input", distiller.primaryTank.writeToNBT(new NBTTagCompound()));
			nbttagcompound.setTag("output", distiller.outputTank.writeToNBT(new NBTTagCompound()));
		}
		return new SPacketUpdateTileEntity(this.pos, 3, nbttagcompound);
	}

	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
		if(this.isConnected()) {
			ControllerAlloyFurnace distiller = this.getMultiblockController();
			nbt.setTag("input", distiller.primaryTank.writeToNBT(new NBTTagCompound()));
			nbt.setTag("output", distiller.outputTank.writeToNBT(new NBTTagCompound()));
		}
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		if(this.isConnected()) {
			ControllerAlloyFurnace distiller = this.getMultiblockController();
			distiller.primaryTank.readFromNBT(pkt.getNbtCompound().getCompoundTag("input"));
			distiller.outputTank.readFromNBT(pkt.getNbtCompound().getCompoundTag("output"));
		}
	}

}
