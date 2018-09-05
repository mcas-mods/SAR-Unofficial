package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.tileentities.TileEntityDistillerFrame;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockDistillerFrame extends BlockMultiblockBase<TileEntityDistillerFrame> {

    public BlockDistillerFrame(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityDistillerFrame.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState blockState) {
        return new TileEntityDistillerFrame();
    }

}