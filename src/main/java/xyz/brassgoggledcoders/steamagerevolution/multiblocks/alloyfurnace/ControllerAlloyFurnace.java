package xyz.brassgoggledcoders.steamagerevolution.multiblocks.alloyfurnace;

import com.teamacronymcoders.base.multiblocksystem.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblocksystem.validation.ValidationError;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.handlers.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces.InventoryPieceTemperatureGauge;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.MachineType;
import xyz.brassgoggledcoders.steamagerevolution.machinesystem.multiblock.MultiblockMachineType;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class ControllerAlloyFurnace extends MultiblockCraftingMachine<InventoryHeatable> {

    public static final String uid = "alloy_furnace";
    public static int inputCapacity = RecipeUtil.VALUE_BLOCK * 8;
    public static int outputCapacity = Fluid.BUCKET_VOLUME * 8;

    public ControllerAlloyFurnace(World world) {
        super(world);
        // FIXME Need to reimplement MultiFluidHandler in order to expose both tanks
        // with a single wrapper
        setInventory(new InventoryBuilder<>(new InventoryHeatable(this, 500))
                .addPiece("tankOne",
                        new InventoryPieceFluidTank(IOType.INPUT, new FluidTankSync(inputCapacity), 22, 11))
                .addPiece("tankTwo",
                        new InventoryPieceFluidTank(IOType.INPUT, new FluidTankSync(inputCapacity), 78, 11))
                .addPiece("heat", new InventoryPieceTemperatureGauge(10, 10))
                .addPiece("output",
                        new InventoryPieceFluidTank(IOType.OUTPUT, new FluidTankSync(outputCapacity), 134, 17))
                .build());
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        // TODO
        return 1;
    }

    @Override
    protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
        if(world.getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.LAVA) {
            return true;
        }
        else {
            validatorCallback
                    .setLastError(new ValidationError("steamagerevolution.multiblock.validation.alloyforgeinterior"));
            return false;
        }
    }

    @Override
    public int getMinimumXSize() {
        return 5;
    }

    @Override
    public int getMinimumZSize() {
        return 5;
    }

    @Override
    public int getMinimumYSize() {
        return 6;
    }

    @Override
    public int getMaximumXSize() {
        return 5;
    }

    @Override
    public int getMaximumZSize() {
        return 5;
    }

    @Override
    public int getMaximumYSize() {
        return 6;
    }

    @Override
    public MultiblockMachineType getMachineType() {
        if(!MachineType.machinesList.containsKey(uid)) {
            MachineType.machinesList.put(uid, new MultiblockMachineType(uid, SARObjectHolder.alloy_furnace_frame,
                    SARObjectHolder.alloy_furnace_fluid_input, SARObjectHolder.alloy_furnace_fluid_output));
        }
        return (MultiblockMachineType) MachineType.machinesList.get(uid);
    }
}
