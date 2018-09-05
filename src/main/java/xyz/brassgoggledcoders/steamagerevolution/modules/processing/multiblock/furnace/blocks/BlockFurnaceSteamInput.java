package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.tileentities.TileEntityFurnaceSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockFurnaceSteamInput extends BlockMultiblockBase<TileEntityFurnaceSteamInput> {

    public BlockFurnaceSteamInput(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityFurnaceSteamInput.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityFurnaceSteamInput();
    }

}