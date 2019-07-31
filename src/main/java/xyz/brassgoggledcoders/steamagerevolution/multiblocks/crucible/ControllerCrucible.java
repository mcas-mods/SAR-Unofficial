package xyz.brassgoggledcoders.steamagerevolution.multiblocks.crucible;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeUtil;

public class ControllerCrucible extends MultiblockCraftingMachine<InventoryHeatable> {

    public static final String uid = "crucible";
    BlockPos minimumInteriorPos;
    BlockPos maximumInteriorPos;

    public ControllerCrucible(World world) {
        super(world);
        setInventory(new InventoryBuilder<>(new InventoryHeatable(this, 1000))
                .addPiece("itemInput",
                        new InventoryPieceItemHandler(IOType.INPUT, new ItemStackHandlerSync(1), new int[] { 53 },
                                new int[] { 34 }))
                .addPiece("fluidOutput",
                        new InventoryPieceFluidTank(IOType.OUTPUT, new FluidTankSync(RecipeUtil.VALUE_BLOCK * 4), 11,
                                105))
                .addPiece("temp", new InventoryPieceTemperatureGauge(10, 5))
                .addPiece("progress", new InventoryPieceProgressBar(76, 33)).build());
    }

    // FIXME Caching
    // @Override
    // protected void onMachineAssembled() {
    // Pair<BlockPos, BlockPos> interiorPositions =
    // com.teamacronymcoders.base.util.PositionUtils
    // .shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
    // minimumInteriorPos = interiorPositions.getLeft();
    // maximumInteriorPos = interiorPositions.getRight();
    //
    // // int blocksInside = 0;
    // // // TODO Expensive for loop just to increment an integer
    // // for(BlockPos pos : BlockPos.getAllInBoxMutable(minimumInteriorPos,
    // // maximumInteriorPos)) {
    // // blocksInside++;
    // // }
    // // Size internal tank accordingly
    // // TODO
    // // MultiFluidHandler newTank = new MultiFluidHandler(blocksInside *
    // // Fluid.BUCKET_VOLUME, this, 1);
    // // if(inventory.getOutputFluidHandler().fluids != null) {
    // // newTank.fluids.addAll(inventory.getOutputFluidHandler().fluids);
    // // }
    // // inventory.setFluidOutput(newTank);
    // super.onMachineAssembled();
    // }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine() {
        return 17;
    }

    @Override
    public int getMinimumXSize() {
        return 3;
    }

    @Override
    public int getMinimumZSize() {
        return 3;
    }

    @Override
    public int getMinimumYSize() {
        return 3;
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
        return 5;
    }

    @Override
    public String getUID() {
        return uid;
    }

    @Override
    public ItemStack getCatalyst() {
        return new ItemStack(SARObjectHolder.crucible_casing);
    }
}
