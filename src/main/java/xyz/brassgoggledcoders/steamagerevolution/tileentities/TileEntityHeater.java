package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.SARCaps;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryPieceTemperatureGauge;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.TileEntityCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.PacketSetBurnTime;

public class TileEntityHeater extends TileEntityCraftingMachine<InventoryHeatable> implements ITickable {

    public static final String uid = "heater";

    public TileEntityHeater() {
        setInventory(new InventoryBuilder<>(new InventoryHeatable(this, 1000)).addSteamTank(10, 30)
                .addPiece("temp", new InventoryPieceTemperatureGauge(10, 50)).build());
    }

    @Override
    public void update() {
        if(!this.getWorld().isRemote) {
            SteamAgeRevolution.instance.getPacketHandler()
                    .sendToAllAround(
                            new PacketSetBurnTime(this.getMachinePos(),
                                    (int) this.getInventory().getCapability(SARCaps.HEATABLE, null)
                                            .getCurrentTemperature()),
                            this.getMachinePos(), this.getMachineWorld().provider.getDimension());
            if(this.getInventory().getFluidHandlers().get(0).getFluidAmount() > 0) {
                this.getInventory().internal.setCurrentTemperature(1000);
            }
            for(EnumFacing facing : EnumFacing.VALUES) {
                TileEntity te = this.getWorld().getTileEntity(this.getPos().offset(facing));
                if(te != null && te.hasCapability(SARCaps.HEATABLE, facing)) {
                    this.getInventory().internal.heat(-10);
                    te.getCapability(SARCaps.HEATABLE, facing).heat(10);
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == SARCaps.HEATABLE || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == SARCaps.HEATABLE) {
            return this.getInventory().getCapability(capability, facing);
        }
        else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                    .cast(this.getInventory().getHandler("steamTank", FluidTankSync.class));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public String getUID() {
        return uid;
    }

    @Override
    public ItemStack getCatalyst() {
        return null;
    }
}
