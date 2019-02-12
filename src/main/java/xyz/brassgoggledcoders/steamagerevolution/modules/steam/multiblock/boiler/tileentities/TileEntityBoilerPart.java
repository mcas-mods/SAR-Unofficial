package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.ControllerBoiler;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public abstract class TileEntityBoilerPart extends SARMultiblockTileInventory<ControllerBoiler> {

    @Override
    public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
        return ControllerBoiler.class;
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerBoiler(getWorld());
    }

    @Override
    public ISARMultiblock getControllerInfo() {
        return new ControllerBoiler(null);
    }
}
