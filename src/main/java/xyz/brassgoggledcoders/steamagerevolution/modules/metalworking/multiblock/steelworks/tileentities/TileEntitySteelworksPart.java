package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.tileentities;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.ControllerSteelworks;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMultiblock;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockTileInventory;

public abstract class TileEntitySteelworksPart extends SARMultiblockTileInventory<ControllerSteelworks> {

    @Override
    public Class<ControllerSteelworks> getMultiblockControllerType() {
        return ControllerSteelworks.class;
    }

    @Override
    public ISARMultiblock getControllerInfo() {
        return new ControllerSteelworks(null);
    }

    @Override
    public MultiblockControllerBase createNewMultiblock() {
        return new ControllerSteelworks(getWorld());
    }

}
