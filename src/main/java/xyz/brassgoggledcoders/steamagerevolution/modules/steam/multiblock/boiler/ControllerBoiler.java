package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerBoiler extends SARMultiblockInventory {

    public static final int fuelDivisor = 3;
    public static final int fluidConversionPerTick = 5;
    public static final float maxPressure = 3.0F;

    public float pressure = 1.0F;
    public int currentBurnTime = 0;
    public BlockPos minimumInteriorPos;
    public BlockPos maximumInteriorPos;
    public boolean hasWindow = false;
    Set<BlockPos> attachedMonitors;
    Set<BlockPos> attachedValves;

    public ControllerBoiler(World world) {
        super(world);
        attachedMonitors = new HashSet<BlockPos>();
        attachedValves = new HashSet<BlockPos>();
        this.setInventory(new InventoryMachine(new InventoryPieceItem(new ItemStackHandlerFuel(1, this), 81, 32),
                new InventoryPieceFluid(new MultiFluidTank(Fluid.BUCKET_VOLUME * 16, this, 1), 0, 0), null,
                /* TODO: having water tank as output is...hacky */new InventoryPieceFluid(
                new MultiFluidTank(Fluid.BUCKET_VOLUME * 16, this, 1), 50, 9),
                new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 4, "steam", this), 142, 9)));
    }

    @Override
    protected boolean updateServer() {

        // Logic must of course run before checking if it should explode...!
        for (BlockPos pos : attachedValves) {
            if (WORLD.isBlockPowered(pos)) {
                this.inventory.getSteamTank().drain(Fluid.BUCKET_VOLUME, true);
                pressure = 1.0F;
                updateRedstoneOutputLevels();
                return true;
            }
        }

        if (ModuleSteam.enableDestruction && pressure > maxPressure) {
            // Whoopsyboom
            WORLD.createExplosion(null, getReferenceCoord().getX(), getReferenceCoord().getY(),
                    getReferenceCoord().getZ(), 10 * pressure, true);
            return true;
        }

        if (currentBurnTime == 0) {
            for (int i = 0; i < this.inventory.getInputHandler().getSlots(); i++) {
                ItemStack fuel = this.inventory.getInputHandler().getStackInSlot(i);
                if (!fuel.isEmpty() && TileEntityFurnace.getItemBurnTime(fuel) != 0) {
                    currentBurnTime = (TileEntityFurnace.getItemBurnTime(fuel) / fuelDivisor);
                    // TODO
                    fuel.shrink(1);
                    this.inventory.getInputHandler().setStackInSlot(i, fuel);
                    return true;
                }
            }
            if (this.inventory.getInputTank().getFluidAmount() != 0) {
                // TODO
                if (this.inventory.getInputTank().getFluid().getFluid() == FluidRegistry.LAVA) {
                    currentBurnTime = 1000;
                    return true;
                }
            }
        } else {
            if (this.inventory.getOutputTank().getFluidAmount() >= fluidConversionPerTick) {
                if (this.inventory.getSteamTank()
                        .getFluidAmount() <= (this.inventory.getSteamTank().getCapacity() - fluidConversionPerTick)) {
                    this.inventory.getSteamTank()
                            .fill(new FluidStack(FluidRegistry.getFluid("steam"), fluidConversionPerTick), true);
                    this.inventory.getOutputTank().drain(fluidConversionPerTick, true);
                } else {
                    pressure += 0.01F;
                    updateRedstoneOutputLevels();
                }
                currentBurnTime--;
                return true;
            }
        }

        return false;
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 26;
    }

    @Override
    public int getMaximumXSize() {
        return 6;
    }

    @Override
    public int getMaximumZSize() {
        return 6;
    }

    @Override
    public int getMaximumYSize() {
        return 6;
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
        pressure = data.getFloat("pressure");
        currentBurnTime = data.getInteger("burntime");
        super.onAttachedPartWithMultiblockData(part, data);
    }

    @Override
    public void writeToDisk(NBTTagCompound data) {
        data.setFloat("pressure", pressure);
        data.setInteger("burntime", currentBurnTime);
        super.writeToDisk(data);
    }

    @Override
    protected void onBlockAdded(IMultiblockPart newPart) {
        if (newPart instanceof TileEntityBoilerPressureMonitor) {
            attachedMonitors.add(newPart.getWorldPosition());
        } else if (newPart instanceof TileEntityBoilerPressureValve) {
            attachedValves.add(newPart.getWorldPosition());
        } else if (newPart instanceof TileEntityBoilerGauge) {
            this.hasWindow = true;
        }
    }

    @Override
    protected void onBlockRemoved(IMultiblockPart oldPart) {

        if (oldPart instanceof TileEntityBoilerGauge && hasWindow == true) {
            hasWindow = this.connectedParts.stream().noneMatch(part -> part instanceof TileEntityBoilerGauge);
        }

        if (oldPart instanceof TileEntityBoilerPressureMonitor) {
            attachedMonitors.remove(oldPart.getWorldPosition());
        } else if (oldPart instanceof TileEntityBoilerPressureValve) {
            attachedValves.remove(oldPart.getWorldPosition());
        }
    }

    private void updateRedstoneOutputLevels() {
        for (BlockPos pos : attachedMonitors) {
            // FMLLog.warning(pos.toString());
            WORLD.updateComparatorOutputLevel(pos, ModuleSteam.boilerPressureMonitor);
        }
    }

    @Override
    public String getName() {
        return "Boiler";
    }

    @Override
    protected void onMachineAssembled() {
        Pair<BlockPos, BlockPos> interiorPositions = PositionUtils.shrinkPositionCubeBy(getMinimumCoord(),
                getMaximumCoord(), 1);
        minimumInteriorPos = interiorPositions.getLeft();
        maximumInteriorPos = interiorPositions.getRight();
        super.onMachineAssembled();
    }

}
