package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.IMultiblockTileInfo;

public abstract class TileEntityDistillerPart extends RectangularMultiblockTileEntityBase<ControllerDistiller>
		implements IMultiblockTileInfo {

	@Override
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

	@Override
	public Class<ControllerDistiller> getMultiblockControllerType() {
		return ControllerDistiller.class;
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return new ControllerDistiller(null);
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
	public MultiblockControllerBase createNewMultiblock() {
		return new ControllerDistiller(getWorld());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		if(this.isConnected()) {
			ControllerDistiller distiller = this.getMultiblockController();
			nbttagcompound.setTag("steam", distiller.steamTank.writeToNBT(new NBTTagCompound()));
			nbttagcompound.setTag("input", distiller.fluidInput.writeToNBT(new NBTTagCompound()));
			nbttagcompound.setTag("output", distiller.fluidOutput.writeToNBT(new NBTTagCompound()));
		}
		return new SPacketUpdateTileEntity(this.pos, 3, nbttagcompound);
	}

	@Nonnull
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
		if(this.isConnected()) {
			ControllerDistiller distiller = this.getMultiblockController();
			nbt.setTag("steam", distiller.steamTank.writeToNBT(new NBTTagCompound()));
			nbt.setTag("input", distiller.fluidInput.writeToNBT(new NBTTagCompound()));
			nbt.setTag("output", distiller.fluidOutput.writeToNBT(new NBTTagCompound()));
		}
		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		if(this.isConnected()) {
			ControllerDistiller distiller = this.getMultiblockController();
			distiller.steamTank.readFromNBT(pkt.getNbtCompound().getCompoundTag("steam"));
			distiller.fluidInput.readFromNBT(pkt.getNbtCompound().getCompoundTag("input"));
			distiller.fluidOutput.readFromNBT(pkt.getNbtCompound().getCompoundTag("output"));
		}
	}

}
