package xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.tileentities.TileEntityTankCasing;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockTankCasing extends BlockMultiblockBase<TileEntityTankCasing> {

    public BlockTankCasing(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityTankCasing.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityTankCasing();
    }

}
