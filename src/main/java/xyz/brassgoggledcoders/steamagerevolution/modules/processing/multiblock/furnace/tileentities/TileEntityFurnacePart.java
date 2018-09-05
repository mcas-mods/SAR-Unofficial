package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.ControllerSteamFurnace;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public abstract class TileEntityFurnacePart extends SARMultiblockTileInventory<ControllerSteamFurnace> {

    @Override
    public Class<ControllerSteamFurnace> getMultiblockControllerType() {
        return ControllerSteamFurnace.class;
    }

    @Override
    public ISARMultiblock getControllerInfo() {
        return new ControllerSteamFurnace(null);
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerSteamFurnace(getWorld());
    }

}