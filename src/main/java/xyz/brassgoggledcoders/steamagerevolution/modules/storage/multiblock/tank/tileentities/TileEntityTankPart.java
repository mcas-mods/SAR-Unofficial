package xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.ControllerTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileBase;

import javax.annotation.Nonnull;

public abstract class TileEntityTankPart extends SARMultiblockTileBase<ControllerTank> {

    @Override
    public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
        return ControllerTank.class;
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerTank(getWorld());
    }

    @Override
    public ISARMultiblock getControllerInfo() {
        return new ControllerTank(null);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        if (isConnected()) {
            ControllerTank controller = getMultiblockController();
            nbttagcompound.setTag("tank", controller.tank.writeToNBT(new NBTTagCompound()));
        }
        return new SPacketUpdateTileEntity(pos, 3, nbttagcompound);
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
        if (isConnected()) {
            ControllerTank controller = getMultiblockController();
            nbt.setTag("tank", controller.tank.writeToNBT(new NBTTagCompound()));
        }
        return nbt;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        if (isConnected()) {
            ControllerTank controller = getMultiblockController();
            controller.tank.readFromNBT(pkt.getNbtCompound().getCompoundTag("tank"));
        }
    }
}
